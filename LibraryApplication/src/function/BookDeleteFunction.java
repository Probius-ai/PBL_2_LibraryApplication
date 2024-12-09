package function;

import data.Book;
import data.Library;

public class BookDeleteFunction {
    private Library library;

    public BookDeleteFunction(Library library) {
        this.library = library;
    }

    /**
     * ISBN으로 책을 찾아 삭제합니다.
     * @param isbn 삭제할 책의 ISBN
     * @return 삭제 성공 여부
     */
    public boolean deleteBookByISBN(String isbn) {
        Book bookToDelete = library.findBookByISBN(isbn);
        if (bookToDelete == null) {
            return false;
        }
        return library.getBookCollection().remove(bookToDelete);
    }

    /**
     * Book 객체를 직접 전달하여 삭제합니다.
     * @param book 삭제할 책 객체
     * @return 삭제 성공 여부
     */
    public boolean deleteBook(Book book) {
        return library.getBookCollection().remove(book);
    }
}
