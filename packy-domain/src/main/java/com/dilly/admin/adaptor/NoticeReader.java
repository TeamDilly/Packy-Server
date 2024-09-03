package com.dilly.admin.adaptor;

import com.dilly.admin.dao.NoticeRepository;
import com.dilly.admin.domain.notice.Notice;
import com.dilly.exception.EntityNotFoundException;
import com.dilly.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoticeReader {

    private final NoticeRepository noticeRepository;

    public Notice findById(Long id) {
        return noticeRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(ErrorCode.NOTICE_NOT_FOUND)
        );
    }

    public List<Notice> findAllByOrderBySequence() {
        return noticeRepository.findAllByOrderBySequence();
    }
}
