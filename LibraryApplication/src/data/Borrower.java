package data;

import java.util.ArrayList;
import java.util.List;

public class Borrower {
    private int borrowerId;
    private String name;
    private List<Loan> loanHistory;

    public Borrower(int borrowerId, String name) {
        this.borrowerId = borrowerId;
        this.name = name;
        this.loanHistory = new ArrayList<>();
    }

    // Getters and setters
    // ... 기본 getter/setter 메서드들 ...
}
