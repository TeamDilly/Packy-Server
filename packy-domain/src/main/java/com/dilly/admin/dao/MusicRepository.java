package com.dilly.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilly.admin.Music;

public interface MusicRepository extends JpaRepository<Music, Long> {
}
