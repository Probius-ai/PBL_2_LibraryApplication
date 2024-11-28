package function;

import data.Library;
import data.Book;
import java.util.List;
import java.util.stream.Collectors;

public class AvailableBookListFunction {
    private Library library;

    public AvailableBookListFunction(Library library) {
        this.library = library;
    }

    public List<Book> getAvailableBooks() {
        return library.getBooks().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }
}
