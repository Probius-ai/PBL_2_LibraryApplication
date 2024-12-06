package data;

import java.io.Serializable;
import java.time.LocalDate;

public class Loan implements Serializable {
    private static final long serialVersionUID = 1L;
    private Book book;
    private Borrower borrower;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(Book book, Borrower borrower) {
        this.book = book;
        this.borrower = borrower;
        this.loanDate = LocalDate.now();
        this.dueDate = loanDate.plusDays(14); // 2주 대출 기간
    }

    // Getters and setters
    public Book getBook() {
        return this.book;
    }

    public Borrower getBorrower() {
        return this.borrower;
    }

    public LocalDate getLoanDate() {
        return this.loanDate;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public LocalDate getReturnDate() {
        return this.returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    // ... 기본 getter/setter 메서드들 ...
}
