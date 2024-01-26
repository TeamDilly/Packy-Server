package com.dilly.gift.dao.querydsl;

import static com.dilly.gift.QSticker.sticker;

import com.dilly.gift.Sticker;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StickerQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Slice<Sticker> searchBySlice(Long lastStickerId, Pageable pageable) {
        List<Sticker> results = jpaQueryFactory.selectFrom(sticker)
            .where(gtStickerId(lastStickerId))
            .orderBy(sticker.id.asc())
            .limit(pageable.getPageSize() + 1)
            .fetch();

        return checkLastPage(pageable, results);
    }

    // No-offset
    private BooleanExpression gtStickerId(Long sticerId) {
        if (sticerId == null) {
            return null;
        }

        return sticker.id.gt(sticerId);
    }

    // 무한 스크롤
    private Slice<Sticker> checkLastPage(Pageable pageable, List<Sticker> results) {
        boolean hasNext = false;

        if (results.size() > pageable.getPageSize()) {
            results.remove(results.size() - 1);
            hasNext = true;
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }
}