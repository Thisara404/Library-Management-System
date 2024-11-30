package view;

import Controller.DBController;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class EditMember extends javax.swing.JFrame {

    private DBController dbController;

    public EditMember(DBController dbController) {
        this.dbController = dbController;
        initComponents();
        centerWindow();
        setupMemberEditListener();
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

    private void setupMemberEditListener() {
        Name_member_txt.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fetchMemberDetails();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fetchMemberDetails();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                fetchMemberDetails();
            }
        });
    }

     private void fetchMemberDetails() {
//        int id = ID_member_txt1.getText();
        String memberName = Name_member_txt.getText();

        try {
            try (Connection con = dbController.getConnection()) {
                if (con == null) {
                    JOptionPane.showMessageDialog(this, "Database connection failed!");
                    return;
                }

                String sql = "SELECT name, contact, address, course, expire_date FROM members WHERE name = ?";

                try (PreparedStatement pst = con.prepareStatement(sql)) {
                    pst.setString(1, memberName);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        // Populate fields
                        Address_member_txt.setText(rs.getString("address"));
                        Contact_Member_txt.setText(rs.getString("contact"));
                        Course_member_txt.setText(rs.getString("course"));

                        // Parse and set date
                        String expireDate = rs.getString("expire_date");
                        if (expireDate != null) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            publication_year_jDateChooser1.setDate(sdf.parse(expireDate));
                        }
                    } else {
                        // Clear fields if no member found
                        Address_member_txt.setText("");
                        Contact_Member_txt.setText("");
                        Course_member_txt.setText("");
                        publication_year_jDateChooser1.setDate(null);
                    }
                }
            }
        } catch (SQLException | ParseException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }


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
        Member_Save_btn = new javax.swing.JButton();
        Member_Close_btn = new javax.swing.JButton();
        Address_member_txt = new javax.swing.JTextField();
        Contact_Member_txt = new javax.swing.JTextField();
        Name_member_txt = new javax.swing.JTextField();
        Edit_Member_Expire_date_lbl = new javax.swing.JLabel();
        Course_member_txt = new javax.swing.JTextField();
        Edit_Member_Adress_lbl = new javax.swing.JLabel();
        Edit_Member_Name_lbl = new javax.swing.JLabel();
        Edit_Member_Conact_lbl = new javax.swing.JLabel();
        Edit_Member_Course_lbl = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        publication_year_jDateChooser1 = new com.toedter.calendar.JDateChooser();
        Edit_Member_id_lbl1 = new javax.swing.JLabel();
        ID_member_txt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1394, 752));

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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Books_btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                            .addComponent(Members_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                        .addComponent(Return_Books_btn1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Issued_Books_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addComponent(Members_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Books_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        Member_Save_btn.setBackground(new java.awt.Color(250, 129, 47));
        Member_Save_btn.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Member_Save_btn.setForeground(new java.awt.Color(255, 255, 255));
        Member_Save_btn.setText("Save");
        Member_Save_btn.setBorder(null);
        Member_Save_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Member_Save_btnActionPerformed(evt);
            }
        });

        Member_Close_btn.setBackground(new java.awt.Color(0, 0, 0));
        Member_Close_btn.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Member_Close_btn.setForeground(new java.awt.Color(250, 129, 47));
        Member_Close_btn.setText("Close");
        Member_Close_btn.setBorder(null);
        Member_Close_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Member_Close_btnActionPerformed(evt);
            }
        });

        Address_member_txt.setBackground(new java.awt.Color(255, 255, 255));
        Address_member_txt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Address_member_txt.setForeground(new java.awt.Color(255, 51, 51));
        Address_member_txt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        Address_member_txt.setCaretColor(new java.awt.Color(255, 51, 51));
        Address_member_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Address_member_txtActionPerformed(evt);
            }
        });

        Contact_Member_txt.setBackground(new java.awt.Color(255, 255, 255));
        Contact_Member_txt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Contact_Member_txt.setForeground(new java.awt.Color(255, 51, 51));
        Contact_Member_txt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        Contact_Member_txt.setCaretColor(new java.awt.Color(255, 51, 51));
        Contact_Member_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Contact_Member_txtActionPerformed(evt);
            }
        });

        Name_member_txt.setBackground(new java.awt.Color(255, 255, 255));
        Name_member_txt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Name_member_txt.setForeground(new java.awt.Color(255, 51, 51));
        Name_member_txt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        Name_member_txt.setCaretColor(new java.awt.Color(255, 51, 51));

        Edit_Member_Expire_date_lbl.setBackground(new java.awt.Color(255, 51, 51));
        Edit_Member_Expire_date_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Edit_Member_Expire_date_lbl.setForeground(new java.awt.Color(255, 255, 255));
        Edit_Member_Expire_date_lbl.setText("Expire Date");

        Course_member_txt.setBackground(new java.awt.Color(255, 255, 255));
        Course_member_txt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Course_member_txt.setForeground(new java.awt.Color(255, 51, 51));
        Course_member_txt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        Course_member_txt.setCaretColor(new java.awt.Color(255, 51, 51));
        Course_member_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Course_member_txtActionPerformed(evt);
            }
        });

        Edit_Member_Adress_lbl.setBackground(new java.awt.Color(255, 51, 51));
        Edit_Member_Adress_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Edit_Member_Adress_lbl.setForeground(new java.awt.Color(255, 255, 255));
        Edit_Member_Adress_lbl.setText("Address");

        Edit_Member_Name_lbl.setBackground(new java.awt.Color(255, 51, 51));
        Edit_Member_Name_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Edit_Member_Name_lbl.setForeground(new java.awt.Color(255, 255, 255));
        Edit_Member_Name_lbl.setText("Name");

        Edit_Member_Conact_lbl.setBackground(new java.awt.Color(255, 51, 51));
        Edit_Member_Conact_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Edit_Member_Conact_lbl.setForeground(new java.awt.Color(255, 255, 255));
        Edit_Member_Conact_lbl.setText("Contact");

        Edit_Member_Course_lbl.setBackground(new java.awt.Color(255, 51, 51));
        Edit_Member_Course_lbl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Edit_Member_Course_lbl.setForeground(new java.awt.Color(255, 255, 255));
        Edit_Member_Course_lbl.setText("Course");

        jPanel4.setBackground(new java.awt.Color(0, 107, 255));

        jLabel12.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(250, 129, 47));
        jLabel12.setText("Edit ");

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Member");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(19, 19, 19))
        );

        jPanel3.setBackground(new java.awt.Color(2, 76, 170));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 182, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(2, 76, 170));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 545, Short.MAX_VALUE)
        );

        publication_year_jDateChooser1.setBackground(new java.awt.Color(102, 102, 102));
        publication_year_jDateChooser1.setForeground(new java.awt.Color(250, 129, 47));
        publication_year_jDateChooser1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                publication_year_jDateChooser1AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        Edit_Member_id_lbl1.setBackground(new java.awt.Color(255, 51, 51));
        Edit_Member_id_lbl1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Edit_Member_id_lbl1.setForeground(new java.awt.Color(255, 255, 255));
        Edit_Member_id_lbl1.setText("Member ID");

        ID_member_txt.setBackground(new java.awt.Color(255, 255, 255));
        ID_member_txt.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ID_member_txt.setForeground(new java.awt.Color(255, 51, 51));
        ID_member_txt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        ID_member_txt.setCaretColor(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Edit_Member_Conact_lbl)
                                    .addComponent(Edit_Member_Adress_lbl)
                                    .addComponent(Edit_Member_Course_lbl))
                                .addGap(42, 42, 42)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Contact_Member_txt)
                                    .addComponent(Course_member_txt)
                                    .addComponent(Address_member_txt)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(Edit_Member_Expire_date_lbl)
                                .addGap(18, 18, 18)
                                .addComponent(publication_year_jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)))
                        .addGap(6, 6, 6)
                        .addComponent(Member_Close_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Edit_Member_Name_lbl)
                            .addComponent(Edit_Member_id_lbl1))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ID_member_txt)
                            .addComponent(Name_member_txt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Member_Save_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Edit_Member_id_lbl1)
                            .addComponent(ID_member_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Edit_Member_Name_lbl)
                                    .addComponent(Name_member_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Edit_Member_Expire_date_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(publication_year_jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Edit_Member_Conact_lbl)
                                    .addComponent(Contact_Member_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Course_member_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Edit_Member_Course_lbl))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Edit_Member_Adress_lbl)
                                    .addComponent(Address_member_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(Member_Save_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Member_Close_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Books_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Books_btnActionPerformed
        setVisible(false);
        try {
            new View().setVisible(true);
//        if (Book_Manager.isVisible()) {
//            Book_Manager.setVisible(false);
//            Memeber_manager.setVisible(true);
//        } else {
//            Memeber_manager.setVisible(false);
//            Book_Manager.setVisible(true);
//        }
        } catch (ParseException ex) {
            Logger.getLogger(EditMember.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_Books_btnActionPerformed

    private void Members_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Members_btnActionPerformed
        setVisible(false);
        try {
            new View().setVisible(true);
//        if (Memeber_manager.isVisible()) {
//            Memeber_manager.setVisible(false);
//            Book_Manager.setVisible(true);
//        } else {
//            Book_Manager.setVisible(false);
//            Memeber_manager.setVisible(true);
//        }
        } catch (ParseException ex) {
            Logger.getLogger(EditMember.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_Members_btnActionPerformed

    private void Edit_Member_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Edit_Member_btnActionPerformed
        setVisible(false);
        EditMember editMemberFrame = new EditMember(DBController.getInstance());
        editMemberFrame.setVisible(true);
    }//GEN-LAST:event_Edit_Member_btnActionPerformed


    private void Member_Save_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Member_Save_btnActionPerformed
        try {
            // Get values from form fields
            String memberId = ID_member_txt.getText();
            String memberName = Name_member_txt.getText(); // Assuming this is the name field
            String address = Address_member_txt.getText();
            String contactNumber = Contact_Member_txt.getText();
            String email = Course_member_txt.getText(); // Assuming this is used for email or course

            // Get date from JDateChooser
            java.util.Date expiryDate = publication_year_jDateChooser1.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedExpiryDate = (expiryDate != null) ? sdf.format(expiryDate) : null;

            // Basic validation
            if (memberId.isEmpty() || memberName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Member ID and Name are required fields!");
                return;
            }

            try (Connection con = dbController.getConnection()) {
                if (con == null) {
                    JOptionPane.showMessageDialog(this, "Database connection failed!");
                    return;
                }

                // SQL for updating an existing member record
                String sql = "UPDATE members SET id = ?, name = ?, contact = ?, address = ?, "
                        + "course = ?, expire_date = ? WHERE name = ?";

                try (PreparedStatement pst = con.prepareStatement(sql)) {
                    pst.setString(1, memberId);
                    pst.setString(2, memberName);
                    pst.setString(3, contactNumber);
                    pst.setString(4, address);
                    pst.setString(5, email);
                    pst.setString(6, formattedExpiryDate);
//                    pst.setString(6, memberId);

                    // Execute the update
                    int result = pst.executeUpdate();
                    if (result > 0) {
                        JOptionPane.showMessageDialog(this, "Member Updated Successfully!");

                        // Clear fields
                        ID_member_txt.setText("");
                        Name_member_txt.setText("");
                        Address_member_txt.setText("");
                        Contact_Member_txt.setText("");
                        Course_member_txt.setText("");
                        publication_year_jDateChooser1.setDate(null);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to update member!");
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_Member_Save_btnActionPerformed

    private void Member_Close_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Member_Close_btnActionPerformed
        setVisible(false);
        try {
            new View().setVisible(true);
        } catch (ParseException ex) {
            Logger.getLogger(EditMember.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_Member_Close_btnActionPerformed

    private void Address_member_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Address_member_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Address_member_txtActionPerformed

    private void Course_member_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Course_member_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Course_member_txtActionPerformed

    private void publication_year_jDateChooser1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_publication_year_jDateChooser1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_publication_year_jDateChooser1AncestorAdded

    private void Contact_Member_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Contact_Member_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Contact_Member_txtActionPerformed

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
            java.util.logging.Logger.getLogger(EditMember.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditMember.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditMember.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditMember.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DBController dbController = DBController.getInstance();
                EditMember editMemberFrame = new EditMember(dbController);
                editMemberFrame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Address_member_txt;
    private javax.swing.JButton Books_btn;
    private javax.swing.JTextField Contact_Member_txt;
    private javax.swing.JTextField Course_member_txt;
    private javax.swing.JButton Edit_Books_btn;
    private javax.swing.JLabel Edit_Member_Adress_lbl;
    private javax.swing.JLabel Edit_Member_Conact_lbl;
    private javax.swing.JLabel Edit_Member_Course_lbl;
    private javax.swing.JLabel Edit_Member_Expire_date_lbl;
    private javax.swing.JLabel Edit_Member_Name_lbl;
    private javax.swing.JButton Edit_Member_btn;
    private javax.swing.JLabel Edit_Member_id_lbl1;
    private javax.swing.JTextField ID_member_txt;
    private javax.swing.JButton Issued_Books_btn;
    private javax.swing.JButton Member_Close_btn;
    private javax.swing.JButton Member_Save_btn;
    private javax.swing.JButton Members_btn;
    private javax.swing.JTextField Name_member_txt;
    private javax.swing.JButton Return_Books_btn1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private com.toedter.calendar.JDateChooser publication_year_jDateChooser1;
    // End of variables declaration//GEN-END:variables
}
