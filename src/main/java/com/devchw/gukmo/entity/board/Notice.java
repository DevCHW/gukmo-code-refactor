package com.devchw.gukmo.entity.board;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Notice extends Board {
    @Enumerated(EnumType.STRING)
    private MustRead mustRead;    //필독 여부

    public enum MustRead {
        YES, NO
    }
}
