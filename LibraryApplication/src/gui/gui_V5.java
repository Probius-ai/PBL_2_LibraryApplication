import javax.swing.*;
import java.util.ArrayList;
import java.util.TreeSet;
import java.awt.*;

public class gui_V5 extends JFrame {

    private LibraryApplication lib;
    private DefaultListModel<Object> model;
    private JList<Object> leftList;
    private JTextArea rightTextArea;

    public gui_V5() {
        // JFrame 기본 설정
        setTitle("Dynamic JList Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);

        // Container 가져오기
        Container container = getContentPane();
        container.setLayout(null);

        // LibraryApplication 초기화
        lib = new LibraryApplication();

        // 입력 필드
        JTextField inputField = new JTextField();
        inputField.setBounds(10, 10, 150, 30);
        container.add(inputField);
        // 입력 필드
        inputField.addActionListener(ae -> {
                    String searchName = inputField.getText().trim(); // 입력된 이름 가져오기
                    if (searchName.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "검색어를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Borrower 검색
                    Borrower foundBorrower = lib.searchBorrower(searchName);
                    model.clear(); // 이전 검색 결과 초기화

                    if (foundBorrower != null) {
                        // 검색 결과를 JList에 추가
                        model.addElement("이름: " + foundBorrower.getName());
                    } else {
                        model.addElement("검색 결과가 없습니다.");
                    }
            });

        // 상단 버튼 생성
        JButton button1 = new JButton("신규 책 등록");
        button1.setBounds(170, 10, 120, 30);
        JButton button2 = new JButton("책 표시");
        button2.setBounds(300, 10, 120, 30);
        JButton button3 = new JButton("Show List 3");
        button3.setBounds(430, 10, 120, 30);
        JButton button4 = new JButton("Clear List");
        button4.setBounds(560, 10, 120, 30);
        container.add(button1);
        container.add(button2);
        container.add(button3);
        container.add(button4);

        // JList와 모델 생성
        model = new DefaultListModel<>();
        leftList = new JList<>(model);
        leftList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // JScrollPane에 JList 추가
        JScrollPane leftScrollPane = new JScrollPane(leftList);
        leftScrollPane.setBounds(10, 50, 150, 300);
        container.add(leftScrollPane);


        // 오른쪽 텍스트 영역
        rightTextArea = new JTextArea();
        rightTextArea.setEditable(false);
        JScrollPane rightScrollPane = new JScrollPane(rightTextArea);
        rightScrollPane.setBounds(170, 50, 510, 300);
        container.add(rightScrollPane);

        // JList 항목 클릭 시 세부 정보 표시
        leftList.addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting()) {
                        Object selectedObject = leftList.getSelectedValue();
                        if (selectedObject != null) {
                            if (selectedObject instanceof Book) {
                                Book book = (Book) selectedObject;
                                rightTextArea.setText("Book Details:\n" +
                                    "Title: " + book.getTitle() + "\n" +
                                    "Author: " + book.getAuthor() + "\n" +
                                    "Catalog Number: " + book.getUniqueCatalogNum() + "\n" +
                                    "On Loan: " + (book.loanCheck() ? "Yes" : "No"));
                            } else {
                                rightTextArea.setText("Unknown object type selected.");
                            }
                        }
                    }
            });

        // 버튼 이벤트 처리
        button1.addActionListener(e -> registerNewBook());
        button2.addActionListener(e -> showBookFilterDialog());
        button3.addActionListener(e -> registerNewBorrower());
        button4.addActionListener(e -> clearList());
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
                        int catalogNum = Integer.parseInt(catalogNumField.getText());
                        lib.addBook(title, author, catalogNum);
                        dialog.dispose();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(dialog, "Catalog Number는 숫자여야 합니다.");
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
        JDialog dialog = new JDialog(this, "신규 책 등록", true);
        dialog.setSize(300, 220);
        dialog.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 20, 100, 30);
        JTextField nameField = new JTextField();
        nameField.setBounds(100, 20, 150, 30);

        JButton okButton = new JButton("OK");
        okButton.setBounds(100, 140, 80, 30);
        okButton.addActionListener(ae -> {
                    try {
                        String name = nameField.getText().trim();
                        if (name.isEmpty()) {
                            throw new IllegalArgumentException("이름은 비워둘 수 없습니다.");
                        }
                        lib.addBorrower(name); // 이름 추가
                        JOptionPane.showMessageDialog(dialog, "등록이 완료되었습니다.");
                        dialog.dispose();
                    } catch (IllegalArgumentException ex) {
                        // IllegalArgumentException에 대한 메시지 처리
                        JOptionPane.showMessageDialog(dialog, ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
                    }
            });

        dialog.add(nameLabel);
        dialog.add(nameField);
        dialog.add(okButton);

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
                    TreeSet<Book> books = lib.getBooks();
                    ArrayList<Book> filteredBooks = new ArrayList<>();

                    if (onLoanCheckBox.isSelected()) {
                        for (Book book : books) {
                            if (book.loanCheck()) {
                                filteredBooks.add(book);
                            }
                        }
                    }
                    if (availableCheckBox.isSelected()) {
                        for (Book book : books) {
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
    private void updateModel(ArrayList<Book> books) {
        model.clear();
        for (Book book : books) {
            model.addElement(book);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
                    CopyOfGui5 gui = new CopyOfGui5();
                    gui.setVisible(true);
            });
    }
}
