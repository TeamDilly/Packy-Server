package com.dilly.admin.dao;

import com.dilly.admin.domain.notice.Notice;
import com.dilly.admin.domain.notice.NoticeImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeImageRepository extends JpaRepository<NoticeImage, Long> {

    List<NoticeImage> findAllByNoticeOrderBySequence(Notice notice);

}
