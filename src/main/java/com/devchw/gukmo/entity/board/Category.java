package com.devchw.gukmo.entity.board;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent; //부모카테고리번호

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    private String name; //카테고리 이름
}
