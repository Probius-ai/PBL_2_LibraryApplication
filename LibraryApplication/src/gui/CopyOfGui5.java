package gui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.TreeSet;
import java.awt.*;
import java.util.Set;

public class CopyOfGui5 extends JFrame {

    private function.LibraryApplication libApp;
    private DefaultListModel<Object> model;
    private JList<Object> leftList;
    private JTextArea rightTextArea;

    public CopyOfGui5() {
        // JFrame 기본 설정
        setTitle("도서관 관리");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);

        // Container 가져오기
        Container container = getContentPane();
        container.setLayout(null);

        // LibraryApplication 초기화
        libApp = new function.LibraryApplication();

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
        JButton registerBook = new JButton("신규 책 등록");
        registerBook.setBounds(170, 10, 120, 30);
        JButton showBook = new JButton("책 표시");
        showBook.setBounds(300, 10, 120, 30);
        JButton registerBorrower = new JButton("이용자 등록");
        registerBorrower.setBounds(430, 10, 120, 30);
        JButton returnBook = new JButton("반납");
        returnBook.setBounds(560, 10, 120, 30);
        container.add(registerBook);
        container.add(showBook);
        container.add(registerBorrower);
        container.add(returnBook);

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

        // 버튼 추가
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); // 버튼을 FlowLayout으로 설정 (중앙 정렬 등)
        JButton loanButton = new JButton("대출");
        loanButton.setVisible(false); // 초기에는 보이지 않게 설정
        buttonPanel.add(loanButton); // 버튼을 버튼 패널에 추가

        // JButton resturnButton = new JButton("반납");
        // resturnButton.setVisible(false); // 초기에는 보이지 않게 설정
        // buttonPanel.add(resturnButton); // 버튼을 버튼 패널에 추가

        rightPanel.add(buttonPanel); // 오른쪽 패널에 버튼 패널 추가
        // 오른쪽 패널을 container에 직접 추가
        rightPanel.setBounds(170, 50, 510, 300); // 오른쪽 패널 크기 설정
        container.add(rightPanel); // 컨테이너에 추가

       

        // JList 항목 클릭 시 세부 정보 표시
        leftList.addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting()) { // 사용자가 선택을 완료했을 때만 처리
                        Object selectedObject = leftList.getSelectedValue();
                        if (selectedObject != null) {
                            if (selectedObject instanceof data.Book) { // Book 객체일 때 처리
                                data.Book book = (data.Book) selectedObject;
                                rightTextArea.setText("Book Details:\n" +
                                    "Title: " + book.getTitle() + "\n" +
                                    "Author: " + book.getAuthor() + "\n" +
                                    "ISBN: " + book.getIsbn() + "\n" +
                                    "On Loan: " + (book.loanCheck() ? "Yes" : "No"));
                                loanButton.setVisible(false); // 버튼 비활성화
                            } else if (selectedObject instanceof data.Borrower) { // Borrower 객체일 때 처리
                                data.Borrower borrower = (data.Borrower) selectedObject;
                                rightTextArea.setText("Borrower Details:\n" +
                                    "Name: " + borrower.getName() + "\n" +
                                    "Id: " + borrower.getBorrowerId());
                                // Borrower에 다른 정보가 있다면 여기 추가
                                loanButton.setVisible(true); // 버튼 활성화

                               
                            }
                        } else { // 알 수 없는 객체 타입일 경우
                            rightTextArea.setText("Unknown object type selected.");
                            loanButton.setVisible(false); // 버튼 비활성화
                        }
                    } else {
                        rightTextArea.setText(""); // 선택 해제 시 초기화
                        loanButton.setVisible(false); // 버튼 비활성화
                    }

            });

        // 오른쪽 텍스트 영역 및 버튼 코드 (변경된 부분)
        loanButton.addActionListener(ae -> {
            data.Borrower selectedBorrower = (data.Borrower) leftList.getSelectedValue();
            if (selectedBorrower != null) {
                String isbn = JOptionPane.showInputDialog(this, "Enter ISBN:");
                if (isbn != null && !isbn.isEmpty()) {
                    try {
                        data.Book book = libApp.getLibrary().findBookByISBN(isbn);
                        if (book != null) {
                            libApp.processBookLoan(book, selectedBorrower);
                            JOptionPane.showMessageDialog(this, "대출이 완료되었습니다.");
                        } else {
                            JOptionPane.showMessageDialog(this, "해당 ISBN의 책을 찾을 수 없습니다.");
                        }
                    } catch (IllegalStateException | IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        // 버튼 이벤트 처리
        registerBook.addActionListener(e -> registerNewBook());
        showBook.addActionListener(e -> 
                showBookFilterDialog());
        registerBorrower.addActionListener(e -> 
                registerNewBorrower());
        button4.addActionListener(e -> 
                returnBook());
    }

    // 신규 책 등록
    private void registerNewBook() {
        JDialog dialog = new JDialog(this, "신규 책 등록", true);
        dialog.setSize(300, 220);
        dialog.setLayout(null);

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(20, 20, 100, 30);
        JTextField titleField = new JTextField();
        titleField.setBounds(100, 20, 150, 30);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(20, 60, 100, 30);
        JTextField authorField = new JTextField();
        authorField.setBounds(100, 60, 150, 30);

        JLabel catalogNumLabel = new JLabel("CatalogNum:");
        catalogNumLabel.setBounds(20, 100, 100, 30);
        JTextField catalogNumField = new JTextField();
        catalogNumField.setBounds(100, 100, 150, 30);

        JButton okButton = new JButton("OK");
        okButton.setBounds(100, 140, 80, 30);
        okButton.addActionListener(ae -> {
                    try {
                        String title = titleField.getText();
                        String author = authorField.getText();
                        String isbn = catalogNumField.getText(); // catalogNum을 isbn으로 사용
                        
                        data.Book newBook = new data.Book(isbn, title, author);
                        libApp.registerNewBook(newBook);
                        dialog.dispose();
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(dialog, ex.getMessage());
                    }
            });

        dialog.add(titleLabel);
        dialog.add(titleField);
        dialog.add(authorLabel);
        dialog.add(authorField);
        dialog.add(catalogNumLabel);
        dialog.add(catalogNumField);
        dialog.add(okButton);

        dialog.setVisible(true);
    }

    // 신규 이용자 등록
    public void registerNewBorrower(){
        JDialog dialog = new JDialog(this, "신규 이용자 등록", true);
        dialog.setSize(300, 220);
        dialog.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 20, 100, 30);
        JTextField nameField = new JTextField();
        nameField.setBounds(100, 20, 150, 30);

        JLabel idLabel = new JLabel("Id:");
        idLabel.setBounds(20, 60, 100, 30);
        JTextField idField = new JTextField();
        idField.setBounds(100, 60, 150, 30);

        JButton okButton = new JButton("OK");
        okButton.setBounds(100, 140, 80, 30);
        okButton.addActionListener(ae -> {
                    try {
                        String name = nameField.getText().trim();
                        int id = Integer.parseInt(idField.getText());
                        
                        data.Borrower newBorrower = new data.Borrower(id, name);
                        libApp.registerNewBorrower(newBorrower);
                        JOptionPane.showMessageDialog(dialog, "등록이 완료되었습니다.");
                        dialog.dispose();
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(dialog, ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
                    }
            });

        dialog.add(nameLabel);
        dialog.add(nameField);
        dialog.add(idLabel);
        dialog.add(idField);
        dialog.add(okButton);

        dialog.setVisible(true);

    }

    public void returnBook() {
        JDialog dialog = new JDialog(this, "반납", true);
        dialog.setSize(300, 220);
        dialog.setLayout(null);

        JLabel catalogNumLabel = new JLabel("CatalogNum:");
        catalogNumLabel.setBounds(20, 100, 100, 30);
        JTextField catalogNumField = new JTextField();
        catalogNumField.setBounds(100, 100, 150, 30);

        JButton okButton = new JButton("OK");
        okButton.setBounds(100, 140, 80, 30);
        okButton.addActionListener(ae -> {
                    try {
                        String isbn = catalogNumField.getText();
                        data.Book book = libApp.getLibrary().findBookByISBN(isbn);
                        if (book != null && book.getCurrentLoan() != null) {
                            libApp.processBookReturn(book.getCurrentLoan());
                            JOptionPane.showMessageDialog(dialog, "반납이 완료되었습니다.");
                            dialog.dispose();
                        } else {
                            JOptionPane.showMessageDialog(dialog, "대출 중인 책이 아닙니다.");
                        }
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(dialog, ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
                    }
            });

        dialog.add(catalogNumLabel);
        dialog.add(catalogNumField);
        dialog.add(okButton);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // 책 필터 다이얼로그 표시
    private void showBookFilterDialog() {
        JDialog dialog = new JDialog(this, "책 필터 옵션", true);
        dialog.setSize(300, 200);
        dialog.setLayout(null);

        JCheckBox onLoanCheckBox = new JCheckBox("대출 중인 책 표시");
        onLoanCheckBox.setBounds(50, 30, 200, 30);
        JCheckBox availableCheckBox = new JCheckBox("대출 가능한 책 표시");
        availableCheckBox.setBounds(50, 70, 200, 30);

        JButton okButton = new JButton("확인");
        okButton.setBounds(100, 120, 80, 30);
        okButton.addActionListener(ae -> {
                    Set<data.Book> books = libApp.getLibrary().getBooks(); // Library를 통해 책 목록 가져오기
                    ArrayList<data.Book> filteredBooks = new ArrayList<>();

                    if (onLoanCheckBox.isSelected()) {
                        for (data.Book book : books) {
                            if (book.loanCheck()) {
                                filteredBooks.add(book);
                            }
                        }
                    }
                    if (availableCheckBox.isSelected()) {
                        for (data.Book book : books) {
                            if (!book.loanCheck()) {
                                filteredBooks.add(book);
                            }
                        }
                    }

                    updateModel(filteredBooks);
                    dialog.dispose();
            });

        dialog.add(onLoanCheckBox);
        dialog.add(availableCheckBox);
        dialog.add(okButton);

        dialog.setVisible(true);
    }

    // 리스트 초기화
    private void clearList() {
        model.clear();
        rightTextArea.setText("");
    }

    // 리스트 모델 업데이트
    private void updateModel(ArrayList<data.Book> books) {
        model.clear();
        for (data.Book book : books) {
            model.addElement(book);
        }
    }

    private void updateBorrowerList(String searchName) {
        model.clear();
        Set<data.Borrower> allBorrowers = libApp.getLibrary().getBorrowers(); // Library를 통해 대출자 목록 가져오기
        
        for (data.Borrower borrower : allBorrowers) {
            if (borrower.getName().contains(searchName)) {
                model.addElement(borrower);
            }
        }

        if (model.isEmpty()) {
            JOptionPane.showMessageDialog(this, "검색 결과가 없습니다: " + searchName);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
                    CopyOfGui5 gui = new CopyOfGui5();
                    gui.setVisible(true);
            });
    }
}