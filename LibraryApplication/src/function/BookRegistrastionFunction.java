package function;

import data.Library;
import data.Book;

public class BookRegistrastionFunction {
    private Library library;

    public BookRegistrastionFunction(Library library) {
        this.library = library;
    }

    public void registerNewBook(String isbn, String title, String author) {
        Book newBook = new Book(isbn, title, author);
        library.registerBook(newBook);
    }
}
