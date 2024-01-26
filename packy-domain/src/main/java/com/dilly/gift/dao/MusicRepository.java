package com.dilly.gift.dao;

import com.dilly.gift.Music;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {

    List<Music> findAllByOrderBySequenceAsc();
}
