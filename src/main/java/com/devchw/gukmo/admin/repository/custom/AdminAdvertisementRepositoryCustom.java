package com.devchw.gukmo.admin.repository.custom;

import com.devchw.gukmo.admin.dto.api.advertisement.DataTableAdvertisementFormDto;
import com.devchw.gukmo.entity.advertisement.Advertisement;

import java.util.List;

public interface AdminAdvertisementRepositoryCustom {
    List<Advertisement> findAllAdvertisementList(int start, int end, DataTableAdvertisementFormDto form);

    long findAllAdvertisementListTotal(DataTableAdvertisementFormDto form);
}
