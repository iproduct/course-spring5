package course.hibernate.spring.entity;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedEntityGraph(name = "Person.withBooks",
        attributeNodes = {@NamedAttributeNode(value = "books", subgraph = "booksData")},
        subgraphs = @NamedSubgraph(name = "booksData", attributeNodes = {@NamedAttributeNode("title")}))
public class Person {
    @Id
    private Long id;
    @NotNull
    @Size(min = 2, max = 20)
    private String firstName;
    @NotNull
    @Size(min = 2, max = 20)
    private String lastName;
    @Past
    LocalDate dateOfBirth;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    @ToString.Exclude
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    @Where(clause = "year > 2015")
    private List<Book> books = new ArrayList<>();

    public Person(Long id, @NotNull String firstName, @NotNull String lastName, LocalDate dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
}

