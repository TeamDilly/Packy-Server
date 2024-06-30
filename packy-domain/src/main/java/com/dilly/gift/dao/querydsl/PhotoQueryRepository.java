package com.dilly.gift.dao.querydsl;

import static com.dilly.gift.domain.QPhoto.photo;
import static com.dilly.gift.domain.giftbox.QGiftBox.giftBox;
import static com.dilly.gift.domain.receiver.QReceiver.receiver;

import com.dilly.gift.domain.Photo;
import com.dilly.gift.domain.receiver.ReceiverStatus;
import com.dilly.global.util.SliceUtil;
import com.dilly.member.domain.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PhotoQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Slice<Photo> searchBySlice(Member member, LocalDateTime lastPhotoDate,
        Pageable pageable) {
        List<Photo> results = jpaQueryFactory.select(photo)
            .from(receiver)
            .join(receiver.giftBox, giftBox)
            .join(receiver.giftBox.photos, photo)
            .where(
                ltPhotoDate(lastPhotoDate),
                receiver.member.eq(member),
                receiver.status.eq(ReceiverStatus.RECEIVED)
            )
            .orderBy(receiver.createdAt.desc())
            .limit(pageable.getPageSize() + 1L)
            .fetch();

        return SliceUtil.checkLastPage(pageable, results);
    }

    private BooleanExpression ltPhotoDate(LocalDateTime lastPhotoDate) {
        if (lastPhotoDate == null) {
            return null;
        }

        return receiver.createdAt.lt(lastPhotoDate);
    }
}
