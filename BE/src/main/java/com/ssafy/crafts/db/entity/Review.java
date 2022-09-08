package com.ssafy.crafts.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@DynamicInsert
@Entity
@Table(name = "REVIEW")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int id;     // 리뷰 id

    @Column(nullable = false)
    private int score;      // 별점

//    @ElementCollection(fetch = FetchType.LAZY)
//    @CollectionTable(name = "SELECTED_PHRASE",joinColumns = @JoinColumn(name="review_id"))
//    @Column(name = "phrase_review_id", nullable = false)
//    private List<int> prList = new ArrayList<>();   // 리뷰문구 리스트 (prId로 저장)

    @Column(name = "text_review", length = 500)
    private String textRv;      // 텍스트 리뷰

    @Column(name = "regdate",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp regDate;  // 작성일

    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassInfo classInfo;

    @ManyToOne
    @JoinColumn(name = "auth_id")
    private Member member;

    @ManyToMany
    @JoinTable(name = "SELECTED_PHRASE",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "pr_id_arr"))
    private List<PhraseReview> prList = new ArrayList<>();      // 선택한 리뷰문구 리스트

    @Builder
    public Review(int id, int score, String textRv, Timestamp regDate, ClassInfo classInfo, Member member, List<PhraseReview> prList) {
        this.id = id;
        this.score = score;
        this.textRv = textRv;
        this.regDate = regDate;
        this.classInfo = classInfo;
        this.member = member;
        this.prList = prList;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }
}