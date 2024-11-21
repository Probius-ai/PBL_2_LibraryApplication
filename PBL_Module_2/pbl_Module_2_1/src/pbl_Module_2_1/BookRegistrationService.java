package pbl_Module_2_1;

public class BookRegistrationService {
    private Library library;

    public BookRegistrationService(Library library) {
        this.library = library;
    }

    public boolean addBook(String title, String author, String catalogueNumber) {
        if (isValidBookInfo(title, author, catalogueNumber) && !isDuplicateCatalogueNumber(catalogueNumber)) {
            Book newBook = new Book(title, author, catalogueNumber);
            return library.addBook(newBook);
        }
        return false;
    }

    private boolean isValidBookInfo(String title, String author, String catalogueNumber) {
        // 책 정보 유효성 검사 로직
    }

    private boolean isDuplicateCatalogueNumber(String catalogueNumber) {
        // 카탈로그 번호 중복 검사 로직
    }
}
