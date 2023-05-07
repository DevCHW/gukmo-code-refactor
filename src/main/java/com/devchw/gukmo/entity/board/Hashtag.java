package com.devchw.gukmo.entity.board;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Hashtag {
    @Id @GeneratedValue
    @Column(name = "hashtag_id")
    private Long id;

    private String tagName;

//    @OneToMany(mappedBy = "hashtag")
//    private List<BoardHashtag> boardHashtag;
}
