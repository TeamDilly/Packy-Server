package com.dilly.admin.application;

import com.dilly.admin.adaptor.AdminGiftBoxReader;
import com.dilly.admin.adaptor.SettingReader;
import com.dilly.admin.domain.giftbox.ScreenType;
import com.dilly.admin.domain.music.Music;
import com.dilly.admin.domain.setting.Setting;
import com.dilly.admin.dto.response.BoxImgResponse;
import com.dilly.admin.dto.response.ImgResponse;
import com.dilly.admin.dto.response.MusicResponse;
import com.dilly.admin.dto.response.SettingResponse;
import com.dilly.exception.ErrorCode;
import com.dilly.exception.UnsupportedException;
import com.dilly.gift.adaptor.BoxReader;
import com.dilly.gift.adaptor.EnvelopeReader;
import com.dilly.gift.adaptor.GiftBoxStickerReader;
import com.dilly.gift.adaptor.MusicReader;
import com.dilly.gift.adaptor.PhotoReader;
import com.dilly.gift.adaptor.StickerReader;
import com.dilly.gift.domain.Box;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.letter.Envelope;
import com.dilly.gift.domain.sticker.Sticker;
import com.dilly.gift.dto.response.BoxResponse;
import com.dilly.gift.dto.response.EnvelopeListResponse;
import com.dilly.gift.dto.response.EnvelopeResponse;
import com.dilly.gift.dto.response.GiftBoxResponse;
import com.dilly.gift.dto.response.GiftResponseDto.GiftResponse;
import com.dilly.gift.dto.response.PhotoResponseDto.PhotoResponse;
import com.dilly.gift.dto.response.StickerResponse;
import com.dilly.member.adaptor.ProfileImageReader;
import com.dilly.member.domain.ProfileImage;
import java.util.Comparator;
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
    private final AdminGiftBoxReader adminGiftBoxReader;
    private final PhotoReader photoReader;
    private final GiftBoxStickerReader giftBoxStickerReader;

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
        Long lastSequence = 0L;
        if (lastStickerId != null) {
            lastSequence = stickerReader.findById(lastStickerId).getSequence();
        }
        Slice<Sticker> stickerSlice = stickerReader.searchBySlice(lastSequence, pageable);
        List<ImgResponse> imgResponses = stickerSlice.getContent().stream()
            .map(ImgResponse::from)
            .toList();

        return new SliceImpl<>(imgResponses, pageable, stickerSlice.hasNext());
    }

    public List<SettingResponse> getSettingUrls() {
        List<Setting> settingUrls = settingReader.findAll();

        return settingUrls.stream().map(SettingResponse::from).toList();
    }

    public GiftBoxResponse getPackyGiftBox(String screenType) {
        ScreenType type;
        try {
            type = ScreenType.valueOf(screenType);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedException(ErrorCode.UNSUPPORTED_SCREEN_TYPE);
        }

        GiftBox giftBox = adminGiftBoxReader.findByScreenType(type).getGiftBox();

        BoxResponse boxResponse = BoxResponse.from(giftBox.getBox());
        EnvelopeResponse envelopeResponse = EnvelopeResponse.from(
            giftBox.getLetter().getEnvelope());
        List<PhotoResponse> photos = photoReader.findAllByGiftBox(giftBox).stream()
            .map(PhotoResponse::from)
            .sorted(Comparator.comparingInt(PhotoResponse::sequence))
            .toList();
        List<StickerResponse> stickers = giftBoxStickerReader.findAllByGiftBox(giftBox).stream()
            .map(StickerResponse::from)
            .sorted(Comparator.comparingInt(StickerResponse::location))
            .toList();

        GiftResponse giftResponse = null;
        if (giftBox.getGift() != null) {
            giftResponse = GiftResponse.from(giftBox.getGift());
        }

        return GiftBoxResponse.of(giftBox, boxResponse, envelopeResponse, photos, stickers,
            giftResponse);
    }
}
