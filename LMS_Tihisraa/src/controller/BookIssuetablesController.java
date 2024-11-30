import Controller.DBController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.Book;
import model.Member;

public class BookIssuetablesController {
    private DBController dbController;

    public BookIssuetablesController() {
        dbController = DBController.getInstance();
    }

    // Fetch available books for display
    public ArrayList<Book> fetchAvailableBooks() throws SQLException {
        ArrayList<Book> availableBooks = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE copies_available > 0";

        try (Connection conn = dbController.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book(
                    rs.getString("book_title"),
                    rs.getString("author"),
                    rs.getString("publication_year"),
                    rs.getString("language"),
                    rs.getString("genre"),
                    rs.getInt("copies_available"),
                    rs.getString("format")
                );
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    // Fetch library members
    public ArrayList<Member> fetchLibraryMembers() throws SQLException, ParseException {
        return dbController.getAllMembers();
    }

    // Issue a book to a member
    public void issueBook(String memberID, String bookID, Date issuedDate, Date dueDate) throws SQLException {
        // SQL to insert into Issued_books_table
        String insertIssuedBookSql = 
            "INSERT INTO Issued_books_table (member_id, book_id, issued_date, due_date) VALUES (?, ?, ?, ?)";
        
        // SQL to update books table (reduce available copies)
        String updateBookSql = 
            "UPDATE books SET copies_available = copies_available - 1 WHERE book_id = ?";

        Connection conn = null;
        try {
            conn = dbController.getConnection();
            conn.setAutoCommit(false);  // Start transaction

            // Insert issued book record
            try (PreparedStatement pstmt1 = conn.prepareStatement(insertIssuedBookSql)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                pstmt1.setString(1, memberID);
                pstmt1.setString(2, bookID);
                pstmt1.setString(3, sdf.format(issuedDate));
                pstmt1.setString(4, sdf.format(dueDate));
                pstmt1.executeUpdate();
            }

            // Update book availability
            try (PreparedStatement pstmt2 = conn.prepareStatement(updateBookSql)) {
                pstmt2.setString(1, bookID);
                pstmt2.executeUpdate();
            }

            conn.commit();  // Commit transaction
            System.out.println("Book issued successfully!");
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();  // Rollback in case of error
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
            }
        }
    }

    // Fetch currently issued books
    public ArrayList<IssuedBook> fetchIssuedBooks() throws SQLException {
        ArrayList<IssuedBook> issuedBooks = new ArrayList<>();
        String sql = "SELECT * FROM Issued_books_table";

        try (Connection conn = dbController.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                IssuedBook issuedBook = new IssuedBook(
                    rs.getString("member_id"),
                    rs.getString("book_id"),
                    rs.getDate("issued_date"),
                    rs.getDate("due_date")
                );
                issuedBooks.add(issuedBook);
            }
        }
        return issuedBooks;
    }
}

// Additional supporting class for Issued Books
class IssuedBook {
    private String memberID;
    private String bookID;
    private Date issuedDate;
    private Date dueDate;

    public IssuedBook(String memberID, String bookID, Date issuedDate, Date dueDate) {
        this.memberID = memberID;
        this.bookID = bookID;
        this.issuedDate = issuedDate;
        this.dueDate = dueDate;
    }

    // Getters and setters
    public String getMemberID() { return memberID; }
    public String getBookID() { return bookID; }
    public Date getIssuedDate() { return issuedDate; }
    public Date getDueDate() { return dueDate; }
}