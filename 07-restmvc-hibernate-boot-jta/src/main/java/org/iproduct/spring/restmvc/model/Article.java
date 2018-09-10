package org.iproduct.spring.restmvc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@JsonView(Views.Article.class)
public class Article {
//    @Id
    private Long id;

    @NonNull
    @Length(min=3, max=80)
    private String title;

    @NonNull
    @Length(min=3, max=2048)
    private String content;

    private User author;

    @Length(min=3, max=256)
    private String pictureUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created = new Date();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated = new Date();
    
}
