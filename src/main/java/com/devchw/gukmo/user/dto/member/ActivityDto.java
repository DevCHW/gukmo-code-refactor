package com.devchw.gukmo.user.dto.member;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.member.Activity;
import com.devchw.gukmo.entity.member.Activity.Division;
import com.devchw.gukmo.utils.DateUtil;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActivityBoardDto {
        private Long id;
        private String subject;
        private String firstCategory;
        private String secondCategory;
    }

    @Data
    public static class WriterNicknameDto {
        private Long id;
        private String writerNickname;

        @QueryProjection
        public WriterNicknameDto(Long id, String writerNickname) {
            this.id = id;
            this.writerNickname = writerNickname;
        }
    }

    private Long id;
    private Division division;
    private String activityDate;
    private ActivityBoardDto activityBoardDto;

    public ActivityDto toDto(Activity activity) {
        Board board = activity.getBoard();
        ActivityBoardDto activityBoardDto = ActivityBoardDto.builder()
                .id(board.getId())
                .subject(board.getSubject())
                .firstCategory(board.getFirstCategory())
                .secondCategory(board.getSecondCategory())
                .build();

        return ActivityDto.builder()
                .id(activity.getId())
                .division(activity.getDivision())
                .activityDate(DateUtil.calculateTime(activity.getActivityDate()))
                .activityBoardDto(activityBoardDto)
                .build();
    }
}
