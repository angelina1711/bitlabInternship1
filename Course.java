package bitlabInternship.lmsSystem.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Конструктор без аргументов (обязателен для JPA)
    public Course() {
    }

    // Геттер и сеттер для id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Геттер и сеттер для name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Переопределение метода toString
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
