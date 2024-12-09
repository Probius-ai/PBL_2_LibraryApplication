package gui;

import javax.swing.*;         // JFrame, JDialog, JButton, JLabel, JTextField 등 GUI 관련 클래스들
import java.awt.*;            // Container, Dimension, BoxLayout 등 레이아웃 및 크기 관련 클래스들
// import java.util.List;        // List 인터페이스
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import function.*;
import data.*;
import java.util.Set;
import java.util.ArrayList;
// import java.util.TreeSet;
// import java.util.stream.Collectors;
import java.util.List;

public class CopyOfGui7 extends JFrame {
    private LibraryApplication libraryApp;  // LibraryApplication 객체 선언
    private DefaultListModel<Object> model;
    // private DefaultListModel<Object> loanModel;
    private JList<Object> leftList;
    private JList<Object> loanList;
    private JTextArea rightTextArea;
    private BorrowerSearchFunction borrowerSearch;
    private BookSearchFunction bookSearch;

    // 생성자에서 libraryApp 초기화
    public CopyOfGui7(LibraryApplication libraryApp) {
        this.libraryApp = libraryApp;  // 전달받은 LibraryApplication 객체 사용
        this.borrowerSearch = new BorrowerSearchFunction(libraryApp.getLibrary());
        this.bookSearch = new BookSearchFunction(libraryApp.getLibrary());

        // 파일이 존재할 때만 데이터 로드
        try {
            libraryApp.loadLibraryData();
        } catch (Exception e) {
            System.out.println("저장된 데이터가 없습니다. 새로운 데이터베이스를 시작합니다.");
        }

        // 윈도우 종료 시 데이터 저장 부분 수정
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                boolean saveSuccessful = false;
                while (!saveSuccessful) {
                    try {
                        libraryApp.saveLibraryData();
                        saveSuccessful = true;
                        System.exit(0);
                    } catch (Exception ex) {
                        int option = JOptionPane.showConfirmDialog(
                            CopyOfGui7.this,
                            "데이터 저장 중 오류가 발생했습니다.\n다시 시도하시겠습니까?",
                            "저장 오류",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.ERROR_MESSAGE
                        );
                        
                        if (option != JOptionPane.YES_OPTION) {
                            int exitOption = JOptionPane.showConfirmDialog(
                                CopyOfGui7.this,
                                "저장하지 않고 종료하시겠습니까?",
                                "종료 확인",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE
                            );
                            if (exitOption == JOptionPane.YES_OPTION) {
                                System.exit(0);
                            } else {
                                saveSuccessful = false;
                            }
                        }
                    }
                }
            }
        });

        // JFrame 기본 설정
        setTitle("도서관 관리");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);

        // Container 가져오기
        Container container = getContentPane();
        container.setLayout(null);

        // 입력 필드
        JTextField inputField = new JTextField();
        inputField.setBounds(10, 10, 150, 30);
        container.add(inputField);

        // 엔터 키 입력 처리
        inputField.addActionListener(e -> {
                    String searchText = inputField.getText().trim(); // 입력된 텍스트
                    if (!searchText.isEmpty()) {
                        updateBorrowerList(searchText);
                    }
            });

        // 상단 버튼 생성
        JButton bookRegistrationButton = new JButton("신규 책 등록");
        bookRegistrationButton.setBounds(170, 10, 120, 30);
        JButton bookDisplayButton = new JButton("책 표시");
        bookDisplayButton.setBounds(300, 10, 120, 30);
        JButton borrowerRegistrationButton = new JButton("이용자 등록");
        borrowerRegistrationButton.setBounds(430, 10, 120, 30);
        JButton bookReturnButton = new JButton("반납");
        bookReturnButton.setBounds(560, 10, 120, 30);
        container.add(bookRegistrationButton);
        container.add(bookDisplayButton);
        container.add(borrowerRegistrationButton);
        container.add(bookReturnButton);

        // JList와 모델 생성
        model = new DefaultListModel<>();
        leftList = new JList<>(model);
        leftList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // JScrollPane에 JList 추가
        JScrollPane leftScrollPane = new JScrollPane(leftList);
        leftScrollPane.setBounds(10, 50, 150, 300);
        container.add(leftScrollPane);

        // 오른쪽 패널 생성 (JPanel로 묶기)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); // 수직 배치

        // 텍스트 영역 추가
        rightTextArea = new JTextArea();
        rightTextArea.setEditable(false);
        rightTextArea.setPreferredSize(new Dimension(350, 200)); // 텍스트 영역 크기 설정
        rightPanel.add(new JScrollPane(rightTextArea)); // 텍스트 영역을 스크롤 가능한 영역에 추가
        // 버튼 패널 수정
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0)); // 간격 10 추가
        JButton loanButton = new JButton("대출");
        JButton deleteButton = new JButton("삭제"); // 삭제 버튼 추가
        loanButton.setVisible(false);
        deleteButton.setVisible(false); // 초기에는 보이지 않게 설정

        // 버튼 크기 통일
        Dimension buttonSize = new Dimension(100, 30);
        loanButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);

        buttonPanel.add(loanButton);
        buttonPanel.add(deleteButton);

        rightPanel.add(buttonPanel); // 오른쪽 패널에 버튼 패널 추가
        // 오른쪽 패널을 container에 직접 추가
        rightPanel.setBounds(170, 50, 510, 300); // 오른쪽 패널 크기 설정
        container.add(rightPanel); // 컨테이너에 추가
        
        // JList와 모델 생성
        // loanModel = new DefaultListModel<>();
        loanList = new JList<>(model);
        loanList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // // JScrollPane에 JList 추가
        // JScrollPane loanScrollPane = new JScrollPane(loanList);
        // loanScrollPane.setBounds(500, 50, 510, 300);
        // container.add(loanScrollPane);     대출 히스토리 나올 창 

        // 왼쪽 리스트 선택 리스너
        leftList.addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting()) {  // 사용자가 선택을 완료했을 때만 처리
                        Object selectedObject = leftList.getSelectedValue();  // 선택된 객체

                        if (selectedObject != null) {
                            if (selectedObject instanceof Book) {  // 선택된 객체가 Book일 경우
                                Book book = (Book) selectedObject;
                                rightTextArea.setText("Book Details:\n" +
                                    "Title: " + book.getTitle() + "\n" +
                                    "Author: " + book.getAuthor() + "\n" +
                                    "ISBN: " + book.getIsbn() + "\n" +
                                    "On Loan: " + (!book.isAvailable() ? "Yes" : "No"));
                                loanButton.setVisible(false);
                                deleteButton.setVisible(false);

                            } else if (selectedObject instanceof Borrower) {  // 선택된 객체가 Borrower일 경우
                                Borrower borrower = (Borrower) selectedObject;
                                rightTextArea.setText("Borrower Details:\n" +
                                    "Name: " + borrower.getName() + "\n" +
                                    "Id: " + borrower.getBorrowerId());
                                loanButton.setVisible(true);
                                deleteButton.setVisible(true); // Borrower가 선택되면 삭제 버튼도 표시

                            }
                        } else {  // 알 수 없는 객체 타입일 경우
                            rightTextArea.setText("Unknown object type selected.");
                            loanButton.setVisible(false);  // 알 수 없는 타입이 선택되면 loanButton 비활성화
                        }
                    } else {
                        rightTextArea.setText("");  // 선택 해제 시 초기화
                        loanButton.setVisible(false);  // loanButton 비활성화
                    }
            });

        // loanButton 클릭 시 대출 처리
        loanButton.addActionListener(ae -> {
                    // JList에서 선택한 객체를 가져옴
                    Borrower selectedBorrower = (Borrower) leftList.getSelectedValue();
                    if (selectedBorrower != null) {
                        // 책의 ISBN을 입력받는 다이얼로그 띄우기
                        JTextField isbnField = new JTextField();
                        Object[] message = {
                                "Enter ISBN:", isbnField
                            };

                        int option = JOptionPane.showConfirmDialog(this, message, "Loan Book", JOptionPane.OK_CANCEL_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            try {
                                // ISBN을 입력받고, 이를 문자열로 처리
                                String isbn = isbnField.getText().trim();

                                // Library 객체에서 해당 ISBN의 책을 찾기
                                Library library = libraryApp.getLibrary(); // 이미 생성된 Library 객체 가져오기
                                Book bookToLoan = library.findBookByISBN(isbn); // 해당 ISBN으로 책 검색
                                /////////////////////////////////////////
                                // ===== 추가된 코드 시작 =====
                                // ISBN으로 검색된 책 목록 가져오기
                                ArrayList<Book> foundBooks = bookSearch.searchBooksByIsbn(isbn);
                                
                                // 검색된 책이 없는 경우
                                if (foundBooks.isEmpty()) {
                                    JOptionPane.showMessageDialog(this, "해당 ISBN으로 검색된 책이 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                
                                // 검색된 책이 여러 권인 경우 선택 다이얼로그 표시
                                if (foundBooks.size() > 1) {
                                    String[] bookTitles = foundBooks.stream()
                                            .map(book -> book.getTitle() + " (" + book.getIsbn() + ")")
                                            .toArray(String[]::new);
                                            
                                    String selectedBook = (String) JOptionPane.showInputDialog(
                                        this,
                                        "여러 권의 책이 검색되었습니다. 대출할 책을 선택하세요:",
                                        "책 선택",
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        bookTitles,
                                        bookTitles[0]
                                    );
                                    
                                    if (selectedBook == null) {
                                        return; // 사용자가 취소한 경우
                                    }
                                    
                                    // 선택된 책 찾기
                                    String selectedIsbn = selectedBook.substring(
                                        selectedBook.lastIndexOf("(") + 1,
                                        selectedBook.lastIndexOf(")")
                                    );
                                    bookToLoan = foundBooks.stream()
                                            .filter(book -> book.getIsbn().equals(selectedIsbn))
                                            .findFirst()
                                            .orElse(null);
                                } else {
                                    bookToLoan = foundBooks.get(0);
                                }
                                // ===== 추가된 코드 끝 =====
                                // 책을 대출하는 로직
                                if (bookToLoan != null && bookToLoan.isAvailable()) {
                                    // 대출이 가능한 책이면 대출 처리
                                    libraryApp.getBookLoan().loanBook(bookToLoan, selectedBorrower); // 대출 처리 (Library 클래스에서 처리)

                                    // 성공 메시지
                                    JOptionPane.showMessageDialog(this, "Book loaned successfully!");
                                } else if (bookToLoan == null) {
                                    JOptionPane.showMessageDialog(this, "No book found with this ISBN.", "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(this, "This book is already on loan.", "Error", JOptionPane.ERROR_MESSAGE);
                                }

                            } catch (IllegalArgumentException ex){
                                // 기타 예외 처리 (예: 대출할 수 없는 책을 대출하려고 시도 시)
                                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        // Borrower가 선택되지 않은 경우 처리
                        JOptionPane.showMessageDialog(this, "Please select a borrower.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
            });

        // 버튼 클릭 시 동작 정의
        bookRegistrationButton.addActionListener(e -> registerNewBook());
        bookDisplayButton.addActionListener(e -> showBookFilterDialog());
        borrowerRegistrationButton.addActionListener(e -> registerNewBorrower());
        bookReturnButton.addActionListener(e -> returnBook());

        // 삭제 버튼 클릭 이벤트 추가
        deleteButton.addActionListener(ae -> {
            Borrower selectedBorrower = (Borrower) leftList.getSelectedValue();
            if (selectedBorrower != null) {
                int option = JOptionPane.showConfirmDialog(
                    this,
                    String.format("'%s' 이용자를 삭제하시겠습니까?", selectedBorrower.getName()),
                    "이용자 삭제 확인",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

                if (option == JOptionPane.YES_OPTION) {
                    boolean deleted = libraryApp.deleteBorrower(selectedBorrower.getBorrowerId());
                    if (deleted) {
                        JOptionPane.showMessageDialog(
                            this,
                            "이용자가 성공적으로 삭제되었습니다.",
                            "삭제 완료",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        // 리스트에서 선택 해제 및 텍스트 영역 초기화
                        leftList.clearSelection();
                        rightTextArea.setText("");
                        // 버튼 숨기기
                        loanButton.setVisible(false);
                        deleteButton.setVisible(false);
                        // 리스트 모델에서 해당 이용자 제거
                        model.removeElement(selectedBorrower);
                    } else {
                        JOptionPane.showMessageDialog(
                            this,
                            "이용자 삭제에 실패했습니다.",
                            "삭제 실패",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });
    }

    // 신규 책 등록
    private void registerNewBook() {
        JDialog dialog = new JDialog(this, "신규 책 등록", true);
        dialog.setSize(300, 220);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(this);

        //책 제목 입력단
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(20, 20, 100, 30);
        JTextField titleField = new JTextField();
        titleField.setBounds(100, 20, 150, 30);

        //책 저자 입력단
        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(20, 60, 100, 30);
        JTextField authorField = new JTextField();
        authorField.setBounds(100, 60, 150, 30);

        //책 카탈로그 번호 입력단
        JLabel isbnLabel = new JLabel("CatalogNum:");
        isbnLabel.setBounds(20, 100, 100, 30);
        JTextField isbnField = new JTextField();
        isbnField.setBounds(100, 100, 150, 30);

        //확인 버튼
        JButton okButton = new JButton("OK");
        okButton.setBounds(100, 140, 80, 30);
        okButton.addActionListener(ae -> {
                    try {
                        // 사용자 입력 받기
                        String title = titleField.getText();  // 책 제목
                        String author = authorField.getText();  // 책 저자
                        String isbn = isbnField.getText();  // 카탈로그 번호 (숫자)
                        if (title.isEmpty()) {
                            throw new IllegalArgumentException("제목은 비워둘 수 없습니다.");
                        }
                        if (author.isEmpty()) {
                            throw new IllegalArgumentException("저자는 비워둘 수 없습니다.");
                        }
                        if (isbn.isEmpty()) {
                            throw new IllegalArgumentException("isbn은 비워둘 수 없습니다.");
                        }

                        // Book 객체 생성 (ISBN도 필요하면 추가로 처리)

                        // BookRegistrastionFunction을 사용하여 책 등록
                        libraryApp.registerNewBook(isbn, title, author);  // LibraryApplication을 통해 책 등록

                        // 성공 메시지
                        JOptionPane.showMessageDialog(dialog, "책이 성공적으로 등록되었습니다.");
                        dialog.dispose();  // 창 닫기

                    }  catch (Exception ex) {
                        // 기타 예외 처리
                        JOptionPane.showMessageDialog(dialog, "책 등록에 실패했습니다: " + ex.getMessage());
                    }
            });

        dialog.add(titleLabel);
        dialog.add(titleField);
        dialog.add(authorLabel);
        dialog.add(authorField);
        dialog.add(isbnLabel);
        dialog.add(isbnField);
        dialog.add(okButton);

        dialog.setVisible(true);
    }

    public void registerNewBorrower() {
        JDialog dialog = new JDialog(this, "신규 이용자 등록", true);
        dialog.setSize(300, 220);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(this);

        //이름 입력단
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 20, 100, 30);
        JTextField nameField = new JTextField();
        nameField.setBounds(100, 20, 150, 30);

        //아이디 입력단
        JLabel idLabel = new JLabel("Id:");
        idLabel.setBounds(20, 60, 100, 30);
        JTextField idField = new JTextField();
        idField.setBounds(100, 60, 150, 30);

        //확인 버튼
        JButton okButton = new JButton("OK");
        okButton.setBounds(100, 140, 80, 30);
        okButton.addActionListener(ae -> {
                    try {
                        String name = nameField.getText().trim();
                        int id = Integer.parseInt(idField.getText());
                        if (name.isEmpty()) {
                            throw new IllegalArgumentException("이름은 비워둘 수 없습니다.");
                        }

                        // Borrower 객체 생성
                        // Borrower newBorrower = new Borrower(id, name);

                        // LibraryApplication에서 제공하는 등록 기능 활용
                        libraryApp.registerNewBorrower(id, name);

                        // 성공 메시지
                        JOptionPane.showMessageDialog(dialog, "이용자가 성공적으로 등록되었습니다.");
                        dialog.dispose(); // 창 닫기

                    } catch (NumberFormatException ex) {
                        // id가 숫자가 아닌 경우 처리
                        JOptionPane.showMessageDialog(dialog, "ID는 숫자여야 합니다.");
                    } catch (IllegalArgumentException ex) {
                        // 이름 비어있는 경우 처리
                        JOptionPane.showMessageDialog(dialog, ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        // 기타 예외 처리
                        JOptionPane.showMessageDialog(dialog, "이용자 등록에 실패했습니다: " + ex.getMessage());
                    }
            });

        dialog.add(nameLabel);
        dialog.add(nameField);
        dialog.add(idLabel);
        dialog.add(idField);
        dialog.add(okButton);

        dialog.setVisible(true);
    }

    // 책 표시 기능

    // private Book getSelectedBookFromList() {
    //     Object selectedObject = leftList.getSelectedValue();
    //     if (selectedObject instanceof Book) {
    //         return (Book) selectedObject;  // Book 객체 반환
    //     }
    //     return null;  // Book이 선택되지 않으면 null 반환
    // }

    // updateBorrowerList 메서드 수정
    private void updateBorrowerList(String searchName) {
        model.clear(); // 기존 데이터 초기화

        // BorrowerSearchFunction을 사용하여 검색
        ArrayList<Borrower> searchResults = borrowerSearch.searchBorrowersByName(searchName);

        // 검색 결과를 모델에 추가
        for (Borrower borrower : searchResults) {
            model.addElement(borrower);
        }

        // 검색 결과가 없을 때 처리
        if (model.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
            String.format("'%s'에 대한 검색 결과가 없습니다.", searchName));
        }
    }

    private void showBookFilterDialog() {
        JDialog dialog = new JDialog(this, "책 필터 옵션", true);
        dialog.setSize(300, 200);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(this);

        JCheckBox onLoanCheckBox = new JCheckBox("대출 중인 책 표시");
        onLoanCheckBox.setBounds(50, 30, 200, 30);
        JCheckBox availableCheckBox = new JCheckBox("대출 가능한 책 표시");
        availableCheckBox.setBounds(50, 70, 200, 30);

        JButton okButton = new JButton("확인");
        okButton.setBounds(100, 120, 80, 30);
        okButton.addActionListener(ae -> {
                    Set<Book> books = libraryApp.getLibrary().getBookCollection();
                    ArrayList<Book> filteredBooks = new ArrayList<>();

                    // 대출 중인 책 표시 선택되었을때
                    if (onLoanCheckBox.isSelected()) {
                        for (Book book : books) {
                            if (!book.isAvailable()) { // 대출 중인 책
                                filteredBooks.add(book);
                            }
                        }
                    }
                    // 대출 가능한 책 표시 선택되었을때
                    if (availableCheckBox.isSelected()) {
                        for (Book book : books) {
                            if (book.isAvailable()) { // 대출 가능한 책
                                filteredBooks.add(book);
                            }
                        }
                    }

                    // 필터링된 책 목록을 JList에 반영 (model 업데이트)
                    updateModel(filteredBooks);
                    dialog.dispose();  // 다이얼로그 닫기
            });

        dialog.add(onLoanCheckBox);
        dialog.add(availableCheckBox);
        dialog.add(okButton);

        dialog.setVisible(true);
    }

    // 책 목록을 모델에 업데이트하는 메서드
    private void updateModel(ArrayList<Book> filteredBooks) {
        model.clear();  // 기존 모델 초기화

        // 필터링된 책 목록을 모델에 추가
        for (Book book : filteredBooks) {
            model.addElement(book);
        }

        // 책 목록이 비어있는 경우 메시지 표시
        if (filteredBooks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "검색된 책이 없습니다.");
        }
    }
    // 반납 버튼 메소드
    public void returnBook() {
        JDialog dialog = new JDialog(this, "도서 반납", true);
        dialog.setSize(300, 150);
        dialog.setLayout(new BorderLayout(10, 10));

        // 입력 패널 생성
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel isbnLabel = new JLabel("ISBN 번호:");
        JTextField isbnField = new JTextField();
        inputPanel.add(isbnLabel, BorderLayout.WEST);
        inputPanel.add(isbnField, BorderLayout.CENTER);

        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton okButton = new JButton("반납");
        JButton cancelButton = new JButton("취소");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        // 반납 처리
        okButton.addActionListener(ae -> {
            try {
                String isbn = isbnField.getText().trim();
                if (isbn.isEmpty()) {
                    throw new IllegalArgumentException("ISBN을 입력해주세요.");
                }

                // 대출 중인 도서 검색
                List<Book> loanedBooks = libraryApp.getLoanedBookList().searchLoanedBooksByIsbn(isbn);
                
                if (loanedBooks.isEmpty()) {
                    throw new IllegalArgumentException("해당 ISBN으로 대출된 도서를 찾을 수 없습니다.");
                }

                Book bookToReturn;
                if (loanedBooks.size() > 1) {
                    // 여러 권이 검색된 경우 선택 다이얼로그 표시
                    String[] bookOptions = loanedBooks.stream()
                        .map(book -> libraryApp.getLoanedBookList().getLoanInfo(book))
                        .toArray(String[]::new);

                    String selectedBook = (String) JOptionPane.showInputDialog(
                        dialog,
                        "반납할 도서를 선택하세요:",
                        "도서 선택",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        bookOptions,
                        bookOptions[0]
                    );

                    if (selectedBook == null) {
                        return;
                    }

                    String selectedIsbn = selectedBook.substring(
                        selectedBook.indexOf("(") + 1,
                        selectedBook.indexOf(")")
                    );
                    bookToReturn = loanedBooks.stream()
                        .filter(book -> book.getIsbn().equals(selectedIsbn))
                        .findFirst()
                        .orElse(null);
                } else {
                    bookToReturn = loanedBooks.get(0);
                }

                // 반납 처리
                if (bookToReturn != null) {
                    Loan loanToReturn = bookToReturn.getCurrentLoan();
                    libraryApp.getBookReturn().returnBook(loanToReturn);
                    
                    String message = String.format("'%s' 도서가 성공적으로 반납되었습니다.\n대출자: %s", 
                        bookToReturn.getTitle(),
                        loanToReturn.getBorrower().getName());
                        
                    JOptionPane.showMessageDialog(dialog, message, "반납 완료", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                }

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, 
                    ex.getMessage(), 
                    "오류", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "반납 처리 중 오류가 발생했습니다: " + ex.getMessage(), 
                    "오류", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        // 취소 버튼 처리
        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        LibraryApplication libraryApp = new LibraryApplication();  // LibraryApplication 객체 생성
        SwingUtilities.invokeLater(() -> {
                    CopyOfGui7 gui = new CopyOfGui7(libraryApp);  // 생성된 libraryApp을 GUI에 전달
                    gui.setLocationRelativeTo(null); // 윈도우를 화면 중앙에 위치
                    gui.setVisible(true);
            });
    }
}