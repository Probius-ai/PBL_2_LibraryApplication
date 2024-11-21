package pbl_Module_2_1;

public class AvailableBookListService {
    private Library library;

    public AvailableBookListService(Library library) {
        this.library = library;
    }

    public List<Book> getAvailableBooks() {
        return library.getBooks().stream()
                      .filter(Book::isAvailable)
                      .collect(Collectors.toList());
    }
}