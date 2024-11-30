package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.Book;
import model.Member;
import model.MembershipCard;

public class DBController {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/librarydb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private Connection connection;
    private static DBController instance;

    private DBController() {
        // Don't throw SQLException in constructor
        // Connection will be established when needed
    }

    public static DBController getInstance() {
        if (instance == null) {
            instance = new DBController();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Connected to the database successfully!");
            } catch (ClassNotFoundException e) {
                System.err.println("JDBC Driver not found: " + e.getMessage());
                throw new SQLException("JDBC Driver not found.", e);
            } catch (SQLException e) {
                System.err.println("Failed to connect to database: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    public ArrayList<Member> getAllMembers() throws SQLException, ParseException {
        ArrayList<Member> members = new ArrayList<>();
        String sql = "SELECT name, contact, address, course, expire_date FROM members";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); var rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                String contact = rs.getString("contact");
                String address = rs.getString("address");
                String cardNumber = rs.getString("course");
                String expiryDate = rs.getString("expire_date");

                MembershipCard membershipCard = new MembershipCard(cardNumber, new SimpleDateFormat("yyyy-MM-dd").parse(expiryDate));
                Member member = new Member(name, contact, address, membershipCard);
                members.add(member);
            }
        } catch (SQLException | ParseException e) {
            System.err.println("Error fetching members from the database: " + e.getMessage());
            throw e;
        }

        return members;
    }

    public void addMember(Member member) throws SQLException {
        String sql = "INSERT INTO members (name, contact, address, course, expire_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getContact());
            pstmt.setString(3, member.getAddress());
            pstmt.setString(4, member.getMembershipCard().getCardNumber());
            pstmt.setString(5, member.getMembershipCard().getExpiryDate());
//            pstmt.setString(6, "active");
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Member added successfully. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error adding member to the database: " + e.getMessage());
            throw e;
        }
    }

    public void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (book_title, author, publication_year, language, genre, copies_available, format) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getName());

            try {
                int year = Integer.parseInt(book.getPublishedYear());
                pstmt.setInt(3, year);
            } catch (NumberFormatException e) {
                throw new SQLException("Invalid publication year format: " + book.getPublishedYear());
            }

            pstmt.setString(4, book.getLanguage());
            pstmt.setString(5, book.getGenre());
            pstmt.setInt(6, book.getCopies_Available());
            pstmt.setString(7, book.getFormat());

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Book added successfully. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error adding book to the database: " + e.getMessage());
            throw e;
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Connection closed successfully.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
