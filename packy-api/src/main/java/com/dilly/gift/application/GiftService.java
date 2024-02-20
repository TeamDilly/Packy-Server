package com.dilly.gift.application;

import com.dilly.gift.adaptor.GiftBoxReader;
import com.dilly.gift.adaptor.LetterReader;
import com.dilly.gift.adaptor.PhotoReader;
import com.dilly.gift.adaptor.ReceiverReader;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.letter.Letter;
import com.dilly.gift.domain.Photo;
import com.dilly.gift.domain.receiver.Receiver;
import com.dilly.gift.dto.response.GiftResponseDto.ItemResponse;
import com.dilly.gift.dto.response.LetterResponse;
import com.dilly.gift.dto.response.MusicResponse;
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
    private final GiftBoxReader giftBoxReader;
    private final PhotoReader photoReader;
    private final LetterReader letterReader;
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

    public Slice<LetterResponse> getLetters(Long lastLetterId, Pageable pageable) {
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberReader.findById(memberId);

        LocalDateTime lastLetterDate = LocalDateTime.now();
        if (lastLetterId != null) {
            Letter lastLetter = letterReader.findById(lastLetterId);
            GiftBox giftBox = giftBoxReader.findByLetter(lastLetter);
            Receiver lastReceiver = receiverReader.findByMemberAndGiftBox(member, giftBox);

            lastLetterDate = lastReceiver.getCreatedAt();
        }
        Slice<Letter> letterSlice = letterReader.searchBySlice(member, lastLetterDate, pageable);

        List<LetterResponse> letterResponses = letterSlice.stream()
            .map(LetterResponse::from)
            .toList();

        return new SliceImpl<>(letterResponses, pageable, letterSlice.hasNext());
    }

    public Slice<MusicResponse> getMusics(Long lastGiftBoxId, Pageable pageable) {
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberReader.findById(memberId);

        LocalDateTime lastGiftBoxDate = LocalDateTime.now();
        if (lastGiftBoxId != null) {
            GiftBox lastGiftBox = giftBoxReader.findById(lastGiftBoxId);
            Receiver lastReceiver = receiverReader.findByMemberAndGiftBox(member, lastGiftBox);

            lastGiftBoxDate = lastReceiver.getCreatedAt();
        }
        Slice<GiftBox> giftBoxSlice = giftBoxReader.searchReceivedGiftBoxesBySlice(member,
            lastGiftBoxDate, pageable);

        List<MusicResponse> musicResponses = giftBoxSlice.stream()
            .map(MusicResponse::from)
            .toList();

        return new SliceImpl<>(musicResponses, pageable, giftBoxSlice.hasNext());
    }

    public Slice<ItemResponse> getItems(Long lastGiftBoxId, Pageable pageable) {
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberReader.findById(memberId);

        LocalDateTime lastGiftBoxDate = LocalDateTime.now();
        if (lastGiftBoxId != null) {
            GiftBox lastGiftBox = giftBoxReader.findById(lastGiftBoxId);
            Receiver lastReceiver = receiverReader.findByMemberAndGiftBox(member, lastGiftBox);

            lastGiftBoxDate = lastReceiver.getCreatedAt();
        }
        Slice<GiftBox> giftBoxSlice = giftBoxReader.searchReceivedGiftBoxesWithGiftBySlice(member,
            lastGiftBoxDate, pageable);

        List<ItemResponse> itemResponses = giftBoxSlice.stream()
            .map(ItemResponse::from)
            .toList();

        return new SliceImpl<>(itemResponses, pageable, giftBoxSlice.hasNext());
    }
}
