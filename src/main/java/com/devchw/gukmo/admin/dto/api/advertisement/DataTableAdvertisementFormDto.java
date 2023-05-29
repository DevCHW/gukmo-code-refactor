package com.devchw.gukmo.admin.dto.api.advertisement;

import com.devchw.gukmo.admin.dto.api.member.DataTableMemberFormDto;
import com.devchw.gukmo.entity.advertisement.Advertisement;
import com.devchw.gukmo.entity.advertisement.Advertisement.Type;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;

import static com.devchw.gukmo.entity.advertisement.Advertisement.*;
import static org.springframework.util.StringUtils.hasText;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataTableAdvertisementFormDto {
    private String id;
    private Type type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String sort;
    private String direction;

    public DataTableAdvertisementFormDto toDto(MultiValueMap<String, String> formData) {
        String id = formData.get("columns[0][search][value]").get(0);
        String strType = formData.get("columns[1][search][value]").get(0);
        String startDate = formData.get("columns[2][search][value]").get(0);
        String endDate = formData.get("columns[3][search][value]").get(0);
        String sort = formData.get("order[0][column]").get(0);
        String direction = formData.get("order[0][dir]").get(0);

        switch (sort) {
            case "0":
                sort = "id";
                break;
            case "1":
                sort = "type";
                break;
            case "2":
                sort = "startDate";
                break;
            case "3":
                sort = "endDate";
                break;
            default:
                sort = "id";
                break;
        }

        Type type = null;
        if("BOARD".equals(strType)) {
            type = Type.BOARD;
        } else if("MAIN".equals(strType)) {
            type = Type.MAIN;
        }

        if(hasText(startDate) && !hasText(endDate)) {
            return DataTableAdvertisementFormDto.builder()
                    .id(id)
                    .type(type)
                    .startDate(DateUtil.stringToLocalDateTimeConverter(startDate))
                    .sort(sort)
                    .direction(direction)
                    .build();
        } else if(!hasText(startDate) && hasText(endDate)) {
            return DataTableAdvertisementFormDto.builder()
                    .id(id)
                    .type(type)
                    .endDate(DateUtil.stringToLocalDateTimeConverter(endDate).plusDays(1))
                    .sort(sort)
                    .direction(direction)
                    .build();
        } else if(!hasText(startDate) && !hasText(endDate)) {
            return DataTableAdvertisementFormDto.builder()
                    .id(id)
                    .type(type)
                    .sort(sort)
                    .direction(direction)
                    .build();
        } else {
            return DataTableAdvertisementFormDto.builder()
                    .id(id)
                    .type(type)
                    .startDate(DateUtil.stringToLocalDateTimeConverter(startDate))
                    .endDate(DateUtil.stringToLocalDateTimeConverter(endDate).plusDays(1))
                    .sort(sort)
                    .direction(direction)
                    .build();
        }
    }
}
