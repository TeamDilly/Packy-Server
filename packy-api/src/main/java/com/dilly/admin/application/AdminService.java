package com.dilly.admin.application;

import com.dilly.admin.adaptor.NoticeImageReader;
import com.dilly.admin.adaptor.NoticeReader;
import com.dilly.admin.adaptor.SettingReader;
import com.dilly.admin.domain.music.Music;
import com.dilly.admin.domain.notice.Notice;
import com.dilly.admin.domain.notice.NoticeImage;
import com.dilly.admin.domain.setting.Setting;
import com.dilly.admin.dto.response.BoxImgResponse;
import com.dilly.admin.dto.response.ImgResponse;
import com.dilly.admin.dto.response.MusicResponse;
import com.dilly.admin.dto.response.NoticeResponse;
import com.dilly.admin.dto.response.SettingResponse;
import com.dilly.gift.adaptor.BoxReader;
import com.dilly.gift.adaptor.EnvelopeReader;
import com.dilly.gift.adaptor.MusicReader;
import com.dilly.gift.adaptor.StickerReader;
import com.dilly.gift.domain.Box;
import com.dilly.gift.domain.letter.Envelope;
import com.dilly.gift.domain.sticker.Sticker;
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
    private final NoticeReader noticeReader;
    private final NoticeImageReader noticeImageReader;

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

    public List<NoticeResponse> getNotices() {
        List<Notice> notices = noticeReader.findAllByOrderBySequence();

        return notices.stream().map(NoticeResponse::from).toList();
    }

    public List<String> getNotice(Long noticeId) {
        Notice notice = noticeReader.findById(noticeId);
        List<NoticeImage> noticeImages = noticeImageReader.findAllByNoticeOrderBySequence(notice);

        return noticeImages.stream().map(NoticeImage::getImgUrl).toList();
    }
}
