package function;

import data.Library;
import data.Book;
import data.Borrower;
import data.Loan;
import java.util.List;

public class BookLoanFunction {
    private Library library;
    private AvailableBookListFunction availableBookList;

    public BookLoanFunction(Library library) {
        this.library = library;
        this.availableBookList = new AvailableBookListFunction(library);
    }

    public Loan loanBook(Book book, Borrower borrower) {
        // 대출 가능 여부 검증
        List<Book> availableBooks = availableBookList.getAvailableBooks();
        if (!availableBooks.contains(book)) {
            throw new IllegalStateException("이 도서는 현재 대출이 불가능합니다.");
        }

        // 대출자 등록 여부 확인
        if (!library.getBorrowers().contains(borrower)) {
            throw new IllegalArgumentException("등록되지 않은 대출자입니다.");
        }

        // 대출 처리
        Loan loan = new Loan(book, borrower);
        book.setLoan(loan);
        library.lendBook(loan);
        
        return loan;
    }
}
