package com.dilly.admin.adaptor;

import com.dilly.admin.dao.NoticeImageRepository;
import com.dilly.admin.domain.notice.Notice;
import com.dilly.admin.domain.notice.NoticeImage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoticeImageReader {

    private final NoticeImageRepository noticeImageRepository;

    public List<NoticeImage> findAllByNoticeOrderBySequence(Notice notice) {
        return noticeImageRepository.findAllByNoticeOrderBySequence(notice);
    }
}
