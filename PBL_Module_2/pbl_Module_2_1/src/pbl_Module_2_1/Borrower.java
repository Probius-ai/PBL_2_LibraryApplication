package pbl_Module_2_1;

import java.util.Objects;

public class Borrower {
    private String name;

    public Borrower(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrower borrower = (Borrower) o;
        return Objects.equals(name, borrower.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Borrower{" +
                "name='" + name + '\'' +
                '}';
    }
}
