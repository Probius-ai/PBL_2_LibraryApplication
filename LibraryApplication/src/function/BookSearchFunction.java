package function;

import data.Library;
import data.Book;
import java.util.ArrayList;
import java.util.TreeSet;

public class BookSearchFunction {
    private Library library;

    public BookSearchFunction(Library library) {
        this.library = library;
    }

    public ArrayList<Book> filterBooks(boolean showOnLoan, boolean showAvailable) {
        TreeSet<Book> books = new TreeSet<>(library.getBooks());
        ArrayList<Book> filteredBooks = new ArrayList<>();

        if (showOnLoan) {
            for (Book book : books) {
                if (book.getCurrentLoan() != null) {
                    filteredBooks.add(book);
                }
            }
        }
        
        if (showAvailable) {
            for (Book book : books) {
                if (book.getCurrentLoan() == null) {
                    filteredBooks.add(book);
                }
            }
        }

        return filteredBooks;
    }
} 