package pbl_Module_2_1;

import java.time.LocalDate;
import java.util.Objects;

public class Loan {
    private Book book;
    private Borrower borrower;
    private LocalDate loanDate;

    public Loan(Book book, Borrower borrower) {
        this.book = book;
        this.borrower = borrower;
        this.loanDate = LocalDate.now();
    }

    public Book getBook() {
        return book;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Objects.equals(book, loan.book) &&
               Objects.equals(borrower, loan.borrower) &&
               Objects.equals(loanDate, loan.loanDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, borrower, loanDate);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "book=" + book +
                ", borrower=" + borrower +
                ", loanDate=" + loanDate +
                '}';
    }
}
