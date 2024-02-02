package com.dilly.gift.adaptor;

import com.dilly.exception.ErrorCode;
import com.dilly.exception.entitynotfound.EntityNotFoundException;
import com.dilly.gift.Sticker;
import com.dilly.gift.dao.StickerRepository;
import com.dilly.gift.dao.querydsl.StickerQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StickerReader {

    private final StickerRepository stickerRepository;
    private final StickerQueryRepository stickerQueryRepository;

    public Slice<Sticker> searchBySlice(Long lastStickerId, Pageable pageable) {
        return stickerQueryRepository.searchBySlice(lastStickerId, pageable);
    }

    public Sticker findById(Long id) {
        return stickerRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(ErrorCode.STICKER_NOT_FOUND)
        );
    }
}
