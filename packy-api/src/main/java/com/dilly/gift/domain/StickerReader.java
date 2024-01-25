package com.dilly.gift.domain;

import com.dilly.admin.dto.response.ImgResponse;
import com.dilly.exception.ErrorCode;
import com.dilly.exception.entitynotfound.EntityNotFoundException;
import com.dilly.gift.Sticker;
import com.dilly.gift.dao.StickerRepository;
import com.dilly.gift.dao.querydsl.StickerQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StickerReader {

    private final StickerRepository stickerRepository;
    private final StickerQueryRepository stickerQueryRepository;

    public Slice<ImgResponse> findAll(Long lastStickerId, Pageable pageable) {
        Slice<Sticker> stickerSlice = stickerQueryRepository.searchBySlice(lastStickerId, pageable);

        List<ImgResponse> imgResponses = stickerSlice.getContent().stream()
            .map(sticker -> ImgResponse.builder()
                .id(sticker.getId())
                .imgUrl(sticker.getImgUrl())
                .sequence(sticker.getSequence())
                .build())
            .toList();

        return new SliceImpl<>(imgResponses, pageable, stickerSlice.hasNext());
    }

    public Sticker findById(Long id) {
        return stickerRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(ErrorCode.STICKER_NOT_FOUND)
        );
    }
}
