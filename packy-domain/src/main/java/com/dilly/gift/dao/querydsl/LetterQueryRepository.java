package com.dilly.gift.dao.querydsl;

import static com.dilly.gift.domain.QGiftBox.giftBox;
import static com.dilly.gift.domain.QLetter.letter;
import static com.dilly.gift.domain.QReceiver.receiver;

import com.dilly.gift.domain.Letter;
import com.dilly.gift.domain.ReceiverStatus;
import com.dilly.member.domain.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LetterQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Slice<Letter> searchBySlice(Member member, LocalDateTime lastLetterDate, Pageable pageable) {
        List<Letter> results = jpaQueryFactory.select(letter)
            .from(receiver)
            .join(receiver.giftBox, giftBox)
            .join(giftBox.letter, letter)
            .where(
                ltLetterDate(lastLetterDate),
                receiver.member.eq(member),
                receiver.status.eq(ReceiverStatus.RECEIVED)
            )
            .orderBy(receiver.createdAt.desc())
            .limit(pageable.getPageSize() + 1L)
            .fetch();

        return checkLastPage(pageable, results);
    }

    private BooleanExpression ltLetterDate(LocalDateTime lastLetterDate) {
        if (lastLetterDate == null) {
            return null;
        }

        return receiver.createdAt.lt(lastLetterDate);
    }

    private Slice<Letter> checkLastPage(Pageable pageable, List<Letter> results) {
        boolean hasNext = false;

        if (results.size() > pageable.getPageSize()) {
            results.remove(results.size() - 1);
            hasNext = true;
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }
}
