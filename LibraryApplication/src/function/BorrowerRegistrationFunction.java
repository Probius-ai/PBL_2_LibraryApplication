package function;

import data.Library;
import data.Borrower;

public class BorrowerRegistrationFunction {
    private Library library;

    public BorrowerRegistrationFunction(Library library) {
        this.library = library;
    }

    public void registerBorrower(Borrower borrower) {
        library.registerBorrower(borrower);
    }
}
