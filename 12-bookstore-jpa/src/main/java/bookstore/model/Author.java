package bookstore.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = {"books"})
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Author {
	@Id
	@GeneratedValue
	private int id;
	
	@NonNull
	@NotNull
	@Length(min=2, max=60)
	@Column(name = "first_name")
	private String firstName;
	
	@NonNull
	@NotNull
	@Length(min=2, max=60)
	@Column(name = "last_name")
	private String lastName;
	
	@ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
	List<Book> books = new ArrayList<>();
	
	@JsonIgnore
	public String getName() {
		return firstName + " " + lastName;
	}
	
	@JsonIgnore
	public String getBooksAsString() {
		return books.stream()
			.map(book -> book.getTitle())
			.collect(Collectors.joining(", "));
	}
	
}
