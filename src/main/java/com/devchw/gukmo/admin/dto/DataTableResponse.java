package com.devchw.gukmo.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataTableResponse {
    private int draw;
    private int recordsTotal;
    private int recordsFiltered;

    private List<?> data;

    public List<?> getData(){
        if(CollectionUtils.isEmpty(data)){
            data = new ArrayList<>();
        }
        return data;
    }
}
