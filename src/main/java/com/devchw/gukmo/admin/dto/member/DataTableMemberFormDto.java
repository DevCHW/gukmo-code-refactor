package com.devchw.gukmo.admin.dto.member;

import com.devchw.gukmo.entity.member.Member.Status;
import com.devchw.gukmo.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;

import static org.springframework.util.StringUtils.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataTableMemberFormDto {

    private String nickname;
    private String userId;
    private String email;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Status status; //REST, ACTIVE, WAIT
    private String sort;
    private String direction;

    public DataTableMemberFormDto toDto(MultiValueMap<String, String> formData) {
        String nickname = formData.get("columns[0][search][value]").get(0);
        String userId = formData.get("columns[1][search][value]").get(0);
        String email = formData.get("columns[2][search][value]").get(0);
        String joinDate = formData.get("columns[3][search][value]").get(0);
        String strStatus = formData.get("columns[4][search][value]").get(0);
        String sort = formData.get("order[0][column]").get(0);
        String direction = formData.get("order[0][dir]").get(0);
        switch (sort) {
            case "0":
                sort = "nickname";
                break;
            case "1":
                sort = "userId";
                break;
            case "2":
                sort = "email";
                break;
            case "3":
                sort = "joinDate";
                break;
            case "4":
                sort = "status";
                break;
            default:
                sort = "joinDate";
                break;
        }

        String startDate = null;
        String endDate = null;
        if(joinDate != null && !joinDate.trim().isEmpty()) {
            String[] startAndEnd = joinDate.split(",");
            startDate = startAndEnd[0];
            endDate = startAndEnd[1];
        }

        Status status = null;
        if("SUSPENDED".equals(strStatus)) {
            status = Status.SUSPENDED;
        } else if("ACTIVE".equals(strStatus)) {
            status = Status.ACTIVE;
        } else if("WAIT".equals(strStatus)) {
            status = Status.WAIT;
        }

        if(hasText(joinDate)) {
            if(hasText(startDate) && !hasText(endDate)) {
                return DataTableMemberFormDto.builder()
                        .nickname(nickname)
                        .userId(userId)
                        .email(email)
                        .startDate(DateUtil.stringToLocalDateTimeConverter(startDate))
                        .status(status)
                        .sort(sort)
                        .direction(direction)
                        .build();
            } else if(!hasText(startDate) && hasText(endDate)) {
                return DataTableMemberFormDto.builder()
                        .nickname(nickname)
                        .userId(userId)
                        .email(email)
                        .endDate(DateUtil.stringToLocalDateTimeConverter(endDate).plusDays(1))
                        .status(status)
                        .sort(sort)
                        .direction(direction)
                        .build();
            } else if(!hasText(startDate) && !hasText(endDate)) {
                return DataTableMemberFormDto.builder()
                        .nickname(nickname)
                        .userId(userId)
                        .email(email)
                        .status(status)
                        .sort(sort)
                        .direction(direction)
                        .build();
            } else {
                return DataTableMemberFormDto.builder()
                        .nickname(nickname)
                        .userId(userId)
                        .email(email)
                        .startDate(DateUtil.stringToLocalDateTimeConverter(startDate))
                        .endDate(DateUtil.stringToLocalDateTimeConverter(endDate).plusDays(1))
                        .status(status)
                        .sort(sort)
                        .direction(direction)
                        .build();
            }
        } else {
            return DataTableMemberFormDto.builder()
                    .nickname(nickname)
                    .userId(userId)
                    .email(email)
                    .status(status)
                    .sort(sort)
                    .direction(direction)
                    .build();
        }
    }
}
