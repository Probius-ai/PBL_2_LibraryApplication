package data;

import java.util.Objects;

public class Book implements Comparable<Book> {
    private String isbn;
    private String title;
    private String author;
    private boolean isAvailable;
    private Loan currentLoan;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.currentLoan = null;
    }

    public boolean isAvailable() {
        return currentLoan == null;
    }

    public void setLoan(Loan loan) {
        this.currentLoan = loan;
    }

    public Loan getCurrentLoan() {
        return currentLoan;
    }

    // Getters and setters
    // ... 기본 getter/setter 메서드들 ...

    @Override
    public int compareTo(Book other) {
        return this.isbn.compareTo(other.isbn);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
