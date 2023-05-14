package com.devchw.gukmo.entity.report;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@DynamicInsert  //insert 시 명시해주지 않아도 기본값 적용되도록 하기.
public class CommentsReport {

    @Id @GeneratedValue
    @Column(name = "comments_report_id")
    private Long id;

}