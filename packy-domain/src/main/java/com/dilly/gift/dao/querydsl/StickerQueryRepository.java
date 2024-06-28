package com.dilly.gift.dao.querydsl;

import static com.dilly.gift.domain.sticker.QSticker.sticker;

import com.dilly.gift.domain.sticker.Sticker;
import com.dilly.global.util.SliceUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StickerQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Slice<Sticker> searchBySlice(Long lastSequence, Pageable pageable) {
        List<Sticker> results = jpaQueryFactory.selectFrom(sticker)
            .where(gtSequence(lastSequence))
            .orderBy(sticker.sequence.asc())
            .limit(pageable.getPageSize() + 1L)
            .fetch();

        return SliceUtil.checkLastPage(pageable, results);
    }

    private BooleanExpression gtSequence(Long sequence) {
        if (sequence == null) {
            return null;
        }

        return sticker.sequence.gt(sequence);
    }
}
