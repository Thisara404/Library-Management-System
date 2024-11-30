package view;

import Controller.DBController;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CHAMA COMPUTERS
 */
public class ReturnedBooks extends javax.swing.JFrame {

    /**
     * Creates new form ReturnedBooks
     */
    private DBController dbController;

    public ReturnedBooks(DBController instance) {
        initComponents();
        centerWindow();
        fetchAndDisplayIssuedBooks();
        fetchAndDisplayReturnedBooks();
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

    // 7. Method to fetch and display issued books
    public void fetchAndDisplayIssuedBooks() {
        try {
            Connection conn = DBController.getInstance().getConnection();
            String sql = "SELECT member_id, member_name, book_id, book_title, "
                    + "issued_date, due_date FROM issued_books";

            try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

                DefaultTableModel model = (DefaultTableModel) Issued_books_table.getModel();
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

    public void fetchAndDisplayReturnedBooks() {
        try {
            Connection conn = DBController.getInstance().getConnection();
            String sql = "SELECT member_id, member_name, book_id, book_title, "
                    + "damage_fees, return_date, due_date, issue_date, condition_on_return "
                    + "FROM returned_books";

            try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

                DefaultTableModel model = (DefaultTableModel) Returned_books_table.getModel();
                model.setRowCount(0); // Clear existing rows

                while (rs.next()) {
                    Object[] row = {
                        rs.getString("member_id"),
                        rs.getString("member_name"),
                        rs.getString("book_id"),
                        rs.getString("book_title"),
                        rs.getDouble("damage_fees"),
                        rs.getDate("return_date"),
                        rs.getDate("due_date"),
                        rs.getDate("issue_date"),
                        rs.getString("condition_on_return")
                    };
                    model.addRow(row);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error fetching returned books: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void initializeComboBoxes() {
        // Setup damage fees options
        String[] damageFeeOptions = {
            "0.00",
            "100.00",
            "200.00",
            "300.00",
            "400.00",
            "500.00"
        };
        DamegeFees.setModel(new javax.swing.DefaultComboBoxModel<>(damageFeeOptions));

        // Setup condition options
        String[] conditionOptions = {
            "Good",
            "Slightly Damaged",
            "Damaged",
            "Heavily Damaged",
            "Lost"
        };
        ConditionOnReturn.setModel(new javax.swing.DefaultComboBoxModel<>(conditionOptions));
    }

    private void clearFields() {
        MemberID_txt.setText("");
        MemberName_txt.setText("");
        Book_id_txt.setText("");
        BookTitle_txt.setText("");
        DamegeFees.setSelectedIndex(0);  // Set to first option (0.00)
        returned_dateChooser.setDate(null);
        DueDate_datechooser.setDate(null);
        Issue_dateChooser.setDate(null);
        ConditionOnReturn.setSelectedIndex(0);  // Set to first option (Good)
    }

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
        Return_Books_btn1 = new javax.swing.JButton();
        Issued_Books_btn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        returned_lbl = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Returned_books_table = new javax.swing.JTable();
        mem_id_lbl = new javax.swing.JLabel();
        MemberID_txt = new javax.swing.JTextField();
        member_name_lbl = new javax.swing.JLabel();
        MemberName_txt = new javax.swing.JTextField();
        issue_date_lbl = new javax.swing.JLabel();
        returned_dateChooser = new com.toedter.calendar.JDateChooser();
        DueDate_datechooser = new com.toedter.calendar.JDateChooser();
        due_date_lbl = new javax.swing.JLabel();
        book_id_lbl = new javax.swing.JLabel();
        Book_id_txt = new javax.swing.JTextField();
        booktitle_lbl = new javax.swing.JLabel();
        BookTitle_txt = new javax.swing.JTextField();
        Issue_dateChooser = new com.toedter.calendar.JDateChooser();
        ConditionOnReturn = new javax.swing.JComboBox<>();
        DamegeFees = new javax.swing.JComboBox<>();
        return_date_lbl = new javax.swing.JLabel();
        condition_lbl = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        save = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Issued_books_table = new javax.swing.JTable();

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
                        .addComponent(Members_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Issued_Books_btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Edit_Books_btn, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                            .addComponent(Return_Books_btn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addContainerGap(147, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(2, 76, 170));

        returned_lbl.setBackground(new java.awt.Color(0, 0, 0));
        returned_lbl.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        returned_lbl.setForeground(new java.awt.Color(250, 129, 47));
        returned_lbl.setText("Returned");

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel5.setText("books");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(133, 133, 133))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(returned_lbl)
                        .addGap(31, 31, 31))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(returned_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(2, 76, 170));

        Returned_books_table.setBackground(new java.awt.Color(255, 247, 209));
        Returned_books_table.setForeground(new java.awt.Color(0, 0, 204));
        Returned_books_table.setModel(new javax.swing.table.DefaultTableModel(
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
        Returned_books_table.setSelectionForeground(new java.awt.Color(0, 0, 204));
        Returned_books_table.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                Returned_books_tableAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(Returned_books_table);

        mem_id_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        mem_id_lbl.setForeground(new java.awt.Color(255, 255, 255));
        mem_id_lbl.setText("Member ID");

        MemberID_txt.setBackground(new java.awt.Color(255, 255, 255));
        MemberID_txt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        MemberID_txt.setForeground(new java.awt.Color(0, 102, 255));
        MemberID_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MemberID_txtActionPerformed(evt);
            }
        });

        member_name_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        member_name_lbl.setForeground(new java.awt.Color(255, 255, 255));
        member_name_lbl.setText("Member Name ");

        MemberName_txt.setBackground(new java.awt.Color(255, 255, 255));
        MemberName_txt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        MemberName_txt.setForeground(new java.awt.Color(0, 102, 255));
        MemberName_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MemberName_txtActionPerformed(evt);
            }
        });

        issue_date_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        issue_date_lbl.setForeground(new java.awt.Color(255, 255, 255));
        issue_date_lbl.setText("Issue Date");

        returned_dateChooser.setBackground(new java.awt.Color(255, 255, 255));
        returned_dateChooser.setForeground(new java.awt.Color(0, 102, 255));

        DueDate_datechooser.setBackground(new java.awt.Color(255, 255, 255));
        DueDate_datechooser.setForeground(new java.awt.Color(0, 102, 255));

        due_date_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        due_date_lbl.setForeground(new java.awt.Color(255, 255, 255));
        due_date_lbl.setText("Due Date");

        book_id_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        book_id_lbl.setForeground(new java.awt.Color(255, 255, 255));
        book_id_lbl.setText("Book ID");

        Book_id_txt.setBackground(new java.awt.Color(255, 255, 255));
        Book_id_txt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Book_id_txt.setForeground(new java.awt.Color(0, 102, 255));
        Book_id_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Book_id_txtActionPerformed(evt);
            }
        });

        booktitle_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        booktitle_lbl.setForeground(new java.awt.Color(255, 255, 255));
        booktitle_lbl.setText("Book Title");

        BookTitle_txt.setBackground(new java.awt.Color(255, 255, 255));
        BookTitle_txt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        BookTitle_txt.setForeground(new java.awt.Color(0, 102, 255));

        Issue_dateChooser.setBackground(new java.awt.Color(255, 255, 255));
        Issue_dateChooser.setForeground(new java.awt.Color(0, 102, 255));

        ConditionOnReturn.setBackground(new java.awt.Color(255, 255, 255));
        ConditionOnReturn.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ConditionOnReturn.setForeground(new java.awt.Color(0, 102, 255));
        ConditionOnReturn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Good", "Slightly Damaged", "Heavily Damaged", "Missing Pages", "Lost" }));
        ConditionOnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConditionOnReturnActionPerformed(evt);
            }
        });

        DamegeFees.setBackground(new java.awt.Color(255, 255, 255));
        DamegeFees.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        DamegeFees.setForeground(new java.awt.Color(0, 102, 255));
        DamegeFees.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Good – Rs.0", "Slightly Damaged – Rs.500 to Rs.1500", "Heavily Damaged – Rs.2000 to Rs.3000", "Missing Pages – Rs.3500 to Rs.4000", "Lost – Full replacement cost" }));
        DamegeFees.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DamegeFeesActionPerformed(evt);
            }
        });

        return_date_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        return_date_lbl.setForeground(new java.awt.Color(255, 255, 255));
        return_date_lbl.setText("Return Date");

        condition_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        condition_lbl.setForeground(new java.awt.Color(255, 255, 255));
        condition_lbl.setText("Condition on Return");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Damage Fees");

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

        Issued_books_table.setBackground(new java.awt.Color(255, 247, 209));
        Issued_books_table.setForeground(new java.awt.Color(0, 0, 204));
        Issued_books_table.setModel(new javax.swing.table.DefaultTableModel(
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
        Issued_books_table.setSelectionForeground(new java.awt.Color(0, 0, 204));
        Issued_books_table.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                Issued_books_tableAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane2.setViewportView(Issued_books_table);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mem_id_lbl)
                    .addComponent(book_id_lbl)
                    .addComponent(member_name_lbl)
                    .addComponent(booktitle_lbl)
                    .addComponent(jLabel8))
                .addGap(67, 67, 67)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(MemberID_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(MemberName_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(DamegeFees, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BookTitle_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Book_id_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(due_date_lbl)
                    .addComponent(condition_lbl)
                    .addComponent(return_date_lbl)
                    .addComponent(issue_date_lbl))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Issue_dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ConditionOnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(returned_dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(DueDate_datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(42, 42, 42)
                .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mem_id_lbl)
                            .addComponent(MemberID_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(return_date_lbl))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(MemberName_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(member_name_lbl)
                            .addComponent(due_date_lbl))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Book_id_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(book_id_lbl))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(BookTitle_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(booktitle_lbl))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(DamegeFees, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(issue_date_lbl)
                                .addGap(27, 27, 27)
                                .addComponent(condition_lbl))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(returned_dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(DueDate_datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Issue_dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ConditionOnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Books_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Books_btnActionPerformed
//        if (Book_Manager.isVisible()) {
//            Book_Manager.setVisible(false);
//            Memeber_manager.setVisible(true);
//        }
        //        else {
        //            Memeber_manager.setVisible(false);
        //            Book_Manager.setVisible(true);
        //        }
    }//GEN-LAST:event_Books_btnActionPerformed

    private void Members_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Members_btnActionPerformed
//        if (Memeber_manager.isVisible()) {
//            Memeber_manager.setVisible(false);
//            Book_Manager.setVisible(true);
//        }
        //        else {
        //            Book_Manager.setVisible(false);
        //            Memeber_manager.setVisible(true);
        //        }
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

    private void Returned_books_tableAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_Returned_books_tableAncestorAdded
        //        fetchAndDisplayMembers();
//        fetchAndDisplayMembers("active");
    }//GEN-LAST:event_Returned_books_tableAncestorAdded

    private void MemberID_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MemberID_txtActionPerformed

    }//GEN-LAST:event_MemberID_txtActionPerformed

    private void MemberName_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MemberName_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MemberName_txtActionPerformed

    private void Book_id_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Book_id_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Book_id_txtActionPerformed

    private void ConditionOnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConditionOnReturnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ConditionOnReturnActionPerformed

    private void DamegeFeesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DamegeFeesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DamegeFeesActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        try {
            // First validate that we have all required fields
            if (MemberID_txt.getText().trim().isEmpty()
                    || MemberName_txt.getText().trim().isEmpty()
                    || Book_id_txt.getText().trim().isEmpty()
                    || BookTitle_txt.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Please fill in all required fields",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Connection conn = DBController.getInstance().getConnection();

            // First verify if this is a valid issued book
            String checkIssuedSQL = "SELECT * FROM issued_books WHERE member_id = ? AND book_id = ? AND book_title = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkIssuedSQL)) {
                checkStmt.setString(1, MemberID_txt.getText().trim());
                checkStmt.setString(2, Book_id_txt.getText().trim());
                checkStmt.setString(3, BookTitle_txt.getText().trim());

                ResultSet rs = checkStmt.executeQuery();
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this,
                            "This book was not issued to this member or the details don't match.",
                            "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // If we get here, the book was issued to this member
            String sql = "INSERT INTO returned_books (member_id, member_name, book_id, "
                    + "book_title, damage_fees, return_date, due_date, issue_date, condition_on_return) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Get the damage fee value from the selected item
                String selectedFee = DamegeFees.getSelectedItem().toString();
                // Extract the number from "Good - Rs.0" format
                double damageFee = 0.0;
                if (selectedFee.contains("Rs.")) {
                    damageFee = Double.parseDouble(selectedFee.split("Rs.")[1].trim());
                }

                pstmt.setString(1, MemberID_txt.getText().trim());
                pstmt.setString(2, MemberName_txt.getText().trim());
                pstmt.setString(3, Book_id_txt.getText().trim());
                pstmt.setString(4, BookTitle_txt.getText().trim());
                pstmt.setDouble(5, damageFee);

                // Convert the date chooser values to java.sql.Date
                java.sql.Date returnDate = new java.sql.Date(returned_dateChooser.getDate().getTime());
                java.sql.Date dueDate = new java.sql.Date(DueDate_datechooser.getDate().getTime());
                java.sql.Date issueDate = new java.sql.Date(Issue_dateChooser.getDate().getTime());

                pstmt.setDate(6, returnDate);
                pstmt.setDate(7, dueDate);
                pstmt.setDate(8, issueDate);
                pstmt.setString(9, ConditionOnReturn.getSelectedItem().toString());

                pstmt.executeUpdate();

                // After successful return, delete from issued_books
                String deleteIssuedSQL = "DELETE FROM issued_books WHERE member_id = ? AND book_id = ?";
                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteIssuedSQL)) {
                    deleteStmt.setString(1, MemberID_txt.getText().trim());
                    deleteStmt.setString(2, Book_id_txt.getText().trim());
                    deleteStmt.executeUpdate();
                }

                JOptionPane.showMessageDialog(this,
                        "Book return recorded successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Refresh the table
                fetchAndDisplayReturnedBooks();

                // Clear the form fields
                clearFields();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error saving returned book: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_saveActionPerformed

    private void Issued_books_tableAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_Issued_books_tableAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_Issued_books_tableAncestorAdded

    private void Return_Books_btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Return_Books_btn1ActionPerformed
        setVisible(false);
        ReturnedBooks ReturnedBooksFrame = new ReturnedBooks(DBController.getInstance());
        ReturnedBooksFrame.setVisible(true);
    }//GEN-LAST:event_Return_Books_btn1ActionPerformed

    private void Issued_Books_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Issued_Books_btnActionPerformed
        setVisible(false);
        IssuedBooks IssuedBooksFrame = new IssuedBooks(DBController.getInstance());
        IssuedBooksFrame.setVisible(true);
    }//GEN-LAST:event_Issued_Books_btnActionPerformed

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
            java.util.logging.Logger.getLogger(ReturnedBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReturnedBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReturnedBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReturnedBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReturnedBooks(DBController.getInstance()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BookTitle_txt;
    private javax.swing.JTextField Book_id_txt;
    private javax.swing.JButton Books_btn;
    private javax.swing.JComboBox<String> ConditionOnReturn;
    private javax.swing.JComboBox<String> DamegeFees;
    private com.toedter.calendar.JDateChooser DueDate_datechooser;
    private javax.swing.JButton Edit_Books_btn;
    private javax.swing.JButton Edit_Member_btn;
    private com.toedter.calendar.JDateChooser Issue_dateChooser;
    private javax.swing.JButton Issued_Books_btn;
    private javax.swing.JTable Issued_books_table;
    private javax.swing.JTextField MemberID_txt;
    private javax.swing.JTextField MemberName_txt;
    private javax.swing.JButton Members_btn;
    private javax.swing.JButton Return_Books_btn1;
    private javax.swing.JTable Returned_books_table;
    private javax.swing.JLabel book_id_lbl;
    private javax.swing.JLabel booktitle_lbl;
    private javax.swing.JLabel condition_lbl;
    private javax.swing.JLabel due_date_lbl;
    private javax.swing.JLabel issue_date_lbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel mem_id_lbl;
    private javax.swing.JLabel member_name_lbl;
    private javax.swing.JLabel return_date_lbl;
    private com.toedter.calendar.JDateChooser returned_dateChooser;
    private javax.swing.JLabel returned_lbl;
    private javax.swing.JButton save;
    // End of variables declaration//GEN-END:variables
}
