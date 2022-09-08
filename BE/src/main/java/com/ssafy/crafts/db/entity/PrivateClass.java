package com.ssafy.crafts.db.entity;

import io.micrometer.core.annotation.Counted;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Entity
@NoArgsConstructor
@DynamicInsert
@Table(name = "PRIVATE_CLASS")
public class PrivateClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pc_id")
    private int id;     // id

    @Column(name = "class_name", nullable = false)
    private String className;   // 수업명

    @Column(name = "class_datetime", nullable = false)
    private String classDatetime;   // 수업일

    @Column(name = "tuition_fee", nullable = false)
    private int tuitionFee;      // 수강료

//    @Column(name = "mt_id")
//    private int mtId;   // 선생님_매칭보드 id

    // 1:1 관계 - 1:1수업 - 매칭_선생님
    @OneToOne
    @JoinColumn(name = "mt_id", nullable = false)
    private MBoardTeacher mBoardTeacher;

    @Builder
    public PrivateClass(int id, String className, String classDatetime, int tuitionFee, MBoardTeacher mBoardTeacher) {
        this.id = id;
        this.className = className;
        this.classDatetime = classDatetime;
        this.tuitionFee = tuitionFee;
        this.mBoardTeacher = mBoardTeacher;
    }
}
