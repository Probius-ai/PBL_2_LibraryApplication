package function;

import data.Library;
import java.util.List;

public class LibraryApplication {
    private static final String DATA_FILE = "library_data.ser";
    
    private Library library;
    private AvailableBookListFunction availableBookList;
    private BookLoanFunction bookLoan;
    private BookRegistrastionFunction bookRegistration;
    private BorrowerRegistrationFunction borrowerRegistration;
    private BookReturnFunction bookReturn;
    private LoanedBookListFunction loanedBookList;
    private BookDeleteFunction bookDelete;
    private BorrowerDeleteFunction borrowerDelete;

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
        this.bookDelete = new BookDeleteFunction(library);
        this.borrowerDelete = new BorrowerDeleteFunction(library);
    }

    // 각 기능에 대한 호출 메서드

    // 대출 가능한 책 목록 반환
    public AvailableBookListFunction getAvailableBookList() {
        return availableBookList;
    }

    // 대출 기능 반환
    public BookLoanFunction getBookLoan() {
        return bookLoan;
    }

    // 책 등록 기능 반환
    public BookRegistrastionFunction getBookRegistration() {
        return bookRegistration;
    }

    // 대출자 등록 기능 반환
    public BorrowerRegistrationFunction getBorrowerRegistration() {
        return borrowerRegistration;
    }

    // 책 반납 기능 반환
    public BookReturnFunction getBookReturn() {
        return bookReturn;
    }

    // 대출된 책 목록 반환
    public LoanedBookListFunction getLoanedBookList() {
        return loanedBookList;
    }

    // Library 인스턴스에 접근하기 위한 getter 메소드 추가
    public Library getLibrary() {
        return library;
    }

    // // 실제 사용 예시 메서드들
    // public void processBookLoan(data.Book book, data.Borrower borrower) {
    //     // 비즈니스 로직을 BookLoanFunction으로 위임
    //     bookLoan.loanBook(book, borrower);
    // }

    // public void processBookReturn(data.Loan loan) {
    //     // 비즈니스 로직을 BookReturnFunction으로 위임
    //     bookReturn.returnBook(loan);
    // }

    // 책 등록 기능
    public void registerNewBook(String isbn, String title, String author) {
        bookRegistration.registerNewBook(isbn, title, author);
    }

    // 대출자 등록 기능
    public void registerNewBorrower(int borrowerId, String name) {
        borrowerRegistration.registerBorrower(borrowerId, name);
    }

    // 대출 가능한 책 목록 반환
    public List<data.Book> getAvailableBooks() {
        return availableBookList.getAvailableBooks();
    }

    // 대출된 책 목록 반환
    public List<data.Book> getLoanedBooks() {
        return loanedBookList.getLoanedBooks();
    }

    public void saveLibraryData() {
        library.saveToFile(DATA_FILE);
    }
    
    public void loadLibraryData() {
        try {
            library.loadFromFile(DATA_FILE);
            // 기능들 재초기화
            initializeFunctions();
        } catch (RuntimeException e) {
            System.err.println("데이터 파일을 불러올 수 없습니다: " + e.getMessage());
            // 새로운 라이브러리로 시작
            this.library = new Library();
            initializeFunctions();
        }
    }

    // 책 삭제 기능 반환을 위한 getter
    public BookDeleteFunction getBookDelete() {
        return bookDelete;
    }

    // 편의를 위한 메서드 추가
    public boolean deleteBook(String isbn) {
        return bookDelete.deleteBookByISBN(isbn);
    }

    // BorrowerDeleteFunction 반환을 위한 getter
    public BorrowerDeleteFunction getBorrowerDelete() {
        return borrowerDelete;
    }

    // 편의를 위한 메서드 추가
    public boolean deleteBorrower(int borrowerId) {
        return borrowerDelete.deleteBorrowerById(borrowerId);
    }
}
