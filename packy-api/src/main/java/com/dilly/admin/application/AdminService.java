package com.dilly.admin.application;

import com.dilly.admin.dto.response.BoxImgResponse;
import com.dilly.admin.dto.response.ImgResponse;
import com.dilly.admin.dto.response.MusicResponse;
import com.dilly.gift.domain.BoxReader;
import com.dilly.gift.domain.EnvelopeReader;
import com.dilly.gift.domain.MusicReader;
import com.dilly.gift.domain.StickerReader;
import com.dilly.gift.dto.response.EnvelopeListResponse;
import com.dilly.member.domain.ProfileImageReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminService {

    private final ProfileImageReader profileImageReader;
    private final BoxReader boxReader;
    private final EnvelopeReader envelopeReader;
    private final MusicReader musicReader;
    private final StickerReader stickerReader;

    public List<ImgResponse> getProfiles() {
        return profileImageReader.findAll();
    }

    public List<BoxImgResponse> getBoxes() {
        return boxReader.findAll();
    }

    public List<EnvelopeListResponse> getEnvelopes() {
        return envelopeReader.findAll();
    }

    public List<MusicResponse> getMusics() {
        return musicReader.findAll();
    }

    public Slice<ImgResponse> getStickers(Long lastStickerId, Pageable pageable) {
        return stickerReader.findAll(lastStickerId, pageable);
    }
}
