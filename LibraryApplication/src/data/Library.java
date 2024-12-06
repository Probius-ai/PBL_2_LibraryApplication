package data;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.io.*;

public class Library implements Serializable {
    private static final long serialVersionUID = 1L;
    private Set<Book> bookCollection;
    private Set<Borrower> borrowerCollection;
    private LinkedList<Loan> loanCollection;
    private LoanHistory loanHistory;

    public Library() {
        this.bookCollection = new TreeSet<>();
        this.borrowerCollection = new HashSet<>();
        this.loanCollection = new LinkedList<>();
        this.loanHistory = new LoanHistory();
    }

    public void registerBook(Book book) {
        bookCollection.add(book);
    }

    public void registerBorrower(Borrower borrower) {
        borrowerCollection.add(borrower);
    }

    public void registerLoan(Loan loan) {
        loanCollection.add(loan);
        loanHistory.addNewLoan(loan);
        loan.getBook().setLoan(loan);
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

    // LinkedList 관련 새로운 메서드들
    public LinkedList<Loan> getLoanCollection() {
        return new LinkedList<>(loanCollection);
    }

    public Loan getLatestLoan() {
        return loanCollection.isEmpty() ? null : loanCollection.getLast();
    }

    public Loan getFirstLoan() {
        return loanCollection.isEmpty() ? null : loanCollection.getFirst();
    }

    // 추가 메서드들
    // ... 도서 반납, 도서 검색 등의 메서드들 ...

    // 특정 사용자의 대출 기록을 조회하는 메서드 추가// 추후 추가 function 클래스로 옮길 예정
    public List<Loan> getBorrowerLoanHistory(Borrower borrower) {
        return loanHistory.getAllLoans().stream()
                .filter(loan -> loan.getBorrower().equals(borrower))
                .toList();
    }

    // 특정 사용자의 현재 대출 목록을 조회하는 메서드 추가 // 추후 추가 function 클래스로 옮길 예정
    public List<Loan> getBorrowerCurrentLoans(Borrower borrower) {
        return loanHistory.getCurrentLoans().stream()
                .filter(loan -> loan.getBorrower().equals(borrower))
                .toList();
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(bookCollection);
            out.writeObject(borrowerCollection);
            out.writeObject(loanCollection);
            out.writeObject(loanHistory);
        } catch (IOException e) {
            throw new RuntimeException("데이터 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            this.bookCollection = (Set<Book>) in.readObject();
            this.borrowerCollection = (Set<Borrower>) in.readObject();
            this.loanCollection = (LinkedList<Loan>) in.readObject();
            this.loanHistory = (LoanHistory) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("데이터 로딩 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
