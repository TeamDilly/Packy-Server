package com.dilly.admin.adaptor;

import com.dilly.admin.dao.NoticeRepository;
import com.dilly.admin.domain.notice.Notice;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoticeReader {

    private final NoticeRepository noticeRepository;

    public List<Notice> findAllByOrderBySequence() {
        return noticeRepository.findAllByOrderBySequence();
    }
}
