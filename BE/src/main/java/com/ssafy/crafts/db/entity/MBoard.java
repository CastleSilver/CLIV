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
@Entity
@NoArgsConstructor
@DynamicInsert
@Table(name = "MBOARD")
public class MBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mboard_id")
    private int id;     // PK

    @Column(nullable = false, length = 50)
    private String title;   // 제목

    @Column(name = "wanted_day", nullable = false, length = 50)
    private String wantedDay;       // 원하는 수업 날짜

    @Column(name = "teacher_gender", nullable = false, length = 1)
    private String teacherGender;   // 강사 성별

    @Column(nullable = false, length = 500)
    private String content;     // 내용

    @Column(name = "regdate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp regDate;  // 작성 시간

    @Column(name = "mat_status", length = 1)
    @Convert(converter = BooleanToYNConverter.class)
    private boolean matStatus = false;      // 매칭 완료 여부

    // 1:1 관계 : 매칭보드 - 매칭_선생님
    @OneToMany(mappedBy = "mBoard")
    private List<MBoardTeacher> mBoardTeacher = new ArrayList<>();

    // 1:1 관계 : 매칭보드 - 카테고리
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // N:1 관계 : 매칭보드 - 회원
    @ManyToOne
    @JoinColumn(name = "auth_id")
    private Member member;

    @Column
    private int price;
    @Builder
    public MBoard(int id, String title, String wantedDay, String teacherGender, String content, Timestamp regDate, boolean matStatus, List<MBoardTeacher> mBoardTeacher, Category category, Member member, int price) {
        this.id = id;
        this.title = title;
        this.wantedDay = wantedDay;
        this.teacherGender = teacherGender;
        this.content = content;
        this.regDate = regDate;
        this.matStatus = matStatus;
        this.mBoardTeacher = mBoardTeacher;
        this.category = category;
        this.member = member;
        this.price = price;
    }

    public void updateMBoard(String title, String content, String wantedDay){
        this.title = title;
        this.content = content;
        this.wantedDay = wantedDay;
    }

}