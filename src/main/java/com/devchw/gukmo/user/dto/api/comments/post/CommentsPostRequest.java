package com.devchw.gukmo.user.dto.api.comments.post;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.comment.Comments;
import com.devchw.gukmo.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@Builder
public class CommentsPostRequest {

    @Nullable
    private Long parentId;
    private String content;
    private Long boardId;
    private Long memberId;

    public Comments toEntity(Board board, Member member, Comments parent) {
        return Comments.builder()
                .parent(parent)
                .member(member)
                .content(content)
                .board(board)
                .build();
    }
}
