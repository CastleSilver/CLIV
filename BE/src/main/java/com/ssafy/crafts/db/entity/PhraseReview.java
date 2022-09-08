package com.ssafy.crafts.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@DynamicInsert
@Entity
@Table(name = "PHRASE_REVIEW")
public class PhraseReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pr_id")
    private int id;

    @Column(nullable = false, length = 20)
    private String content;     // 리뷰문구 내용

    @ManyToMany(mappedBy = "")
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public PhraseReview(int id, String content) {
        this.id = id;
        this.content = content;
    }
}
