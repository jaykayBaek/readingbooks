package com.readingbooks.web.domain.entity.author;

import com.readingbooks.web.domain.entity.BaseEntity;
import com.readingbooks.web.domain.enums.AuthorOption;
import com.readingbooks.web.domain.enums.Gender;
import com.readingbooks.web.service.manage.author.AuthorRegisterRequest;
import com.readingbooks.web.service.manage.author.AuthorUpdateRequest;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Author extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private AuthorOption authorOption;

    private String nationality;
    private String description;
    private String birthYear;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public static Author createAuthor(AuthorRegisterRequest request){
        Author author = new Author();
        author.name = request.getName();
        author.authorOption = request.getAuthorOption();
        author.nationality = request.getNationality();
        author.description = request.getDescription();
        return author;
    }

    public void updateAuthor(AuthorUpdateRequest request){
        this.name = request.getName();
        this.authorOption = request.getAuthorOption();
        this.nationality = request.getNationality();
        this.description = request.getDescription();
    }
}
