package com.dilly.admin.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.dilly.BoxFixture;
import com.dilly.admin.dto.response.BoxImgResponse;
import com.dilly.admin.dto.response.ImgResponse;
import com.dilly.admin.dto.response.MusicResponse;
import com.dilly.admin.dto.response.NoticeResponse;
import com.dilly.admin.dto.response.SettingResponse;
import com.dilly.gift.domain.Box;
import com.dilly.gift.dto.response.EnvelopeListResponse;
import com.dilly.global.IntegrationTestSupport;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AdminServiceTest extends IntegrationTestSupport {

    @DisplayName("프로필 이미지를 조회한다.")
    @Test
    void getProfiles() {
        // given
        List<ImgResponse> profiles = profileImageReader.findAll()
            .stream().map(ImgResponse::from).toList();

        // when
        List<ImgResponse> response = adminService.getProfiles();

        // then
        assertThat(response).isEqualTo(profiles);
    }

    @DisplayName("박스 디자인을 조회한다.")
    @Test
    void getBoxes() {
        // given
        Box box1 = BoxFixture.createBoxFixture(1L, 1L);
        Box box2 = BoxFixture.createBoxFixture(2L, 2L);
        Box box3 = BoxFixture.createBoxFixture(3L, 3L);
        List<Box> boxes = List.of(box1, box2, box3);
        boxWriter.saveAll(boxes);

        List<BoxImgResponse> boxImgResponses = boxReader.findAll()
            .stream().map(BoxImgResponse::from).toList();

        // when
        List<BoxImgResponse> response = adminService.getBoxes();

        // then
        assertThat(response).isEqualTo(boxImgResponses);
    }

    @DisplayName("편지 봉투 디자인을 조회한다.")
    @Test
    void getEnvelopes() {
        // given
        List<EnvelopeListResponse> envelopes = envelopeReader.findAll()
            .stream().map(EnvelopeListResponse::from).toList();

        // when
        List<EnvelopeListResponse> response = adminService.getEnvelopes();

        // then
        assertThat(response).isEqualTo(envelopes);
    }

    @DisplayName("패키 추천 음악을 조회한다.")
    @Test
    void getMusics() {
        // given
        List<MusicResponse> musics = musicReader.findAll()
            .stream().map(MusicResponse::from)
            .toList();

        // when
        List<MusicResponse> response = adminService.getMusics();

        // then
        assertThat(response).isEqualTo(musics);
    }

    @DisplayName("설정 링크를 조회한다.")
    @Test
    void getSettingUrls() {
        // given
        List<SettingResponse> settingUrls = settingReader.findAll()
            .stream().map(SettingResponse::from)
            .toList();

        // when
        List<SettingResponse> response = adminService.getSettingUrls();

        // then
        assertThat(response).isEqualTo(settingUrls);
    }

    @DisplayName("공지사항 목록을 조회한다.")
    @Test
    void getNotices() {
        // given
        List<NoticeResponse> notices = noticeReader.findAllByOrderBySequence()
            .stream().map(NoticeResponse::from)
            .toList();

        // when
        List<NoticeResponse> response = adminService.getNotices();

        // then
        assertThat(response).isEqualTo(notices);
    }
}
