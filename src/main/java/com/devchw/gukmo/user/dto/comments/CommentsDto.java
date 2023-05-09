package com.devchw.gukmo.user.dto.comments;

import com.devchw.gukmo.entity.comment.Comments;
import com.devchw.gukmo.user.dto.member.WriterDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {

    private Long id;    //댓글번호
    private String content; //댓글내용
    private Comments.Blind blind;   //블라인드 여부
    private boolean likeExist;      //좋아요 여부
    private LocalDateTime writeDate;    //작성일자
    private List<CommentsDto> children = new ArrayList<>();   //자식댓글들
    private WriterDto writer;   //작성자정보
    private Long likeCount; //좋아요 개수

    /**
     * 조회한 댓글목록들 계층구조로 변환(로그인중이 아닐 시)
     */
    public static List<CommentsDto> convertHierarchy(List<Comments> comments) {
        Map<Long, CommentsDto> map = new HashMap<>();
        List<CommentsDto> roots = new ArrayList<>();

        for (Comments comment : comments) { // 1
            CommentsDto dto = toCommentsDto(comment, false);
            map.put(comment.getId(), dto); // 2

            if (comment.getParent() != null) { //부모가 있다면
                Long parentId = comment.getParent().getId();
                CommentsDto parentDto = map.get(parentId);
                parentDto.getChildren().add(dto);
            } else {    //부모가 없다면
                roots.add(dto); // 4
            }
        }
        return roots;
    }


    /**
     * 조회한 댓글목록들 계층구조로 변환(로그인중일 시 좋아요 여부까지 확인)
     */
    public static List<CommentsDto> convertHierarchy(List<Comments> comments, List<Long> commentsLikeList) {
        Map<Long, CommentsDto> map = new HashMap<>();
        List<CommentsDto> roots = new ArrayList<>();

        for (Comments comment : comments) { // 1
            CommentsDto dto = null;
            if(commentsLikeList.contains(comment.getId())) {    //좋아요 누른 댓글
                dto = toCommentsDto(comment, true);
            } else {    //좋아요 누른 댓글
                dto = toCommentsDto(comment, false);
            }

            map.put(comment.getId(), dto); // 2

            if (comment.getParent() != null) { //부모가 있다면
                Long parentId = comment.getParent().getId();
                CommentsDto parentDto = map.get(parentId);
                parentDto.getChildren().add(dto);
            } else {    //부모가 없다면
                roots.add(dto); // 4
            }
        }
        return roots;
    }

    /**
     * Entity -> Dto
     */
    public static CommentsDto toCommentsDto(Comments comments, boolean likeExist) {
        return CommentsDto.builder()
                .id(comments.getId())
                .content(comments.getContent())
                .blind(comments.getBlind())
                .likeExist(likeExist)
                .writeDate(comments.getWriteDate())
                .writer(WriterDto.toWriterDto(comments.getMember()))
                .children(new ArrayList<>())
                .likeCount(comments.getLikeCount())
                .build();
    }
}
