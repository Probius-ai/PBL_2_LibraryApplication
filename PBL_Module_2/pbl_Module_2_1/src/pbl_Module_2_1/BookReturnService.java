package pbl_Module_2_1;

public class BookReturnService {
    private Library library;

    public BookReturnService(Library library) {
        this.library = library;
    }

    public boolean returnBook(String catalogueNumber) {
        Book book = library.findBook(catalogueNumber);
        if (book != null) {
            Loan loanToRemove = library.getLoans().stream()
                                       .filter(loan -> loan.getBook().equals(book))
                                       .findFirst()
                                       .orElse(null);
            if (loanToRemove != null) {
                library.removeLoan(loanToRemove);
                book.setAvailable(true);
                return true;
            }
        }
        return false;
    }
}
