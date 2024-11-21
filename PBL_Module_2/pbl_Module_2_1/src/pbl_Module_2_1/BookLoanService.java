package pbl_Module_2_1;

public class BookLoanService {
    private Library library;

    public BookLoanService(Library library) {
        this.library = library;
    }

    public boolean loanBook(String borrowerName, String catalogueNumber) {
        Borrower borrower = library.findBorrower(borrowerName);
        Book book = library.findBook(catalogueNumber);

        if (borrower != null && book != null && book.isAvailable()) {
            Loan newLoan = new Loan(book, borrower);
            library.addLoan(newLoan);
            book.setAvailable(false);
            return true;
        }
        return false;
    }
}
