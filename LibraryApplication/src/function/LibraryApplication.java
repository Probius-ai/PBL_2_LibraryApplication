package function;

import data.Library;
import java.util.List;

public class LibraryApplication {
    private Library library;
    private AvailableBookListFunction availableBookList;
    private BookLoanFunction bookLoan;
    private BookRegistrastionFunction bookRegistration;
    private BorrowerRegistrationFunction borrowerRegistration;
    private BookReturnFunction bookReturn;
    private LoanedBookListFunction loanedBookList;

    public LibraryApplication() {
        this.library = new Library();
        initializeFunctions();
    }

    private void initializeFunctions() {
        this.availableBookList = new AvailableBookListFunction(library);
        this.bookLoan = new BookLoanFunction(library);
        this.bookRegistration = new BookRegistrastionFunction(library);
        this.borrowerRegistration = new BorrowerRegistrationFunction(library);
        this.bookReturn = new BookReturnFunction(library);
        this.loanedBookList = new LoanedBookListFunction(library);
    }

    // 각 기능에 대한 getter 메서드들
    public AvailableBookListFunction getAvailableBookList() {
        return availableBookList;
    }

    public BookLoanFunction getBookLoan() {
        return bookLoan;
    }

    public BookRegistrastionFunction getBookRegistration() {
        return bookRegistration;
    }

    public BorrowerRegistrationFunction getBorrowerRegistration() {
        return borrowerRegistration;
    }

    public BookReturnFunction getBookReturn() {
        return bookReturn;
    }

    public LoanedBookListFunction getLoanedBookList() {
        return loanedBookList;
    }

    // 실제 사용 예시 메서드들
    public void processBookLoan(data.Book book, data.Borrower borrower) {
        // 비즈니스 로직을 BookLoanFunction으로 위임
        bookLoan.loanBook(book, borrower);
    }

    public void processBookReturn(data.Loan loan) {
        // 비즈니스 로직을 BookReturnFunction으로 위임
        bookReturn.returnBook(loan);
    }

    public void registerNewBook(data.Book book) {
        bookRegistration.registerBook(book);
    }

    public void registerNewBorrower(data.Borrower borrower) {
        borrowerRegistration.registerBorrower(borrower);
    }

    public List<data.Book> getAvailableBooks() {
        return availableBookList.getAvailableBooks();
    }

    public List<data.Book> getLoanedBooks() {
        return loanedBookList.getLoanedBooks();
    }
}
