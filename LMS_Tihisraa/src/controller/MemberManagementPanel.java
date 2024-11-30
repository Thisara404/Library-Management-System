package controller;

import Controller.DBController;
import java.text.ParseException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class MemberManagementPanel extends JPanel {

    private DBController dbController;
    private JTable member_table;

    public MemberManagementPanel(DBController dbController) {
        this.dbController = dbController;
        initComponents();
    }

    private void initComponents() {
        // Initialize the table and other UI components

        member_table = new JTable();
        member_table.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Name", "Contact", "Address", "Course", "Expiry Date"
                }
        ));
        // Add the table to the panel or frame

        fetchAndDisplayMembers();
    }

    public void fetchAndDisplayMembers() {
        try {
            if (dbController == null) {
                throw new SQLException("Database controller not initialized");
            }
            try {
                var members = dbController.getAllMembers();
                var model = (DefaultTableModel) member_table.getModel();
                model.setRowCount(0);

                for (var member : members) {
                    Object[] row = {
                        member.getName(),
                        member.getContact(),
                        member.getAddress(),
                        member.getMembershipCard().getCardNumber(),
                        member.getMembershipCard().getExpiryDate()
                    };
                    model.addRow(row);
                }
            } catch (SQLException | ParseException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error fetching member data: " + e.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
                System.err.println("Database error: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error fetching data: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
