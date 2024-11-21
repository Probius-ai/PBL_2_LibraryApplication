package pbl_Module_2_1;

import java.util.Objects;

public class Book implements Comparable<Book> {
    private String title;
    private String author;
    private String catalogueNumber;
    private boolean isAvailable;

    public Book(String title, String author, String catalogueNumber) {
        this.title = title;
        this.author = author;
        this.catalogueNumber = catalogueNumber;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCatalogueNumber() {
        return catalogueNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public int compareTo(Book other) {
        return this.catalogueNumber.compareTo(other.catalogueNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(catalogueNumber, book.catalogueNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(catalogueNumber);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", catalogueNumber='" + catalogueNumber + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
