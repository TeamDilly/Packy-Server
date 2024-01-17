package com.dilly.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilly.admin.MusicHashtag;

public interface MusicHashtagRepository extends JpaRepository<MusicHashtag, Long> {
}
