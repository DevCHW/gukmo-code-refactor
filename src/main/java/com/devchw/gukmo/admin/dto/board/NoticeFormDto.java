package com.devchw.gukmo.admin.dto.board;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.board.Notice;
import com.devchw.gukmo.entity.board.Notice.MustRead;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.user.dto.board.CommunityFormDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeFormDto {
    private Long id;
    private Long memberId;
    private String subject;
    private String content;
    private String hashtags;
    private String mustRead;

    /**
     * Dto -> Entity
     */
    public Notice toEntity(Member member) {
        if(mustRead.equals("YES")) {
            return Notice.builder()
                    .subject(subject)
                    .content(content)
                    .firstCategory("공지사항")
                    .secondCategory("공지사항")
                    .member(member)
                    .mustRead(MustRead.YES)
                    .build();
        } else {
            return Notice.builder()
                    .subject(subject)
                    .content(content)
                    .firstCategory("공지사항")
                    .secondCategory("공지사항")
                    .member(member)
                    .mustRead(MustRead.NO)
                    .build();
        }
    }

    /**
     * Entity -> Dto
     */
    public NoticeFormDto toDto(Notice notice) {
        String mustRead = notice.getMustRead().equals(MustRead.YES)?"YES":"NO";
        return NoticeFormDto.builder()
                .id(notice.getId())
                .subject(notice.getSubject())
                .content(notice.getContent())
                .mustRead(mustRead)
                .build();
    }
}
