package view;

import Controller.DBController;
import java.sql.ResultSet;
import java.util.Date;
import controller.BookController;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class EditBooks extends javax.swing.JFrame {

    private DBController dbController;

    /**
     * Creates new form aditBooks
     */
    public EditBooks(DBController dbController) {
        this.dbController = dbController;
        initComponents();
        centerWindow();
        setupBookEditListener();
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

    private void setupBookEditListener() {
        BookID_txt.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fetchBookDetails();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fetchBookDetails();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                fetchBookDetails();
            }
        });
    }

    private void fetchBookDetails() {
        String bookId = BookID_txt.getText();

        try {
            try (Connection con = dbController.getConnection()) {
                if (con == null) {
                    JOptionPane.showMessageDialog(this, "Database connection failed!");
                    return;
                }

                String sql = "SELECT book_title, author, publication_year, language, "
                        + "copies_available, genre, format FROM books WHERE book_id = ?";
                ResultSet rs;
                try (PreparedStatement pst = con.prepareStatement(sql)) {
                    pst.setString(1, bookId);
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        BookTitle_txt.setText(rs.getString("book_title"));
                        Author_txt.setText(rs.getString("author"));
                        PublicationYear_txt.setText(rs.getString("publication_year"));

                        for (int i = 0; i < Language_jComboBox.getItemCount(); i++) {
                            if (Language_jComboBox.getItemAt(i).equalsIgnoreCase(rs.getString("language"))) {
                                Language_jComboBox.setSelectedIndex(i);
                                break;
                            }
                        }
                        CopiesAvailable_txt.setText(rs.getString("copies_available"));

                        // Set combo box values
                        String genre = rs.getString("genre");
                        String format = rs.getString("format");

                        // Find and set the matching genre
                        for (int i = 0; i < Genre_txt.getItemCount(); i++) {
                            if (Genre_txt.getItemAt(i).equalsIgnoreCase(genre)) {
                                Genre_txt.setSelectedIndex(i);
                                break;
                            }
                        }

                        // Find and set the matching format
                        for (int i = 0; i < Book_Format_txt.getItemCount(); i++) {
                            if (Book_Format_txt.getItemAt(i).equalsIgnoreCase(format)) {
                                Book_Format_txt.setSelectedIndex(i);
                                break;
                            }
                        }
                    } else {
                        // Clear fields if no book found
                        BookTitle_txt.setText("");
                        Author_txt.setText("");
                        PublicationYear_txt.setText("");
                        Language_jComboBox.setSelectedIndex(0);
                        CopiesAvailable_txt.setText("");
                        Genre_txt.setSelectedIndex(0);
                        Book_Format_txt.setSelectedIndex(0);
                    }
                }
                rs.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Members_btn123 = new javax.swing.JButton();
        Books_btn123 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        Edit_Member_btn = new javax.swing.JButton();
        Edit_Books_btn = new javax.swing.JButton();
        Issued_Books_btn = new javax.swing.JButton();
        Return_Books_btn1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Edit_Books_Copies_lbl = new javax.swing.JLabel();
        BookTitle_txt = new javax.swing.JTextField();
        Author_txt = new javax.swing.JTextField();
        Edit_Books_Format_lbl = new javax.swing.JLabel();
        Edit_Books_Author_lbl = new javax.swing.JLabel();
        CopiesAvailable_txt = new javax.swing.JTextField();
        Genre_txt = new javax.swing.JComboBox<>();
        book_Save_btn = new javax.swing.JButton();
        Edit_Books_Genre_lbl = new javax.swing.JLabel();
        Book_Format_txt = new javax.swing.JComboBox<>();
        Close = new javax.swing.JButton();
        Edit_Books_Title_lbl = new javax.swing.JLabel();
        Edit_Books_Lan_lbl = new javax.swing.JLabel();
        Edit_Books_ID_lbl = new javax.swing.JLabel();
        BookID_txt = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        Language_jComboBox = new javax.swing.JComboBox<>();
        publication_year_lbl = new javax.swing.JLabel();
        PublicationYear_txt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(9, 16, 87));
        jPanel1.setForeground(new java.awt.Color(255, 153, 153));

        jLabel1.setBackground(new java.awt.Color(102, 102, 102));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(250, 64, 50));
        jLabel1.setText("Library  ");

        Members_btn123.setBackground(new java.awt.Color(250, 129, 47));
        Members_btn123.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        Members_btn123.setForeground(new java.awt.Color(255, 255, 255));
        Members_btn123.setText("Members");
        Members_btn123.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Members_btn123.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Members_btn123ActionPerformed(evt);
            }
        });

        Books_btn123.setBackground(new java.awt.Color(250, 129, 47));
        Books_btn123.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        Books_btn123.setForeground(new java.awt.Color(255, 255, 255));
        Books_btn123.setText("Books");
        Books_btn123.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Books_btn123.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Books_btn123ActionPerformed(evt);
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Members_btn123, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                            .addComponent(Books_btn123, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Edit_Books_btn, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                            .addComponent(Issued_Books_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addComponent(Books_btn123, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Members_btn123, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Edit_Member_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Edit_Books_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Issued_Books_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Return_Books_btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(2, 76, 170));

        jPanel5.setBackground(new java.awt.Color(0, 107, 255));

        jLabel13.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(250, 129, 47));
        jLabel13.setText("Edit ");

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Books");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        Edit_Books_Copies_lbl.setBackground(new java.awt.Color(255, 51, 51));
        Edit_Books_Copies_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Edit_Books_Copies_lbl.setForeground(new java.awt.Color(255, 255, 255));
        Edit_Books_Copies_lbl.setText("Copies Available");

        BookTitle_txt.setBackground(new java.awt.Color(255, 255, 255));
        BookTitle_txt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        BookTitle_txt.setForeground(new java.awt.Color(255, 51, 51));
        BookTitle_txt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        BookTitle_txt.setCaretColor(new java.awt.Color(255, 51, 51));
        BookTitle_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookTitle_txtActionPerformed(evt);
            }
        });

        Author_txt.setBackground(new java.awt.Color(255, 255, 255));
        Author_txt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Author_txt.setForeground(new java.awt.Color(255, 51, 51));
        Author_txt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        Author_txt.setCaretColor(new java.awt.Color(255, 51, 51));
        Author_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Author_txtActionPerformed(evt);
            }
        });

        Edit_Books_Format_lbl.setBackground(new java.awt.Color(255, 51, 51));
        Edit_Books_Format_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Edit_Books_Format_lbl.setForeground(new java.awt.Color(255, 255, 255));
        Edit_Books_Format_lbl.setText("Format");

        Edit_Books_Author_lbl.setBackground(new java.awt.Color(255, 51, 51));
        Edit_Books_Author_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Edit_Books_Author_lbl.setForeground(new java.awt.Color(255, 255, 255));
        Edit_Books_Author_lbl.setText("Author");

        CopiesAvailable_txt.setBackground(new java.awt.Color(255, 255, 255));
        CopiesAvailable_txt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        CopiesAvailable_txt.setForeground(new java.awt.Color(255, 51, 51));
        CopiesAvailable_txt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        CopiesAvailable_txt.setCaretColor(new java.awt.Color(255, 51, 51));
        CopiesAvailable_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CopiesAvailable_txtActionPerformed(evt);
            }
        });

        Genre_txt.setBackground(new java.awt.Color(255, 255, 255));
        Genre_txt.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        Genre_txt.setForeground(new java.awt.Color(250, 129, 47));
        Genre_txt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Adventure", "African Literature", "Anthology", "Art", "Autobiography", "Biography", "Business", "Children's", "Classic", "Comic Book", "Coming of Age", "Contemporary", "Cookbook", "Crime", "Drama", "Dystopian", "Education", "Epic Poetry", "Erotica", "Essay", "Fairy Tale", "Fantasy", "Feminist Literature", "Folklore", "Gothic", "Graphic Novel", "Health", "Historical Fiction", "History", "Horror", "Humor", "Inspirational", "Journal", "Literary Fiction", "LGBTQ+", "Magic Realism", "Manga", "Medical", "Memoir", "Metaphysical", "Military", "Multicultural", "Music", "Mystery", "Mythology", "New Adult", "Noir", "Paranormal", "Personal Development", "Philosophy", "Photography", "Play", "Poetry", "Political", "Psychology", "Religion", "Romance", "Satire", "Science", "Science Fiction", "Self-help", "Short Story", "Social Science", "Spirituality", "Sports", "Steampunk", "Superhero", "Suspense", "Technology", "Textbook", "Thriller", "Travel", "True Crime", "Urban Fantasy", "Utopian", "War", "Western", "Women's Fiction", "Young Adult" }));
        Genre_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Genre_txtActionPerformed(evt);
            }
        });

        book_Save_btn.setBackground(new java.awt.Color(250, 129, 47));
        book_Save_btn.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        book_Save_btn.setForeground(new java.awt.Color(255, 255, 255));
        book_Save_btn.setText("Save");
        book_Save_btn.setBorder(null);
        book_Save_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                book_Save_btnActionPerformed(evt);
            }
        });

        Edit_Books_Genre_lbl.setBackground(new java.awt.Color(255, 51, 51));
        Edit_Books_Genre_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Edit_Books_Genre_lbl.setForeground(new java.awt.Color(255, 255, 255));
        Edit_Books_Genre_lbl.setText("Genre");

        Book_Format_txt.setBackground(new java.awt.Color(255, 255, 255));
        Book_Format_txt.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        Book_Format_txt.setForeground(new java.awt.Color(250, 129, 47));
        Book_Format_txt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PDF", "Printed Book", "Kindle", "Online reader", "News paper", "Leaflet", "Paper" }));
        Book_Format_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Book_Format_txtActionPerformed(evt);
            }
        });

        Close.setBackground(new java.awt.Color(0, 0, 0));
        Close.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Close.setForeground(new java.awt.Color(250, 129, 47));
        Close.setText("Close");
        Close.setBorder(null);
        Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseActionPerformed(evt);
            }
        });

        Edit_Books_Title_lbl.setBackground(new java.awt.Color(255, 51, 51));
        Edit_Books_Title_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Edit_Books_Title_lbl.setForeground(new java.awt.Color(255, 255, 255));
        Edit_Books_Title_lbl.setText("Book Title");

        Edit_Books_Lan_lbl.setBackground(new java.awt.Color(255, 51, 51));
        Edit_Books_Lan_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Edit_Books_Lan_lbl.setForeground(new java.awt.Color(255, 255, 255));
        Edit_Books_Lan_lbl.setText("Language");

        Edit_Books_ID_lbl.setBackground(new java.awt.Color(255, 51, 51));
        Edit_Books_ID_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Edit_Books_ID_lbl.setForeground(new java.awt.Color(255, 255, 255));
        Edit_Books_ID_lbl.setText("Book ID");

        BookID_txt.setBackground(new java.awt.Color(255, 255, 255));
        BookID_txt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        BookID_txt.setForeground(new java.awt.Color(255, 51, 51));
        BookID_txt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        BookID_txt.setCaretColor(new java.awt.Color(255, 51, 51));
        BookID_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookID_txtActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(2, 76, 170));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 198, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(2, 76, 170));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 194, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        Language_jComboBox.setBackground(new java.awt.Color(255, 255, 255));
        Language_jComboBox.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        Language_jComboBox.setForeground(new java.awt.Color(250, 129, 47));
        Language_jComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "English", "Sinhala", "Mandarin Chinese", "Spanish", "Hindi", "Arabic", "Bengali", "Portuguese", "Russian", "Japanese", "Punjabi", "German", "Korean", "French", "Turkish", "Italian", "Thai", "Dutch", "Greek", "Swedish", "Polish", "Czech", "Hebrew", "Danish", "Finnish", "" }));
        Language_jComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Language_jComboBoxActionPerformed(evt);
            }
        });

        publication_year_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        publication_year_lbl.setForeground(new java.awt.Color(255, 255, 255));
        publication_year_lbl.setText("Publication Year");

        PublicationYear_txt.setBackground(new java.awt.Color(255, 255, 255));
        PublicationYear_txt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        PublicationYear_txt.setForeground(new java.awt.Color(0, 102, 255));
        PublicationYear_txt.setCaretColor(new java.awt.Color(0, 102, 255));
        PublicationYear_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PublicationYear_txtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(137, 137, 137)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Edit_Books_Author_lbl)
                            .addComponent(Edit_Books_Title_lbl)
                            .addComponent(Edit_Books_Lan_lbl)
                            .addComponent(Edit_Books_ID_lbl)
                            .addComponent(Edit_Books_Format_lbl)
                            .addComponent(Edit_Books_Genre_lbl)
                            .addComponent(Edit_Books_Copies_lbl))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Genre_txt, 0, 257, Short.MAX_VALUE)
                            .addComponent(BookTitle_txt, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Author_txt, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CopiesAvailable_txt)
                            .addComponent(Book_Format_txt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BookID_txt)
                            .addComponent(Language_jComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(publication_year_lbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(PublicationYear_txt)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(book_Save_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Close, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Edit_Books_ID_lbl)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(book_Save_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Close, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(BookID_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(BookTitle_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Edit_Books_Title_lbl))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Author_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Edit_Books_Author_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Language_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Edit_Books_Lan_lbl))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CopiesAvailable_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Edit_Books_Copies_lbl))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Genre_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Edit_Books_Genre_lbl))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Book_Format_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Edit_Books_Format_lbl))))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(publication_year_lbl)
                            .addComponent(PublicationYear_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(228, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
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

    private void Members_btn123ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Members_btn123ActionPerformed
        setVisible(false);
        try {
            new View().setVisible(true);

        } catch (ParseException ex) {
            Logger.getLogger(EditBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_Members_btn123ActionPerformed

    private void Books_btn123ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Books_btn123ActionPerformed
        setVisible(false);
        try {
            new View().setVisible(true);

        } catch (ParseException ex) {
            Logger.getLogger(EditBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_Books_btn123ActionPerformed

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

    private void Author_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Author_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Author_txtActionPerformed

    private void CopiesAvailable_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CopiesAvailable_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CopiesAvailable_txtActionPerformed

    private void book_Save_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_book_Save_btnActionPerformed
        try {
            // Get values from form fields
            String bookId = BookID_txt.getText();
            String bookTitle = BookTitle_txt.getText();
            String author = Author_txt.getText();
            //        String publicationYear = PublicationYear.getText();
            String language = Language_jComboBox.getSelectedItem().toString();
            String copiesAvailable = CopiesAvailable_txt.getText();
            String genre = Genre_txt.getSelectedItem().toString();
            String format = Book_Format_txt.getSelectedItem().toString();

            // Basic validation
            if (bookId.isEmpty() || bookTitle.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Book ID, Title and Author are required fields!");
                return;
            }

            try ( // Check if connection is successful
                    Connection con = dbController.getConnection()) {
                if (con == null) {
                    JOptionPane.showMessageDialog(this, "Database connection failed!");
                    return;
                }

                // SQL for updating an existing record
                String sql = "UPDATE books SET book_title = ?, author = ?,"
                        + "language = ?, copies_available = ?, genre = ?, format = ? WHERE book_id = ?";
                // Set values in prepared statement
                try (PreparedStatement pst = con.prepareStatement(sql)) {
                    // Set values in prepared statement
                    pst.setString(1, bookTitle);
                    pst.setString(2, author);
                    //        pst.setString(3, publicationYear);
                    pst.setString(3, language);
                    pst.setString(4, copiesAvailable);
                    pst.setString(5, genre);
                    pst.setString(6, format);
                    pst.setString(7, bookId);
                    // Execute the update
                    int result = pst.executeUpdate();
                    if (result > 0) {
                        JOptionPane.showMessageDialog(this, "Book Updated Successfully!");
                        // Clear fields
                        BookID_txt.setText("");
                        BookTitle_txt.setText("");
                        Author_txt.setText("");
                        //            PublicationYear.setText("");
                        CopiesAvailable_txt.setText("");
                        // Safely set combo box values
                        try {
                            Language_jComboBox.setSelectedIndex(0);
                            Genre_txt.setSelectedIndex(0);
                            Book_Format_txt.setSelectedIndex(0);
                        } catch (Exception e) {
                            // Ignore combo box errors
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to update book!");
                    }
                    // Close the prepared statement and connection
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_book_Save_btnActionPerformed

    private void CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseActionPerformed
        setVisible(false);
        try {
            new View().setVisible(true);
        } catch (ParseException ex) {
            Logger.getLogger(EditBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_CloseActionPerformed

    private void BookID_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookID_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BookID_txtActionPerformed

    private void BookTitle_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookTitle_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BookTitle_txtActionPerformed

    private void Language_jComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Language_jComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Language_jComboBoxActionPerformed

    private void Genre_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Genre_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Genre_txtActionPerformed

    private void Book_Format_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Book_Format_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Book_Format_txtActionPerformed

    private void PublicationYear_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PublicationYear_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PublicationYear_txtActionPerformed

    private void Issued_Books_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Issued_Books_btnActionPerformed
        setVisible(false);
        IssuedBooks IssuedBooksFrame = new IssuedBooks(DBController.getInstance());
        IssuedBooksFrame.setVisible(true);
    }//GEN-LAST:event_Issued_Books_btnActionPerformed

    private void Return_Books_btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Return_Books_btn1ActionPerformed
        setVisible(false);
        ReturnedBooks ReturnedBooksFrame = new ReturnedBooks(DBController.getInstance());
        ReturnedBooksFrame.setVisible(true);
    }//GEN-LAST:event_Return_Books_btn1ActionPerformed

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
            java.util.logging.Logger.getLogger(EditBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DBController dbController = DBController.getInstance();
                EditBooks editBooksFrame = new EditBooks(dbController);
                editBooksFrame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Author_txt;
    private javax.swing.JTextField BookID_txt;
    private javax.swing.JTextField BookTitle_txt;
    private javax.swing.JComboBox<String> Book_Format_txt;
    private javax.swing.JButton Books_btn123;
    private javax.swing.JButton Close;
    private javax.swing.JTextField CopiesAvailable_txt;
    private javax.swing.JLabel Edit_Books_Author_lbl;
    private javax.swing.JLabel Edit_Books_Copies_lbl;
    private javax.swing.JLabel Edit_Books_Format_lbl;
    private javax.swing.JLabel Edit_Books_Genre_lbl;
    private javax.swing.JLabel Edit_Books_ID_lbl;
    private javax.swing.JLabel Edit_Books_Lan_lbl;
    private javax.swing.JLabel Edit_Books_Title_lbl;
    private javax.swing.JButton Edit_Books_btn;
    private javax.swing.JButton Edit_Member_btn;
    private javax.swing.JComboBox<String> Genre_txt;
    private javax.swing.JButton Issued_Books_btn;
    private javax.swing.JComboBox<String> Language_jComboBox;
    private javax.swing.JButton Members_btn123;
    private javax.swing.JTextField PublicationYear_txt;
    private javax.swing.JButton Return_Books_btn1;
    private javax.swing.JButton book_Save_btn;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel publication_year_lbl;
    // End of variables declaration//GEN-END:variables

}
