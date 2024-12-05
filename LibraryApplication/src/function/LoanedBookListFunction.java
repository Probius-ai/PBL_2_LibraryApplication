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
    // 대출된 책 목록 반환 // Book 객체 list구조로 담아서 반환 // 반환값에 book객체의 메소드 사용 가능
}
