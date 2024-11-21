package data;

import java.time.LocalDate;

public class Loan {
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
    // ... 기본 getter/setter 메서드들 ...
}
