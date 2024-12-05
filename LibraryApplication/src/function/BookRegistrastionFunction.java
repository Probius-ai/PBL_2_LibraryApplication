package function;

import data.Library;
import data.Book;

public class BookRegistrastionFunction {
    private Library library;

    public BookRegistrastionFunction(Library library) {
        this.library = library;
    }

    // 책 등록 기능 // 책 객체 생성 후 라이브러리에 등록
    public void registerNewBook(String isbn, String title, String author) {
        Book newBook = new Book(isbn, title, author);
        library.registerBook(newBook);
    }
    
}
