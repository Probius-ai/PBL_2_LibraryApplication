package function;

import data.Library;
import data.Borrower;
import java.util.ArrayList;
import java.util.Set;
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
    }
} 