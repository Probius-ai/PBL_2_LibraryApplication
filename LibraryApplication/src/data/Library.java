package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Objects;
import java.util.HashSet;
import java.time.LocalDate;

public class Library {
    private Set<Book> bookCollection;
    private Set<Borrower> borrowerCollection;
    private LoanHistory loanHistory;

    public Library() {
        this.bookCollection = new TreeSet<>();
        this.borrowerCollection = new HashSet<>();
        this.loanHistory = new LoanHistory();
    }

    public void addBook(Book book) {
        bookCollection.add(book);
    }

    public void registerBorrower(Borrower borrower) {
        borrowerCollection.add(borrower);
    }

    public Loan lendBook(Book book, Borrower borrower) {
        if (book.isAvailable()) {
            Loan loan = new Loan(book, borrower);
            loanHistory.addNewLoan(loan);
            book.setLoan(loan);
            return loan;
        }
        return null;
    }

    public Set<Book> getBooks() {
        return bookCollection;
    }

    public void returnBook(Loan loan) {
        if (loan != null) {
            loan.getBook().setLoan(null);
            loanHistory.completeLoan(loan);
        }
    }

    public List<Loan> getLoanHistory() {
        return loanHistory.getAllLoans();
    }

    public List<Loan> getCurrentLoans() {
        return loanHistory.getCurrentLoans();
    }

    public List<Loan> getCompletedLoans() {
        return loanHistory.getCompletedLoans();
    }

    public Set<Borrower> getBorrowers() {
        return borrowerCollection;
    }

    /**
     * ISBN을 사용하여 도서관 장서에서 특정 책을 검색합니다.
     * @param isbn 검색할 책의 ISBN
     * @return 찾은 책 객체, 없으면 null 반환
     */
    public Book findBookByISBN(String isbn) {
        return bookCollection.stream()
                .filter(book -> book.getIsbn().equals(isbn))  // ISBN이 일치하는 책 필터링
                .findFirst()  // 첫 번째 일치하는 책 선택
                .orElse(null);  // 책을 찾지 못한 경우 null 반환
    }

    // 추가 메서드들
    // ... 도서 반납, 도서 검색 등의 메서드들 ...
}
