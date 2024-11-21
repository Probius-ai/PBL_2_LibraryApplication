package pbl_Module_2_1;

public class BorrowerRegistrationService {
    private Library library;

    public BorrowerRegistrationService(Library library) {
        this.library = library;
    }

    public boolean registerBorrower(String name) {
        if (isValidName(name) && !isDuplicateName(name)) {
            Borrower newBorrower = new Borrower(name);
            return library.addBorrower(newBorrower);
        }
        return false;
    }

    private boolean isValidName(String name) {
        // 이름 유효성 검사 로직
    }

    private boolean isDuplicateName(String name) {
        // 이름 중복 검사 로직
    }
}
