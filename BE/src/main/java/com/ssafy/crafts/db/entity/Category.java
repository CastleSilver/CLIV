package com.ssafy.crafts.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "CATEGORY")
@NoArgsConstructor
@DynamicInsert
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;

    @Column(nullable = false, length = 20)
    private String content;

    @OneToMany(mappedBy = "category")
    private List<ClassInfo> classes = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<MBoard> mBoard = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "TEACHER_CATEGORIES",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "auth_id"))
    private List<Member> teachers = new ArrayList<Member>();

    @Builder
    public Category(int id, String content, List<ClassInfo> classes, List<Member> teachers) {
        this.id = id;
        this.content = content;
        this.classes = classes;
        this.teachers = teachers;
    }

    public void addClass(ClassInfo classInfo) {
        this.classes.add(classInfo);
        if (classInfo.getCategory() != this) {
            classInfo.setCategory(this);
        }
    }
}
