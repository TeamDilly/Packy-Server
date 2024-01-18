package com.dilly.gift.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilly.gift.Music;

public interface MusicRepository extends JpaRepository<Music, Long> {
}
