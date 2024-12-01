package function;

import data.Library;
import data.Book;
import data.Loan;
import data.Borrower;
import java.util.List;
import java.util.stream.Collectors;

public class BookReturnFunction {
    private Library library;

    public BookReturnFunction(Library library) {
        this.library = library;
    }

    public void returnBook(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("대출 정보가 없습니다.");
        }

        // 도서 상태 업데이트
        loan.getBook().setLoan(null);
        
        // 도서관의 대출 목록에서 제거하고 이력에 추가
        library.returnBook(loan);
    }

    // 특정 도서의 대출 이력 조회
    public List<Loan> getBookLoanHistory(Book book) {
        return library.getLoanHistory().stream()
                .filter(loan -> loan.getBook().equals(book))
                .collect(Collectors.toList());
    }

    // 특정 대출자의 대출 이력 조회
    public List<Loan> getBorrowerLoanHistory(Borrower borrower) {
        return library.getLoanHistory().stream()
                .filter(loan -> loan.getBorrower().equals(borrower))
                .collect(Collectors.toList());
    }
}
