package view;

import Controller.DBController;
import controller.AdminController;
import controller.BookController;
import controller.IssuedBookController;
import controller.MemberController;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Book;
import model.Member;

/**
 *
 * @author THISARA DASUN
 */
public class IssuedBooks extends javax.swing.JFrame {

    /**
     * Creates new form IssuedBooks
     */
    private DBController dbController;

    public IssuedBooks(DBController instance) {
        initComponents();
        centerWindow();
//        refreshIssuedBooksTable();
//        setupBookAutoFillListener();
        setupTable();
        fetchAndDisplayMembers("Active");
        fetchAndDisplayIssuedBooks();
        initializeTableListeners();
        initializeTableSettings();
        initializeTableSettings();

        try {
            initializeControllers();
        } catch (SQLException ex) {
            Logger.getLogger(IssuedBooks.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(IssuedBooks.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void initializeControllers() throws SQLException, ParseException {
        // Initialize database and controllers
        dbController = DBController.getInstance();
        fetchAndDisplayBooks();

    }

    private void setupTable() {
        String[] columnNames = {"ID", "Name", "Contact", "Address", "Course", "expire_date", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        Member_table.setModel(model);
    }

    private void centerWindow() {
        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 1356;
        int height = 659;

        // Calculate the position to center the window
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;

        // Set window bounds (position and size)
        setBounds(x, y, width, height);
    }

    public void initializeTableListeners() {
        Member_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    fillMemberFields();
                }
            }
        });

        Book_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    fillBookFields();
                }
            }
        });
    }

    // 1. Initialize the table settings
    public void initializeTableSettings() {
        // Member table settings
        Member_table.getTableHeader().setReorderingAllowed(false);
        Member_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Member_table.setRowHeight(25);

        // Book table settings
        Book_table.getTableHeader().setReorderingAllowed(false);
        Book_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Book_table.setRowHeight(25);

        // Add mouse listeners
        Member_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    fillMemberFields();
                }
            }
        });

        Book_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    fillBookFields();
                }
            }
        });
    }

    // 2. Method to fill member fields
    private void fillMemberFields() {
        try {
            int selectedRow = Member_table.getSelectedRow();
            if (selectedRow != -1) {
                // Get values directly from the table
                String memberId = Member_table.getValueAt(selectedRow, 0).toString();
                String memberName = Member_table.getValueAt(selectedRow, 1).toString();

                // Set the values to text fields
                MemberID_txt.setText(memberId);
                MemberName_txt.setText(memberName);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error selecting member: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

// 3. Method to fill book fields
    private void fillBookFields() {
        try {
            int selectedRow = Book_table.getSelectedRow();
            if (selectedRow != -1) {
                // Get values directly from the table
                String bookId = Book_table.getValueAt(selectedRow, 0).toString();
                String bookTitle = Book_table.getValueAt(selectedRow, 1).toString();

                // Set the values to text fields
                Book_id_txt.setText(bookId);
                BookTitle_txt.setText(bookTitle);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error selecting book: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // 4. Modified method to save issued book
    public void saveIssuedBook() {
        try {
            // Validate fields
            if (MemberID_txt.getText().trim().isEmpty()
                    || MemberName_txt.getText().trim().isEmpty()
                    || Book_id_txt.getText().trim().isEmpty()
                    || BookTitle_txt.getText().trim().isEmpty()
                    || IssuedDate_dateChooser.getDate() == null
                    || DueDate_datechooser.getDate() == null) {

                JOptionPane.showMessageDialog(this,
                        "Please fill all required fields",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Get connection and prepare statement
            Connection conn = DBController.getInstance().getConnection();
            String sql = "INSERT INTO issued_books (member_id, member_name, book_id, book_title, "
                    + "issued_date, due_date) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Set parameters
                pstmt.setString(1, MemberID_txt.getText().trim());
                pstmt.setString(2, MemberName_txt.getText().trim());
                pstmt.setString(3, Book_id_txt.getText().trim());
                pstmt.setString(4, BookTitle_txt.getText().trim());

                // Convert dates
                java.util.Date issuedDate = IssuedDate_dateChooser.getDate();
                java.util.Date dueDate = DueDate_datechooser.getDate();
                pstmt.setDate(5, new java.sql.Date(issuedDate.getTime()));
                pstmt.setDate(6, new java.sql.Date(dueDate.getTime()));

                // Execute update
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "Book issued successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Clear fields and refresh table
                clearFields();
                fetchAndDisplayIssuedBooks();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error issuing book: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

// 5. Method to clear fields
    private void clearFields() {
        MemberID_txt.setText("");
        MemberName_txt.setText("");
        Book_id_txt.setText("");
        BookTitle_txt.setText("");
        IssuedDate_dateChooser.setDate(null);
        DueDate_datechooser.setDate(null);
    }

    // 7. Method to fetch and display issued books
    public void fetchAndDisplayIssuedBooks() {
        try {
            Connection conn = DBController.getInstance().getConnection();
            String sql = "SELECT member_id, member_name, book_id, book_title, "
                    + "issued_date, due_date FROM issued_books";

            try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

                DefaultTableModel model = (DefaultTableModel) Issued_book_Member_table.getModel();
                model.setRowCount(0); // Clear existing rows

                while (rs.next()) {
                    Object[] row = {
                        rs.getString("member_id"),
                        rs.getString("member_name"),
                        rs.getString("book_id"),
                        rs.getString("book_title"),
                        rs.getDate("issued_date"),
                        rs.getDate("due_date")
                    };
                    model.addRow(row);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error fetching issued books: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void fetchAndDisplayMembers(String status) {
        try {
            Connection conn = DBController.getInstance().getConnection();
            String sql = "SELECT id, name, contact, address, course, expire_date, status "
                    + "FROM members WHERE status = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, status);
                try (ResultSet rs = pstmt.executeQuery()) {
                    DefaultTableModel model = (DefaultTableModel) Member_table.getModel();
                    model.setRowCount(0); // Clear existing rows

                    while (rs.next()) {
                        Object[] row = {
                            rs.getInt("id"),
                            rs.getString("name") != null ? rs.getString("name") : "",
                            rs.getString("contact") != null ? rs.getString("contact") : "",
                            rs.getString("address") != null ? rs.getString("address") : "",
                            rs.getString("course") != null ? rs.getString("course") : "",
                            rs.getString("expire_date") != null ? rs.getString("expire_date") : "",
                            rs.getString("status") != null ? rs.getString("status") : ""
                        };
                        model.addRow(row);
                    }
                }
                pstmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error fetching members: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void fetchAndDisplayBooks() {
        try {
            if (dbController == null) {
                throw new SQLException("Database controller not initialized");
            }

            try (var connection = dbController.getConnection(); var pstmt = connection.prepareStatement(
                    "SELECT book_id, book_title, author, publication_year, "
                    + "language, genre, copies_available, format FROM books"); var rs = pstmt.executeQuery()) {

                // Get the table model and clear existing rows
                var model = (javax.swing.table.DefaultTableModel) Book_table.getModel();
                model.setRowCount(0);

                // Set column headers
                String[] columnHeaders = {
                    "ID", "Title", "Author", "Year", "Language",
                    "Genre", "Copies", "Format"
                };
                model.setColumnIdentifiers(columnHeaders);

                // Add rows to table
                while (rs.next()) {
                    Object[] row = {
                        rs.getInt("book_id"),
                        rs.getString("book_title"),
                        rs.getString("author"),
                        rs.getString("publication_year"),
                        rs.getString("language"),
                        rs.getString("genre"),
                        rs.getInt("copies_available"),
                        rs.getString("format")
                    };
                    model.addRow(row);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error fetching data: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
            // Log the error
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

//    private void autoFillBookDetails(String input) {
//        try {
//            BookController bookController = new BookController();
//            Book book = null;
//
//            // Try to find by ID
//            book = bookController.findBookByID(input);
//
//            // If not found, try to find by Title
//            if (book == null) {
//                book = bookController.findBookByTitle(input);
//            }
//
//            // If book found, populate all relevant fields
//            if (book != null) {
//                Book_id_txt.setText(String.valueOf(book.getBookId()));
//                BookTitle_txt.setText(book.getTitle());
////                Book_Author_txt.setText(book.getAuthorName());
////                Book_PublishedYear_txt.setText(book.getPublishedYear());
////                Book_Language_txt.setText(book.getLanguage());
////                Book_Genre_txt.setText(book.getGenre());
////                Book_Copies_txt.setText(String.valueOf(book.getCopies_Available()));
////                Book_Format_txt.setText(book.getFormat());
//            } else {
//                JOptionPane.showMessageDialog(this, "Book not found!");
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Error finding book: " + e.getMessage());
//        }
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Books_btn = new javax.swing.JButton();
        Members_btn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        Edit_Member_btn = new javax.swing.JButton();
        Edit_Books_btn = new javax.swing.JButton();
        Issued_Books_btn = new javax.swing.JButton();
        Return_Books_btn1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Issued_book_Member_table = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        Book_table = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        Member_table = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        mem_id_lbl = new javax.swing.JLabel();
        member_name_lbl = new javax.swing.JLabel();
        book_id_lbl = new javax.swing.JLabel();
        booktitle_lbl = new javax.swing.JLabel();
        BookTitle_txt = new javax.swing.JTextField();
        Book_id_txt = new javax.swing.JTextField();
        MemberName_txt = new javax.swing.JTextField();
        MemberID_txt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        IssuedDate_dateChooser = new com.toedter.calendar.JDateChooser();
        DueDate_datechooser = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        save = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(9, 16, 87));
        jPanel1.setForeground(new java.awt.Color(255, 153, 153));

        jLabel1.setBackground(new java.awt.Color(102, 102, 102));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(250, 64, 50));
        jLabel1.setText("Library  ");

        Books_btn.setBackground(new java.awt.Color(250, 129, 47));
        Books_btn.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        Books_btn.setForeground(new java.awt.Color(255, 255, 255));
        Books_btn.setText("Members");
        Books_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Books_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Books_btnActionPerformed(evt);
            }
        });

        Members_btn.setBackground(new java.awt.Color(250, 129, 47));
        Members_btn.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        Members_btn.setForeground(new java.awt.Color(255, 255, 255));
        Members_btn.setText("Books");
        Members_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Members_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Members_btnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Management System");

        Edit_Member_btn.setBackground(new java.awt.Color(250, 129, 47));
        Edit_Member_btn.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        Edit_Member_btn.setForeground(new java.awt.Color(255, 255, 255));
        Edit_Member_btn.setText("Edit Member");
        Edit_Member_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Edit_Member_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Edit_Member_btnActionPerformed(evt);
            }
        });

        Edit_Books_btn.setBackground(new java.awt.Color(250, 129, 47));
        Edit_Books_btn.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        Edit_Books_btn.setForeground(new java.awt.Color(255, 255, 255));
        Edit_Books_btn.setText("Edit Books");
        Edit_Books_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Edit_Books_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Edit_Books_btnActionPerformed(evt);
            }
        });

        Issued_Books_btn.setBackground(new java.awt.Color(250, 129, 47));
        Issued_Books_btn.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        Issued_Books_btn.setForeground(new java.awt.Color(255, 255, 255));
        Issued_Books_btn.setText("Issued Books");
        Issued_Books_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Issued_Books_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Issued_Books_btnActionPerformed(evt);
            }
        });

        Return_Books_btn1.setBackground(new java.awt.Color(250, 129, 47));
        Return_Books_btn1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        Return_Books_btn1.setForeground(new java.awt.Color(255, 255, 255));
        Return_Books_btn1.setText("Return Books");
        Return_Books_btn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Return_Books_btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Return_Books_btn1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Books_btn, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Edit_Member_btn, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Edit_Books_btn, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Members_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Issued_Books_btn, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Return_Books_btn1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(55, 55, 55)
                .addComponent(Books_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Members_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Edit_Member_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Edit_Books_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Issued_Books_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Return_Books_btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(2, 76, 170));

        jPanel4.setBackground(new java.awt.Color(2, 76, 170));

        Issued_book_Member_table.setBackground(new java.awt.Color(255, 247, 209));
        Issued_book_Member_table.setForeground(new java.awt.Color(0, 0, 204));
        Issued_book_Member_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        Issued_book_Member_table.setSelectionForeground(new java.awt.Color(0, 0, 204));
        Issued_book_Member_table.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                Issued_book_Member_tableAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(Issued_book_Member_table);

        Book_table.setBackground(new java.awt.Color(255, 247, 209));
        Book_table.setForeground(new java.awt.Color(0, 0, 204));
        Book_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        Book_table.setSelectionForeground(new java.awt.Color(0, 0, 204));
        Book_table.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                Book_tableAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        Book_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Book_tableMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(Book_table);

        Member_table.setBackground(new java.awt.Color(255, 247, 209));
        Member_table.setForeground(new java.awt.Color(0, 0, 204));
        Member_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        Member_table.setSelectionForeground(new java.awt.Color(0, 0, 204));
        Member_table.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                Member_tableAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        Member_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Member_tableMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(Member_table);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(2, 76, 170));

        jLabel4.setBackground(new java.awt.Color(250, 129, 47));
        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(250, 129, 47));
        jLabel4.setText("Issued");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("books");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(2, 76, 170));

        mem_id_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        mem_id_lbl.setForeground(new java.awt.Color(255, 255, 255));
        mem_id_lbl.setText("Member ID");

        member_name_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        member_name_lbl.setForeground(new java.awt.Color(255, 255, 255));
        member_name_lbl.setText("Member Name ");

        book_id_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        book_id_lbl.setForeground(new java.awt.Color(255, 255, 255));
        book_id_lbl.setText("Book ID");

        booktitle_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        booktitle_lbl.setForeground(new java.awt.Color(255, 255, 255));
        booktitle_lbl.setText("Book Title");

        BookTitle_txt.setBackground(new java.awt.Color(255, 255, 255));
        BookTitle_txt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        BookTitle_txt.setForeground(new java.awt.Color(0, 102, 255));

        Book_id_txt.setBackground(new java.awt.Color(255, 255, 255));
        Book_id_txt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Book_id_txt.setForeground(new java.awt.Color(0, 102, 255));
        Book_id_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Book_id_txtActionPerformed(evt);
            }
        });

        MemberName_txt.setBackground(new java.awt.Color(255, 255, 255));
        MemberName_txt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        MemberName_txt.setForeground(new java.awt.Color(0, 102, 255));
        MemberName_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MemberName_txtActionPerformed(evt);
            }
        });

        MemberID_txt.setBackground(new java.awt.Color(255, 255, 255));
        MemberID_txt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        MemberID_txt.setForeground(new java.awt.Color(0, 102, 255));
        MemberID_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MemberID_txtActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Issue Date");

        IssuedDate_dateChooser.setBackground(new java.awt.Color(255, 255, 255));
        IssuedDate_dateChooser.setForeground(new java.awt.Color(0, 102, 255));

        DueDate_datechooser.setBackground(new java.awt.Color(255, 255, 255));
        DueDate_datechooser.setForeground(new java.awt.Color(0, 102, 255));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Due Date");

        save.setBackground(new java.awt.Color(250, 129, 47));
        save.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        save.setForeground(new java.awt.Color(255, 255, 255));
        save.setText("Save");
        save.setBorder(null);
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mem_id_lbl)
                    .addComponent(book_id_lbl)
                    .addComponent(member_name_lbl)
                    .addComponent(booktitle_lbl))
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MemberName_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MemberID_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Book_id_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BookTitle_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(IssuedDate_dateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DueDate_datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(mem_id_lbl))
                                    .addComponent(MemberID_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(member_name_lbl)
                                    .addComponent(MemberName_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Book_id_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(book_id_lbl)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel10)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(booktitle_lbl)
                            .addComponent(BookTitle_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(IssuedDate_dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addComponent(DueDate_datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Books_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Books_btnActionPerformed
        setVisible(false);
        try {
            new View().setVisible(true);

        } catch (ParseException ex) {
            Logger.getLogger(EditBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_Books_btnActionPerformed

    private void Members_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Members_btnActionPerformed
        setVisible(false);
        try {
            new View().setVisible(true);

        } catch (ParseException ex) {
            Logger.getLogger(EditBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_Members_btnActionPerformed

    private void Edit_Member_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Edit_Member_btnActionPerformed
        setVisible(false);
        EditMember editMemberFrame = new EditMember(DBController.getInstance());
        editMemberFrame.setVisible(true);
    }//GEN-LAST:event_Edit_Member_btnActionPerformed

    private void Edit_Books_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Edit_Books_btnActionPerformed
        setVisible(false);
        EditBooks editBooksFrame = new EditBooks(DBController.getInstance());
        editBooksFrame.setVisible(true);
    }//GEN-LAST:event_Edit_Books_btnActionPerformed

    private void Issued_Books_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Issued_Books_btnActionPerformed
        setVisible(false);
        IssuedBooks IssuedBooksFrame = new IssuedBooks(DBController.getInstance());
        IssuedBooksFrame.setVisible(true);
    }//GEN-LAST:event_Issued_Books_btnActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        saveIssuedBook();
    }//GEN-LAST:event_saveActionPerformed

    private void MemberName_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MemberName_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MemberName_txtActionPerformed

    private void Book_id_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Book_id_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Book_id_txtActionPerformed

    private void MemberID_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MemberID_txtActionPerformed

    }//GEN-LAST:event_MemberID_txtActionPerformed

    private void Issued_book_Member_tableAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_Issued_book_Member_tableAncestorAdded
        //        //        fetchAndDisplayMembers();
        //        fetchAndDisplayMembers("active");
    }//GEN-LAST:event_Issued_book_Member_tableAncestorAdded

    private void Book_tableAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_Book_tableAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_Book_tableAncestorAdded

    private void Member_tableAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_Member_tableAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_Member_tableAncestorAdded

    private void Member_tableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Member_tableMousePressed
        initializeTableSettings();
        initializeTableListeners();
    }//GEN-LAST:event_Member_tableMousePressed

    private void Book_tableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Book_tableMousePressed
        initializeTableSettings();
        initializeTableListeners();
    }//GEN-LAST:event_Book_tableMousePressed

    private void Return_Books_btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Return_Books_btn1ActionPerformed
        setVisible(false);
        ReturnedBooks ReturnedBooksFrame = new ReturnedBooks(DBController.getInstance());
        ReturnedBooksFrame.setVisible(true);
    }//GEN-LAST:event_Return_Books_btn1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IssuedBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IssuedBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IssuedBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IssuedBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IssuedBooks(DBController.getInstance()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BookTitle_txt;
    private javax.swing.JTextField Book_id_txt;
    private javax.swing.JTable Book_table;
    private javax.swing.JButton Books_btn;
    private com.toedter.calendar.JDateChooser DueDate_datechooser;
    private javax.swing.JButton Edit_Books_btn;
    private javax.swing.JButton Edit_Member_btn;
    private com.toedter.calendar.JDateChooser IssuedDate_dateChooser;
    private javax.swing.JButton Issued_Books_btn;
    private javax.swing.JTable Issued_book_Member_table;
    private javax.swing.JTextField MemberID_txt;
    private javax.swing.JTextField MemberName_txt;
    private javax.swing.JTable Member_table;
    private javax.swing.JButton Members_btn;
    private javax.swing.JButton Return_Books_btn1;
    private javax.swing.JLabel book_id_lbl;
    private javax.swing.JLabel booktitle_lbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel mem_id_lbl;
    private javax.swing.JLabel member_name_lbl;
    private javax.swing.JButton save;
    // End of variables declaration//GEN-END:variables
}
