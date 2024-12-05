package function;

import data.Library;
import data.Book;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class BookSearchFunction {
    private Library library;

    public BookSearchFunction(Library library) {
        this.library = library;
    }
    public ArrayList<Book> searchBooksByTitle(String searchTitle) {
        return library.getBooks().stream()
                .filter(book -> book.getTitle().contains(searchTitle))
                .collect(Collectors.toCollection(ArrayList::new));
        // 책 제목으로 검색 //책 객체 반환 //반환값으로 book객체의 메소드 사용 가능
    }

    public ArrayList<Book> searchBooksByIsbn(String searchIsbn) {
        return library.getBooks().stream()
                .filter(book -> book.getIsbn().contains(searchIsbn))
                .collect(Collectors.toCollection(ArrayList::new));
        // 책 ISBN으로 검색 //책 객체 반환 //반환값으로 book객체의 메소드 사용 가능
    }

    public ArrayList<Book> searchBooksByAuthor(String searchAuthor) {
        return library.getBooks().stream()
                .filter(book -> book.getAuthor().contains(searchAuthor))
                .collect(Collectors.toCollection(ArrayList::new));
        // 책 저자로 검색 //책 객체 반환 //반환값으로 book객체의 메소드 사용 가능
    }

    // public ArrayList<Book> filterBooks(boolean showOnLoan, boolean showAvailable) {
    //     TreeSet<Book> books = new TreeSet<>(library.getBooks());
    //     ArrayList<Book> filteredBooks = new ArrayList<>();

    //     if (showOnLoan) {
    //         for (Book book : books) {
    //             if (book.getCurrentLoan() != null) {
    //                 filteredBooks.add(book);
    //             }
    //         }
    //     }
        
    //     if (showAvailable) {
    //         for (Book book : books) {
    //             if (book.getCurrentLoan() == null) {
    //                 filteredBooks.add(book);
    //             }
    //         }
    //     }

    //     return filteredBooks;
    // }
} 