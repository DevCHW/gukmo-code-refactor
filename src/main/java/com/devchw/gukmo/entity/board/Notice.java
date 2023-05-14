package com.devchw.gukmo.entity.board;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@DynamicInsert
public class Notice extends Board {
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NO'")
    private MustRead mustRead;    //필독 여부

    public enum MustRead {
        YES, NO
    }
}
