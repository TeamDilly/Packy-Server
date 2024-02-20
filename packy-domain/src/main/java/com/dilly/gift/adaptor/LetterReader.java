package com.dilly.gift.adaptor;

import com.dilly.exception.ErrorCode;
import com.dilly.exception.entitynotfound.EntityNotFoundException;
import com.dilly.gift.dao.LetterRepository;
import com.dilly.gift.dao.querydsl.LetterQueryRepository;
import com.dilly.gift.domain.letter.Letter;
import com.dilly.member.domain.Member;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LetterReader {

    private final LetterRepository letterRepository;
    private final LetterQueryRepository letterQueryRepository;

    public Letter findById(Long id) {
        return letterRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.LETTER_NOT_FOUND));
    }

    public Slice<Letter> searchBySlice(Member member, LocalDateTime lastLetterDate, Pageable pageable) {
        return letterQueryRepository.searchBySlice(member, lastLetterDate, pageable);
    }
}
