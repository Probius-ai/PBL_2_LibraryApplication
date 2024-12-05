package function;

import data.Library;
import data.Borrower;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BorrowerSearchFunction {
    private Library library;

    public BorrowerSearchFunction(Library library) {
        this.library = library;
    }

    /**
     * 대출자 이름으로 검색합니다.
     * @param searchName 검색할 대출자 이름
     * @return 검색된 대출자 목록
     */
    public ArrayList<Borrower> searchBorrowersByName(String searchName) {
        return library.getBorrowers().stream()
                .filter(borrower -> borrower.getName().contains(searchName))
                .collect(Collectors.toCollection(ArrayList::new));
        // 대출자 이름으로 검색 //대출자 객체 반환 //반환값 borrower.getBorrowerId() 사용 가능 

    }

    /**
     * 대출자 ID로 검색합니다.
     * @param searchId 검색할 대출자 ID
     * @return 검색된 대출자 목록
     */

    public ArrayList<Borrower> searchBorrowersById(int searchId) {
        return library.getBorrowers().stream()
                .filter(borrower -> borrower.getBorrowerId() == searchId)
                .collect(Collectors.toCollection(ArrayList::new));
        // 대출자 아이디로 검색 //대출자 객체 반환 //반환값 borrower.getName() 사용 가능
    }
} 