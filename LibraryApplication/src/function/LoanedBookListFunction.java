package function;

import data.Library;
import data.Book;
import java.util.List;
import java.util.stream.Collectors;
// import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LoanedBookListFunction {
    private Library library;

    public LoanedBookListFunction(Library library) {
        this.library = library;
    }

    // 대출된 책 목록 반환 // Book 객체 list구조로 담아서 반환 // 반환값에 book객체의 메소드 사용 가능
    public List<Book> getLoanedBooks() {
        return library.getBooks().stream()
                .filter(book -> !book.isAvailable())
                .collect(Collectors.toList());
    }
    
    public List<Book> searchLoanedBooksByIsbn(String isbn) {
        return getLoanedBooks().stream()
                .filter(book -> book.getIsbn().contains(isbn))
                .collect(Collectors.toList());
    }

    public String getLoanInfo(Book book) {
        return library.getCurrentLoans().stream()
                .filter(loan -> loan.getBook().equals(book))
                .findFirst()
                .map(loan -> String.format("%s (%s) - 대출자: %s, 반납예정일: %s",
                    book.getTitle(),
                    book.getIsbn(),
                    loan.getBorrower().getName(),
                    loan.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .orElse(null);
    }
}
