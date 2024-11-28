package function;

import data.Library;
import data.Book;
import data.Loan;

public class BookReturnFunction {
    private Library library;

    public BookReturnFunction(Library library) {
        this.library = library;
    }

    public void returnBook(Loan loan) {
        library.returnBook(loan);
    }
}
