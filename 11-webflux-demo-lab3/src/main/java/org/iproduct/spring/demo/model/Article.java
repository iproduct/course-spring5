package org.iproduct.spring.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
//@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    private String id;
    private String title;
    private String content;

    @CreatedDate
    private LocalDateTime createdDate;
}