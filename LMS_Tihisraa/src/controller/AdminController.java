package controller;

import Controller.DBController;
import model.Member;
import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class AdminController {
    private DBController dbController;
    private Connection connection;

    public AdminController() {
        try {
            dbController = DBController.getInstance();
            connection = dbController.getConnection();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to initialize AdminController: " + e.getMessage());
        }
    }

    public void addMember(Member member) {
        int response = JOptionPane.showConfirmDialog(null, "Confirm Data", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            try {
                dbController.addMember(member);
                JOptionPane.showMessageDialog(null, "Member added successfully to database.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error adding member to database: " + e.getMessage());
            }
        }
    }

    public void viewMemberDetails() throws ParseException {
        MemberController memberController = new MemberController(dbController);
        ArrayList<Member> members = memberController.getMembers();
        if (members.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No members available.");
        } else {
            for (Member member : members) {
                JOptionPane.showMessageDialog(null, member.toString(), "Member Details", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing admin connection: " + e.getMessage());
        }
    }
}