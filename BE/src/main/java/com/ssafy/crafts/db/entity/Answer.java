package com.ssafy.crafts.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@DynamicInsert
@Entity
@Table(name = "ANSWER")
public class Answer {

    @Id
    @Column(name = "ans_id")
    private int id;

    @OneToOne
    @MapsId // @id로 지정한 컬럼에 @OneToOne 이나 @ManyToOne 관계를 매핑시키는 역할
    @JoinColumn(name = "ans_id")
    private QnA qna;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(name = "regdate",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp regdate;

    @Builder
    public Answer(int id, QnA qna, String content, Timestamp regdate) {
        this.id = id;
        this.qna = qna;
        this.content = content;
        this.regdate = regdate;
    }
}
