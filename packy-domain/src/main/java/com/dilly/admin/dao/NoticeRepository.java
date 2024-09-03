package com.dilly.admin.dao;

import com.dilly.admin.domain.notice.Notice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findAllByOrderBySequence();
}
