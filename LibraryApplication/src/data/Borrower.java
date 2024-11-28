package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Borrower {
    private int borrowerId;
    private String name;
    private List<Loan> loanHistory;

    public Borrower(int borrowerId, String name) {
        this.borrowerId = borrowerId;
        this.name = name;
        this.loanHistory = new ArrayList<>();
    }

    // Getters and setters
    // ... 기본 getter/setter 메서드들 ...

    @Override
    public boolean equals(Object o) {
        // HashSet이 중복을 확인할 때 사용
        // 해시 충돌이 발생한 경우에만 equals() 호출됨
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrower borrower = (Borrower) o;
        // borrowerId나 다른 고유 식별자를 사용하여 비교
        return Objects.equals(borrowerId, borrower.borrowerId);
    }

    @Override
    public int hashCode() {
        
        // HashSet의 핵심 성능 향상 요소
        // 이 해시값을 기준으로 저장 위치가 결정되어 O(1) 검색 가능
        return Objects.hash(borrowerId);
    }
}