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
@Table(name = "HASHTAG")
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private int id;

    @Column(nullable = false, length = 10)
    private String content;

    @ManyToMany(mappedBy = "tagging")
    private List<ClassInfo> classes = new ArrayList<>();

    @Builder
    public Hashtag(int id, String content, List<ClassInfo> classes) {
        this.id = id;
        this.content = content;
        this.classes = classes;
    }
}