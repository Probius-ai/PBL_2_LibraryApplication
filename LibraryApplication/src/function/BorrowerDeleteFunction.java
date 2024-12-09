package function;

import data.Borrower;
import data.Library;

public class BorrowerDeleteFunction {
    private Library library;

    public BorrowerDeleteFunction(Library library) {
        this.library = library;
    }

    /**
     * 대출자 ID로 대출자를 찾아 삭제합니다.
     * @param borrowerId 삭제할 대출자의 ID
     * @return 삭제 성공 여부
     */
    public boolean deleteBorrowerById(int borrowerId) {
        return library.getBorrowers().removeIf(borrower -> 
            borrower.getBorrowerId() == borrowerId
        );
    }

    /**
     * Borrower 객체를 직접 전달하여 삭제합니다.
     * @param borrower 삭제할 대출자 객체
     * @return 삭제 성공 여부
     */
    public boolean deleteBorrower(Borrower borrower) {
        return library.getBorrowers().remove(borrower);
    }
}
