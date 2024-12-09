package data;

import java.io.Serializable;
import java.util.Objects;

public class Book implements Comparable<Book>, Serializable {
    private static final long serialVersionUID = 1L;
    private String isbn;
    private String title;
    private String author;
    private Loan currentLoan;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.currentLoan = null;
    }

    public boolean isAvailable() { // 책이 대출 가능한지 확인 //Loan 객체가 null인지 확인(등록안됨)
        return currentLoan == null;
    }

    public void setLoan(Loan loan) { // Book 객체에 Loan 객체 참조
        this.currentLoan = loan;
    }

    public Loan getCurrentLoan() { // Book 객체에 Loan 객체 참조 반환
        return currentLoan;
    }

    /**
     * 책의 ISBN을 반환합니다.
     * @return 책의 ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * 책의 제목을 반환합니다.
     * @return 책의 제목
     */
    public String getTitle() {
        return title;
    }

    /**
     * 책의 저자를 반환합니다.
     * @return 책의 저자
     */
    public String getAuthor() {
        return author;
    }

    /**
     * ISBN을 기준으로 다른 Book 객체와 비교합니다.
     * TreeSet에서 정렬 순서를 결정하는데 사용됩니다.
     * @param other 비교할 다른 Book 객체
     * @return 현재 책의 ISBN이 더 작으면 음수, 같으면 0, 더 크면 양수
     */
    @Override
    public int compareTo(Book other) {
        return this.isbn.compareTo(other.isbn);
    }

    /**
     * Book 객체의 동등성을 ISBN 기준으로 비교합니다.
     * @param o 비교할 객체
     * @return 같은 ISBN을 가진 책이면 true, 아니면 false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // 같은 객체인 경우
        if (o == null || getClass() != o.getClass()) return false;  // null이거나 다른 클래스인 경우
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);  // ISBN 비교
    }

    /**
     * ISBN을 기준으로 해시코드를 생성합니다.
     * HashSet 등의 자료구조에서 효율적인 검색을 위해 사용됩니다.
     * @return ISBN 기반의 해시코드
     */
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return getTitle(); // 책의 제목을 반환 // 인터페이스 출력용
    }
}
