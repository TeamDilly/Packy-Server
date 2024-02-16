package com.dilly.gift.application;

import com.dilly.gift.adaptor.GiftBoxReader;
import com.dilly.gift.adaptor.PhotoReader;
import com.dilly.gift.adaptor.ReceiverReader;
import com.dilly.gift.domain.GiftBox;
import com.dilly.gift.domain.Photo;
import com.dilly.gift.domain.Receiver;
import com.dilly.gift.dto.response.PhotoResponseDto.PhotoWithoutSequenceResponse;
import com.dilly.global.utils.SecurityUtil;
import com.dilly.member.adaptor.MemberReader;
import com.dilly.member.domain.Member;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GiftService {

    private final MemberReader memberReader;
    private final PhotoReader photoReader;
    private final GiftBoxReader giftBoxReader;
    private final ReceiverReader receiverReader;

    public Slice<PhotoWithoutSequenceResponse> getPhotos(Long lastPhotoId, Pageable pageable) {
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberReader.findById(memberId);

        LocalDateTime lastPhotoDate = LocalDateTime.now();
        if (lastPhotoId != null) {
            Photo lastPhoto = photoReader.findById(lastPhotoId);
            GiftBox lastGiftBox = giftBoxReader.findById(lastPhoto.getGiftBox().getId());
            Receiver lastReceiver = receiverReader.findByMemberAndGiftBox(member, lastGiftBox);

            lastPhotoDate = lastReceiver.getCreatedAt();
        }
        Slice<Photo> photoSlice = photoReader.searchBySlice(member, lastPhotoDate, pageable);

        List<PhotoWithoutSequenceResponse> photoResponses = photoSlice.stream()
            .map(PhotoWithoutSequenceResponse::from)
            .toList();

        return new SliceImpl<>(photoResponses, pageable, photoSlice.hasNext());
    }
}
