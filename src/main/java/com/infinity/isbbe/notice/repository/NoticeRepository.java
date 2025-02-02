package com.infinity.isbbe.notice.repository;

import com.infinity.isbbe.notice.aggregate.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    List<Notice> findByNoticeCode(int noticeCode);
}
