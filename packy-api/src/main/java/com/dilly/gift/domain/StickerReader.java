package com.dilly.gift.domain;

import com.dilly.admin.dto.response.ImgResponse;
import com.dilly.gift.Sticker;
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
}
