package com.devchw.gukmo.entity.comment;

import com.devchw.gukmo.entity.comment.Comments;
import com.devchw.gukmo.entity.member.Member;
import lombok.*;
import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CommentsLike {

    @Id
    @GeneratedValue
    @Column(name = "comments_like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comment_id")
    private Comments comments ;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
