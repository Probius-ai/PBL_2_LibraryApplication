package function;

import data.Library;
import data.Borrower;

public class BorrowerRegistrationFunction {
    private Library library;

    public BorrowerRegistrationFunction(Library library) {
        this.library = library;
    }

    // // 대출자 등록 기능 추가 이름 반환 필요시 사용
    // public Borrower registerBorrower(int borrowerId, String name) {
    //     Borrower newBorrower = new Borrower(borrowerId, name);
        
    //     library.registerBorrower(newBorrower);
        
    //     return newBorrower;
    // }

    public void registerBorrower(int borrowerId, String name) {
        Borrower newBorrower = new Borrower(borrowerId, name);
        library.registerBorrower(newBorrower);
    }
}
