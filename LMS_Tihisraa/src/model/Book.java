package model;

public class Book extends Author {

    private int bookId;
    private String title;
    private String publishedYear;
    private String language;
    private String genre;
    private int copies_Available;
    private String format;
    private String author;
    private int copiesAvailable;
    private int copiesAvailable2;

//    public Book( String title, String authorName, String authorEmail, String publishedYear) {
//        super(authorName, authorEmail);
//        this.title = title;
//        this.publishedYear = publishedYear;
//    }
    public Book(String name, String email, String title, String publishedYear, String language, String genre, int copies_Available, String format) {
        super(name, email);
        this.title = title;
        this.publishedYear = publishedYear;
        this.language = language;
        this.genre = genre;
        this.copies_Available = copies_Available;
        this.format = format;
    }

    public Book(int bookId, String name, String email, String title, String publishedYear, String language, String genre, int copies_Available, String format) {
        super(name, email);
        this.bookId = bookId;
        this.title = title;
        this.publishedYear = publishedYear;
        this.language = language;
        this.genre = genre;
        this.copies_Available = copies_Available;
        this.format = format;
    }

    public Book(String title, String name, String email, String publishedYear, String language, int aInt, String genre) {
        super(name, email);
        this.bookId = bookId;
        this.title = title;
        this.publishedYear = publishedYear;
        this.language = language;
        this.genre = genre;
        this.copiesAvailable2 = copies_Available;
        this.format = "Unknown";
    }

    public int getBookId() {
        return bookId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getCopies_Available() {
        return copies_Available;
    }

    public void setCopies_Available(int copies_Available) {
        this.copies_Available = copies_Available;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(String publishedYear) {
        try {
            // Validate that it's a valid year
            Integer.parseInt(publishedYear);
            this.publishedYear = publishedYear;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Publication year must be a valid number");
        }
    }

    @Override
    public String toString() {
        return title + getName() + publishedYear;
    }
}
