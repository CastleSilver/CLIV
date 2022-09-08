package com.ssafy.crafts.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter
@Table(name = "Auth")
@NoArgsConstructor
@DynamicInsert
@Entity
public class Auth {
    @Id
    @Column(name = "auth_id", length = 13)
    private String authId;

    @Column(length = 40, nullable = false)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "regdate",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp regDate;

    @Builder
    public Auth(String authId, String email, String phoneNumber, Timestamp regDate) {
        this.authId = authId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.regDate = regDate;
    }
}
