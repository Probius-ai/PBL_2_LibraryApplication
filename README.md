# PBL_2_LibraryApplication
도서관관리 시스템에 쓰이는 기능에 관한 코드

import java.util.ArrayList;
import java.util.List;

class Library {
    private List<Book> books;
    private List<Borrower> borrowers;

    public Library() {
        this.books = new ArrayList<>();
        this.borrowers = new ArrayList<>();
    }

    public boolean addBook(Book book) {
        return books.add(book);
    }

    public boolean addBorrower(Borrower borrower) {
        return borrowers.add(borrower);
    }

    public Book findBook(String bookId) {
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                return book;
            }
        }
        return null;
    }

    public Borrower findBorrower(String name) {
        for (Borrower borrower : borrowers) {
            if (borrower.getName().equals(name)) {
                return borrower;
            }
        }
        return null;
    }

    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    public List<Book> getLoanedBooks() {
        List<Book> loanedBooks = new ArrayList<>();
        for (Book book : books) {
            if (!book.isAvailable()) {
                loanedBooks.add(book);
            }
        }
        return loanedBooks;
    }
}

class Book {
    private String id;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = true; // 기본적으로 사용 가능
    }

    public String getId() {
        return id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

class Borrower {
    private String name;

    public Borrower(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class BookLoanService {
    private Library library;

    public BookLoanService(Library library) {
        this.library = library;
    }

    public boolean loanBook(String bookId, String borrowerName) {
        Book book = library.findBook(bookId);
        Borrower borrower = library.findBorrower(borrowerName);

        if (book != null && borrower != null && book.isAvailable()) {
            book.setAvailable(false);
            return true; // 대출 성공
        }
        return false; // 대출 실패
    }
}

class BookReturnService {
    private Library library;

    public BookReturnService(Library library) {
        this.library = library;
    }

    public boolean returnBook(String bookId) {
        Book book = library.findBook(bookId);
        if (book != null) {
            book.setAvailable(true);
            return true; // 반납 성공
        }
        return false; // 반납 실패
    }
}

class BorrowerRegistrationService {
    private Library library;

    public BorrowerRegistrationService(Library library) {
        this.library = library;
    }

    public boolean registerBorrower(String name) {
        if (!isDuplicateName(name)) {
            Borrower borrower = new Borrower(name);
            return library.addBorrower(borrower);
        }
        return false; // 이름 중복
    }

    public boolean isDuplicateName(String name) {
        return library.findBorrower(name) != null;
    }
}

class BookRegistrationService {
    private Library library;

    public BookRegistrationService(Library library) {
        this.library = library;
    }

    public boolean addBook(String id, String title, String author) {
        Book book = new Book(id, title, author);
        return library.addBook(book);
    }

    public boolean isDuplicateCatalogueNumber(String id) {
        return library.findBook(id) != null;
    }
}


도서관 관리 시스템에서 사용되는 객체에 관한 코드

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Library {
    private Set<Book> books;
    private List<Borrower> borrowers;
    private List<Loan> loans;

    public Library() {
        this.books = new HashSet<>();
        this.borrowers = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    public boolean addBook(Book book) {
        return books.add(book);
    }

    public boolean addBorrower(Borrower borrower) {
        return borrowers.add(borrower);
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public Book findBook(String catalogueNumber) {
        for (Book book : books) {
            if (book.getCatalogueNumber().equals(catalogueNumber)) {
                return book;
            }
        }
        return null;
    }

    public Borrower findBorrower(String name) {
        for (Borrower borrower : borrowers) {
            if (borrower.getName().equals(name)) {
                return borrower;
            }
        }
        return null;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
    }
}

class Book {
    private String author;
    private String catalogueNumber;
    private boolean isAvailable;
    private String title;

    public Book(String author, String catalogueNumber, String title) {
        this.author = author;
        this.catalogueNumber = catalogueNumber;
        this.title = title;
        this.isAvailable = true; // 기본적으로 사용 가능
    }

    public String getAuthor() {
        return author;
    }

    public String getCatalogueNumber() {
        return catalogueNumber;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Book)) return false;
        Book book = (Book) obj;
        return catalogueNumber.equals(book.catalogueNumber);
    }

    @Override
    public int hashCode() {
        return catalogueNumber.hashCode();
    }

    @Override
    public String toString() {
        return title + " by " + author;
    }
}

class Borrower {
    private String name;

    public Borrower(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Borrower)) return false;
        Borrower borrower = (Borrower) obj;
        return name.equals(borrower.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}

class Loan {
    private Book book;
    private Borrower borrower;
    private LocalDate loanDate;

    public Loan(Book book, Borrower borrower) {
        this.book = book;
        this.borrower = borrower;
        this.loanDate = LocalDate.now();
    }

    public Book getBook() {
        return book;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Loan)) return false;
        Loan loan = (Loan) obj;
        return book.equals(loan.book) && borrower.equals(loan.borrower);
    }

    @Override
    public int hashCode() {
        return book.hashCode() + borrower.hashCode();
    }

    @Override
    public String toString() {
        return "Loan of " + book + " to " + borrower + " on " + loanDate;
    }
}

