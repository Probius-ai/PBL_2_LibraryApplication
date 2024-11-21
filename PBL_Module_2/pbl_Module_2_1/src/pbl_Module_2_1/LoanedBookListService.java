package pbl_Module_2_1;

public class LoanedBookListService {
    private Library library;

    public LoanedBookListService(Library library) {
        this.library = library;
    }

    public List<Loan> getLoanedBooks() {
        return library.getLoans().stream()
                      .sorted(Comparator.comparing(loan -> loan.getBook().getCatalogueNumber()))
                      .collect(Collectors.toList());
    }
}
