package course.hibernate.spring.entity;

import lombok.*;
import org.aspectj.lang.annotation.RequiredTypes;
import org.hibernate.annotations.*;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
//import org.springframework.cache.annotation.Cacheable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static org.hibernate.id.UUIDGenerator.UUID_GEN_STRATEGY_CLASS;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE,
        region="course.hibernate.spring.entity.Book", include = "all")
public class Book {
    @Id
    private Long id;
    private String title;

    @ManyToOne(fetch = LAZY)
    private Person author;

    @NaturalId
    private String isbn;

    private int year;

    @Basic(fetch=LAZY)
    private String summary;

    @Lob
    @Basic(fetch=LAZY)
    @LazyGroup("lobs")
    private byte[] image;

    @Lob
    @Basic(fetch=LAZY)
    @LazyGroup("lobs")
    @Column(name="sample_content", columnDefinition="BLOB NOT NULL")
    private String sampleContent;

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
