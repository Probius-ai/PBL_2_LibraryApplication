package function;

import data.Library;
import data.Book;
import java.util.List;
import java.util.stream.Collectors;

public class LoanedBookListFunction {
    private Library library;

    public LoanedBookListFunction(Library library) {
        this.library = library;
    }

    public List<Book> getLoanedBooks() {
        return library.getBooks().stream()
                .filter(book -> !book.isAvailable())
                .collect(Collectors.toList());
    }
}
