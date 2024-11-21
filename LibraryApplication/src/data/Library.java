package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Objects;

public class Library {
    private Set<Book> books;
    private List<Borrower> borrowers;
    private List<Loan> loans;

    public Library() {
        this.books = new TreeSet<>();
        this.borrowers = new ArrayList<>();
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
            book.setAvailable(false);
            return loan;
        }
        return null;
    }

    // 추가 메서드들
    // ... 도서 반납, 도서 검색 등의 메서드들 ...
}
