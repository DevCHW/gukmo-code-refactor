package com.devchw.gukmo.admin.repository.custom;

import com.devchw.gukmo.admin.dto.api.advertisement.DataTableAdvertisementFormDto;
import com.devchw.gukmo.entity.advertisement.Advertisement;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.devchw.gukmo.entity.advertisement.Advertisement.*;
import static com.devchw.gukmo.entity.advertisement.QAdvertisement.advertisement;

@Slf4j
@RequiredArgsConstructor
public class AdminAdvertisementRepositoryImpl implements AdminAdvertisementRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Advertisement> findAllAdvertisementList(int start, int end, DataTableAdvertisementFormDto form) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(form);
        return queryFactory
                .selectFrom(advertisement)
                .where(
                        idEq(form.getId()),
                        typeEq(form.getType()),
                        startDateGoe(form.getStartDate()),
                        endDateLt(form.getEndDate())
                )
                .orderBy(orderSpecifiers)
                .offset(start)
                .limit(end)
                .fetch();
    }

    /** total 조회 */
    @Override
    public long findAllAdvertisementListTotal(DataTableAdvertisementFormDto form) {
        return queryFactory
                .selectFrom(advertisement)
                .where(
                        idEq(form.getId()),
                        typeEq(form.getType()),
                        startDateGoe(form.getStartDate()),
                        endDateLt(form.getEndDate())
                )
                .fetchCount();
    }

    /**
     * BooleanExpressions
     */
    private BooleanExpression idEq(String strId) {
        try {
            Long id = Long.parseLong(strId);
            return id != null?advertisement.id.eq(id):null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    private BooleanExpression typeEq(Type type) {
        return type != null?advertisement.type.eq(type):null;
    }

    private BooleanExpression startDateGoe(LocalDateTime startDate) {
        return startDate != null?advertisement.startDate.goe(startDate):null;
    }

    private BooleanExpression endDateLt(LocalDateTime endDate) {
        return endDate != null?advertisement.endDate.lt(endDate):null;
    }


    /** 정렬 */
    private OrderSpecifier[] createOrderSpecifier(DataTableAdvertisementFormDto form) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if(form.getSort().equals("id")) {
            if(form.getDirection().equals("desc")) {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, advertisement.id));
            } else {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, advertisement.id));
            }
        } else if(form.getSort().equals("type")) {
            if(form.getDirection().equals("desc")) {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, advertisement.type));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, advertisement.id));
            } else {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, advertisement.type));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, advertisement.id));
            }

        } else if(form.getSort().equals("startDate")) {
            if(form.getDirection().equals("desc")) {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, advertisement.startDate));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, advertisement.id));
            } else {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, advertisement.startDate));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, advertisement.id));
            }

        } else if(form.getSort().equals("endDate")) {
            if(form.getDirection().equals("desc")) {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, advertisement.endDate));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, advertisement.id));
            } else {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, advertisement.endDate));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, advertisement.id));
            }
        } else {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, advertisement.id));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
