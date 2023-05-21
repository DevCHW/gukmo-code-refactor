package com.devchw.gukmo.entity.advertisement;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@DynamicInsert  //insert 시 명시해주지 않아도 기본값 적용되도록 하기.
public class Advertisement {

    @Id @GeneratedValue
    @Column(name = "advertisement_id")
    private Long id;

    @Enumerated(STRING)
    private Type type;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String fileName;

    private String url;

    public enum Type {
        MAIN, BOARD //메인페이지, 게시글 페이지
    }


}
