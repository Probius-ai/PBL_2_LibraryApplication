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
    // 대출 가능한 책 목록 반환 // Book 객체 list구조로 담아서 반환 // 반환값에 book객체의 메소드 사용 가능
}
