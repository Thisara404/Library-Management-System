package controller;
/**
 *
 * @author THISARA DASUN
 */
import Controller.DBController;
import model.Book;
import model.Member;
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class IssuedBookController {
    private DBController dbController;

    public IssuedBookController() {
        dbController = DBController.getInstance();
    }

    public void issueBook(Member member, Book book, Date issuedDate, Date dueDate) {
        try {
            // Validate inputs
            if (member == null || book == null || issuedDate == null || dueDate == null) {
                JOptionPane.showMessageDialog(null, "All fields are required!");
                return;
            }

            // Check book availability
            if (book.getCopies_Available() <= 0) {
                JOptionPane.showMessageDialog(null, "Book is not available for issuing!");
                return;
            }

            Connection connection = dbController.getConnection();
            String sql = "INSERT INTO issued_books (member_name, member_contact, book_title, book_author, issued_date, due_date) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, member.getName());
                pstmt.setString(2, member.getContact());
                pstmt.setString(3, book.getTitle());
                pstmt.setString(4, book.getName());
                pstmt.setString(5, new SimpleDateFormat("yyyy-MM-dd").format(issuedDate));
                pstmt.setString(6, new SimpleDateFormat("yyyy-MM-dd").format(dueDate));

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    // Reduce available book copies
                    book.setCopies_Available(book.getCopies_Available() - 1);
                    updateBookCopies(book);

                    JOptionPane.showMessageDialog(null, "Book Issued Successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to issue book!");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateBookCopies(Book book) throws SQLException {
        Connection connection = dbController.getConnection();
        String sql = "UPDATE books SET copies_available = ? WHERE book_title = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, book.getCopies_Available());
            pstmt.setString(2, book.getTitle());
            pstmt.executeUpdate();
        }
    }

    public ArrayList<IssuedBookRecord> fetchIssuedBooks() {
        ArrayList<IssuedBookRecord> issuedBooks = new ArrayList<>();
        try {
            Connection connection = dbController.getConnection();
            String sql = "SELECT member_name, member_contact, book_title, book_author, issued_date, due_date FROM issued_books";

            try (PreparedStatement pstmt = connection.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    IssuedBookRecord record = new IssuedBookRecord(
                        rs.getString("member_name"),
                        rs.getString("member_contact"),
                        rs.getString("book_title"),
                        rs.getString("book_author"),
                        rs.getDate("issued_date"),
                        rs.getDate("due_date")
                    );
                    issuedBooks.add(record);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return issuedBooks;
    }

    // Inner class to represent issued book record
    public static class IssuedBookRecord {
        private String memberName;
        private String memberContact;
        private String bookTitle;
        private String bookAuthor;
        private Date issuedDate;
        private Date dueDate;

        public IssuedBookRecord(String memberName, String memberContact, String bookTitle, 
                                String bookAuthor, Date issuedDate, Date dueDate) {
            this.memberName = memberName;
            this.memberContact = memberContact;
            this.bookTitle = bookTitle;
            this.bookAuthor = bookAuthor;
            this.issuedDate = issuedDate;
            this.dueDate = dueDate;
        }

        // Getters for all fields
        public String getMemberName() { return memberName; }
        public String getMemberContact() { return memberContact; }
        public String getBookTitle() { return bookTitle; }
        public String getBookAuthor() { return bookAuthor; }
        public Date getIssuedDate() { return issuedDate; }
        public Date getDueDate() { return dueDate; }
    }
}