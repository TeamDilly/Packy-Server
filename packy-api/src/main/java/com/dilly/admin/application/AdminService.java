package com.dilly.admin.application;

import com.dilly.admin.adaptor.SettingReader;
import com.dilly.admin.domain.Setting;
import com.dilly.admin.dto.response.BoxImgResponse;
import com.dilly.admin.dto.response.ImgResponse;
import com.dilly.admin.dto.response.MusicResponse;
import com.dilly.admin.dto.response.SettingResponse;
import com.dilly.gift.adaptor.BoxReader;
import com.dilly.gift.adaptor.EnvelopeReader;
import com.dilly.gift.adaptor.MusicReader;
import com.dilly.gift.adaptor.StickerReader;
import com.dilly.gift.domain.Box;
import com.dilly.gift.domain.Envelope;
import com.dilly.gift.domain.Music;
import com.dilly.gift.domain.Sticker;
import com.dilly.gift.dto.response.EnvelopeListResponse;
import com.dilly.member.adaptor.ProfileImageReader;
import com.dilly.member.domain.ProfileImage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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
    private final SettingReader settingReader;

    public List<ImgResponse> getProfiles() {
        List<ProfileImage> profileImages = profileImageReader.findAllByOrderBySequenceAsc();

        return profileImages.stream().map(ImgResponse::from).toList();
    }

    public List<BoxImgResponse> getBoxes() {
        List<Box> boxes = boxReader.findAllByOrderBySequenceAsc();

        return boxes.stream().map(BoxImgResponse::from).toList();
    }

    public List<EnvelopeListResponse> getEnvelopes() {
        List<Envelope> envelopes = envelopeReader.findAllByOrderBySequenceAsc();

        return envelopes.stream().map(EnvelopeListResponse::from).toList();
    }

    public List<MusicResponse> getMusics() {
        List<Music> musics = musicReader.findAllByOrderBySequenceAsc();

        return musics.stream().map(MusicResponse::from).toList();
    }

    public Slice<ImgResponse> getStickers(Long lastStickerId, Pageable pageable) {
        Slice<Sticker> stickerSlice = stickerReader.searchBySlice(lastStickerId, pageable);
        List<ImgResponse> imgResponses = stickerSlice.getContent().stream()
            .map(ImgResponse::from)
            .toList();

        return new SliceImpl<>(imgResponses, pageable, stickerSlice.hasNext());
    }

    public List<SettingResponse> getSettingUrls() {
        List<Setting> settingUrls = settingReader.findAll();

        return settingUrls.stream().map(SettingResponse::from).toList();
    }
}
