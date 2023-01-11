package course.hibernate.spring.entity;

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hibernate.id.UUIDGenerator.UUID_GEN_STRATEGY_CLASS;

@Entity
@Table(name = "books")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book {
    private  Long id;
    private String title;
    private List<Author> authors = new ArrayList<>();
    @Access(AccessType.FIELD)
    @Version
    private int version;
    private String isbn;

    public Book() {
    }

    public Book(String title, List<Author> authors, String isbn) {
        this.title = title;
        this.authors = authors;
        this.isbn = isbn;
    }

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "books-custom-id"
    )
    @GenericGenerator(
            name="books-custom-id",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name="sequence_name", value="book_sequence"),
                    @Parameter(name="initial_value", value="1"),
                    @Parameter(name="increment_size", value="5"),
                    @Parameter(name="optimizer", value="pooled-lo")
            }
    )
//    @GeneratedValue( generator = "custom-uuid" )
//    @GenericGenerator(
//            name = "custom-uuid",
//            strategy = "org.hibernate.id.UUIDGenerator",
//            parameters = {
//                    @Parameter(
//                            name = UUID_GEN_STRATEGY_CLASS,
//                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
//                    )
//            }
//    )
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "books_table_generator")
//    @TableGenerator(
//            name = "books_table_generator",
//            table= "generated_values",
//            pkColumnName = "entity_name",
//            valueColumnName = "last_id",
//            allocationSize = 5,
//            initialValue = 10000
//    )
    public Long getId(){
        return id;
    }

    public void setId( Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Author.class)
    @CollectionTable(name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id",
                    foreignKey = @ForeignKey(name = "fk_book_author")))
    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @NaturalId()
    @Basic(optional = false)
    @Column(nullable = false)
    public String getIsbn() {
        return isbn;
    }


    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        return id != null ? id.equals(book.id) : book.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
