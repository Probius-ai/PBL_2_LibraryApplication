package pbl_Module_2_1;

import java.util.TreeSet;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.List;

public class Library {
    private TreeSet<Book> books;
    private ArrayList<Borrower> borrowers;
    private ArrayList<Loan> loans;

    public boolean addBorrower(Borrower borrower) {
        return borrowers.add(borrower);
    }

    public boolean addBook(Book book) {
        return books.add(book);
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
    }

    public TreeSet<Book> getBooks() {
        return books;
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public Borrower findBorrower(String name) {
        // borrowers에서 이름으로 Borrower 객체 찾기
    }

    public Book findBook(String catalogueNumber) {
        // books에서 카탈로그 번호로 Book 객체 찾기
    }
}
