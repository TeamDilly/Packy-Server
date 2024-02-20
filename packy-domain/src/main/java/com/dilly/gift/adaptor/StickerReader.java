package com.dilly.gift.adaptor;

import com.dilly.exception.ErrorCode;
import com.dilly.exception.entitynotfound.EntityNotFoundException;
import com.dilly.gift.dao.StickerRepository;
import com.dilly.gift.dao.querydsl.StickerQueryRepository;
import com.dilly.gift.domain.sticker.Sticker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StickerReader {

    private final StickerRepository stickerRepository;
    private final StickerQueryRepository stickerQueryRepository;

    public Slice<Sticker> searchBySlice(Long lastSequence, Pageable pageable) {
        return stickerQueryRepository.searchBySlice(lastSequence, pageable);
    }

    public Sticker findById(Long id) {
        return stickerRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(ErrorCode.STICKER_NOT_FOUND)
        );
    }
}
