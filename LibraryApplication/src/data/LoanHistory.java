package data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanHistory {
    private List<Loan> completedLoans;  // 반납된 대출 기록
    private List<Loan> currentLoans;    // 현재 진행 중인 대출

    public LoanHistory() {
        this.completedLoans = new ArrayList<>();
        this.currentLoans = new ArrayList<>();
    }

    public void addNewLoan(Loan loan) {
        currentLoans.add(loan);
    }

    public void completeLoan(Loan loan) {
        if (currentLoans.remove(loan)) {
            loan.setReturnDate(LocalDate.now());
            completedLoans.add(loan);
        }
    }

    public List<Loan> getCurrentLoans() {
        return new ArrayList<>(currentLoans);
    }

    public List<Loan> getCompletedLoans() {
        return new ArrayList<>(completedLoans);
    }

    public List<Loan> getAllLoans() {
        List<Loan> allLoans = new ArrayList<>();
        allLoans.addAll(currentLoans);
        allLoans.addAll(completedLoans);
        return allLoans;
    }
}
