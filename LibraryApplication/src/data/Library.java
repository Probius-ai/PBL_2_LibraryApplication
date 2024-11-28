package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Objects;
import java.util.HashSet;

public class Library {
    private Set<Book> books;
    private Set<Borrower> borrowers;
    private List<Loan> loans;

    public Library() {
        this.books = new TreeSet<>();
        this.borrowers = new HashSet<>();
        this.loans = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void registerBorrower(Borrower borrower) {
        borrowers.add(borrower);
    }

    public Loan lendBook(Book book, Borrower borrower) {
        if (book.isAvailable()) {
            Loan loan = new Loan(book, borrower);
            loans.add(loan);
            book.setLoan(loan);
            return loan;
        }
        return null;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void returnBook(Loan loan) {
        if (loans.contains(loan)) {
            loans.remove(loan);
            loan.getBook().setLoan(null);
        }
    }

    // 추가 메서드들
    // ... 도서 반납, 도서 검색 등의 메서드들 ...
}
