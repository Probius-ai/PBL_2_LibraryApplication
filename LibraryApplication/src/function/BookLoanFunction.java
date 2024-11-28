package function;

import data.Library;
import data.Book;
import data.Borrower;
import data.Loan;

public class BookLoanFunction {
    private Library library;

    public BookLoanFunction(Library library) {
        this.library = library;
    }

    public Loan loanBook(Book book, Borrower borrower) {
        return library.lendBook(book, borrower);
    }
}
