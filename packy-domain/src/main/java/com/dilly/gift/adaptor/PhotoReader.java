package com.dilly.gift.adaptor;

import com.dilly.exception.ErrorCode;
import com.dilly.exception.entitynotfound.EntityNotFoundException;
import com.dilly.gift.dao.PhotoRepository;
import com.dilly.gift.dao.querydsl.PhotoQueryRepository;
import com.dilly.gift.domain.Photo;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.member.domain.Member;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhotoReader {

    private final PhotoRepository photoRepository;
    private final PhotoQueryRepository photoQueryRepository;

    public List<Photo> findAllByGiftBox(GiftBox giftBox) {
        return photoRepository.findAllByGiftBox(giftBox);
    }

    public Photo findById(Long photoId) {
        return photoRepository.findById(photoId).orElseThrow(
            () -> new EntityNotFoundException(ErrorCode.PHOTO_NOT_FOUND)
        );
    }

    public Slice<Photo> searchBySlice(Member member, LocalDateTime lastPhotoDate,
        Pageable pageable) {
        return photoQueryRepository.searchBySlice(member, lastPhotoDate, pageable);
    }

    public Long count() {
        return photoRepository.count();
    }
}
