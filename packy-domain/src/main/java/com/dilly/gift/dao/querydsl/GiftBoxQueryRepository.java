package com.dilly.gift.dao.querydsl;

import static com.dilly.gift.domain.QGiftBox.giftBox;
import static com.dilly.gift.domain.QReceiver.receiver;

import com.dilly.gift.domain.GiftBox;
import com.dilly.member.domain.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GiftBoxQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Slice<GiftBox> searchBySlice(Member member, LocalDateTime lastGiftBoxDate, String type,
        Pageable pageable) {
        List<GiftBox> results = new ArrayList<>();

        // TODO: switch문으로 리팩토링
        if (type.equals("sent")) {
            results = jpaQueryFactory.selectFrom(giftBox)
                .limit(pageable.getPageSize() + 1L)
                .where(
                    ltGiftBoxDate(lastGiftBoxDate),
                    giftBox.sender.eq(member))
                .orderBy(giftBox.createdAt.desc())
                .limit(pageable.getPageSize() + 1L)
                .fetch();
        } else if (type.equals("received")) {
            results = jpaQueryFactory.select(giftBox)
                .from(receiver)
                .join(receiver.giftBox, giftBox)
                .where(
                    ltGiftBoxDate(lastGiftBoxDate),
                    receiver.member.eq(member))
                .orderBy(receiver.createdAt.desc())
                .limit(pageable.getPageSize() + 1L)
                .fetch();
        } else if (type.equals("all")) {
            // TODO: type이 없다면 내가 보낸 선물박스랑 내가 받은 선물박스를 합치고 giftboxDate로 정렬
        }

        return checkLastPage(pageable, results);
    }

    private BooleanExpression ltGiftBoxDate(LocalDateTime giftBoxDate) {
        if (giftBoxDate == null) {
            return null;
        }

        return giftBox.createdAt.lt(giftBoxDate);
    }

    private Slice<GiftBox> checkLastPage(Pageable pageable, List<GiftBox> results) {
        boolean hasNext = false;

        if (results.size() > pageable.getPageSize()) {
            results.remove(results.size() - 1);
            hasNext = true;
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }
}
