package function;

import data.Library;
import data.Book;

public class BookRegistrastionFunction {
    private Library library;

    public BookRegistrastionFunction(Library library) {
        this.library = library;
    }

    public void registerBook(Book book) {
        library.addBook(book);
    }
}
