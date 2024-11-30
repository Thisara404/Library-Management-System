package controller;

import Controller.DBController;
import java.sql.SQLException;
import model.Book;
import javax.swing.*;
import java.util.ArrayList;

public class BookController {

    private java.util.ArrayList<Book> books;
    private int currentIndex;
    private DBController dbController;

    private Book book;

    public BookController() {
        books = new ArrayList();
        currentIndex = 1;
        dbController = DBController.getInstance();
    }

    public void add(Book book) {
        if (!books.contains(book)) {
            int response = JOptionPane.showConfirmDialog(null, "Confirm Data", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                books.add(book);
                currentIndex++;
                try {
                    dbController.addBook(book);
                    JOptionPane.showMessageDialog(null, "Book added successfully to database.");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error adding book to database: " + e.getMessage());
                }
            } else {

            }
        }
//        // Check if the book is already in the list
//        if (!books.contains(book)) {
//            // Add book to the list
//            books.add(book);
//            // Increment the current index
//            currentIndex++;
//            System.out.println("book added successfully.");
//        } else {
//            System.out.println("book already exists.");
//        }
    }

    public Book findBookByID(String bookID) {
        // You'll need to implement this using your BookController or DBController
        // This is a placeholder - replace with actual implementation
        BookController bookController = new BookController();
        for (Book book : bookController.getMembers()) {
            if (book.getTitle().equals(bookID)) {
                return book;
            }
        }
        return null;
    }

    public void viewBookDetails() {
        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No books available.");
        } else {
            // Iterate over the books list and show each book's details in a dialog
            for (Book book : books) {
                JOptionPane.showMessageDialog(null, book.toString(), "Book Details", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public Book findBookByTitle(String bookTitle) {
        BookController bookController = new BookController();
        for (Book book : bookController.getMembers()) {
            if (book.getTitle().equalsIgnoreCase(bookTitle)) {
                return book;
            }
        }
        return null;
    }

    public ArrayList<Book> getMembers() {
        return books;
    }

    @Override
    public String toString() {
        return "BookController{"
                + "books=" + books;

    }
}
