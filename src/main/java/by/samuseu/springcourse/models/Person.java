package by.samuseu.springcourse.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;

    @Column(name = "person_name")
    @NotEmpty(message = "Укажите ФИО")
    @Size(min = 2, max = 30, message = "ФИО должно быть от 2 до 30 символов")
    @Pattern(regexp = "[А-Я][а-я]+\\s[А-Я][а-я]+\\s[А-Я][а-я]+$", message = "ФИО в формате: Иванов Иван Иванович")
    private String personName;

    @Column(name = "person_year_of_birth")
    @Min(value = 1900, message = "Год рождения должен быть больше 1900 года")
    private int personYearOfBirth;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Book> personBooks;

    public Person() {

    }

    public Person(String personName, int personYearOfBirth) {
        this.personName = personName;
        this.personYearOfBirth = personYearOfBirth;
    }

    public List<Book> getPersonBooks() {
        return personBooks;
    }

    public void setPersonBooks(List<Book> personBooks) {
        this.personBooks = personBooks;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getPersonYearOfBirth() {
        return personYearOfBirth;
    }

    public void setPersonYearOfBirth(int personYearOfBirth) {
        this.personYearOfBirth = personYearOfBirth;
    }
}