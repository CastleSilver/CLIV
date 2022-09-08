package com.ssafy.crafts.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "CLASS")
@NoArgsConstructor
@DynamicInsert
@Entity
@ToString
public class ClassInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private int id;
    @OneToMany(mappedBy = "classInfo")
    private List<QnA> qnaList = new ArrayList<>();

    @OneToMany(mappedBy = "classInfo")
    private List<Review> reviewList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne
    @JoinColumn(name = "teacher_id")
    private Member teacher;

    @ManyToMany
    @JoinTable(name = "CLASS_MEMBER",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id_array"))
    private List<Member> members = new ArrayList<Member>();

    @ManyToMany
    @JoinTable(name = "TAGGING",
            joinColumns = { @JoinColumn(name = "class_id", referencedColumnName = "class_id")},
            inverseJoinColumns = @JoinColumn(name = "hashtag_id", referencedColumnName = "hashtag_id"))
    private List<Hashtag> tagging = new ArrayList<>();

    @Column(name = "class_name", nullable = false, length = 100)
    private String className;

    @Column(name = "class_datetime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp classDatetime;

    @Column(columnDefinition = "TINYINT", nullable = false, length = 1)
    private int headcount;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false, length = 3000)
    private String content;

    @Column(length = 200)
    private String classImg;

    @Column(columnDefinition = "TINYINT", nullable = false, length = 1)
    private int level;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10, nullable = false)
    private ClassStatus classStatus;

    @Column(name = "session_id", length = 20)
    private String sessionId;

    @Column(name = "regdate",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp regdate;

    @Builder
    public ClassInfo(int id, List<QnA> qnaList, List<Review> reviewList, Category category, Member teacher, List<Member> members, List<Hashtag> tagging, String className, Timestamp classDatetime, int headcount, int price, String content, String classImg, int level, ClassStatus classStatus, String sessionId, Timestamp regdate) {
        this.id = id;
        this.qnaList = qnaList;
        this.reviewList = reviewList;
        this.category = category;
        this.teacher = teacher;
        this.members = members;
        this.tagging = tagging;
        this.className = className;
        this.classDatetime = classDatetime;
        this.headcount = headcount;
        this.price = price;
        this.content = content;
        this.classImg = classImg;
        this.level = level;
        this.classStatus = classStatus;
        this.sessionId = sessionId;
        this.regdate = regdate;
    }

    public void addReviewList(Review review) {
        this.reviewList.add(review);
    }

    public void setTagging(List<Hashtag> tagging) {
        this.tagging = tagging;
    }

    public void addTagging(Hashtag hashtag) {
        this.tagging.add(hashtag);
        hashtag.getClasses().add(this);
    }

    public void addMember(Member member) {
        this.members.add(member);
    }
    public void addQnaList(QnA qna) {
        this.qnaList.add(qna);
    }

    public void setCategory(Category category) {
        this.category = category;
        if (!category.getClasses().contains(this)) {
            category.getClasses().add(this);
        }
    }

    public static enum ClassStatus {
        EXPECTED,
        LIVE,
        ENDED
    }
}
