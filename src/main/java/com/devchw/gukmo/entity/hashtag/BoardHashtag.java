package com.devchw.gukmo.entity.hashtag;

import com.devchw.gukmo.entity.board.Board;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardHashtag {

    @Id @GeneratedValue
    @Column(name = "board_hashtag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;
}
