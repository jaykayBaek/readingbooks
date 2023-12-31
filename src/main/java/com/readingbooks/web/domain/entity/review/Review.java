package com.readingbooks.web.domain.entity.review;

import com.readingbooks.web.domain.entity.BaseEntity;
import com.readingbooks.web.domain.entity.book.Book;
import com.readingbooks.web.domain.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(length = 2000)
    private String content;
    private Integer starRating;
    private boolean isPurchased;
    private boolean isHidden;
    private int likesCount;
    private int commentsCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewComment> reviewComments = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewLikeLog> logs = new ArrayList<>();

    public static Review createReview(Member member, Book book, String content, int starRating, boolean isPurchased) {
        Review review = new Review();
        review.member = member;
        review.book = book;
        review.content = content;
        review.starRating = starRating;
        review.likesCount = 0;
        review.commentsCount = 0;
        review.isHidden = false;
        review.isPurchased = isPurchased;
        return review;
    }

    public void update(String content, int starRating) {
        this.content = content;
        this.starRating = starRating;
    }

    public void addCommentsCount(){
        commentsCount++;
    }

    public void subtractCommentsCount(){
        commentsCount--;
    }

    public void addLikesCount(){
        likesCount++;
    }

    public void subtractLikesCount(){
        likesCount--;
    }

    public void update(String content) {
        this.content = content;
    }
}
