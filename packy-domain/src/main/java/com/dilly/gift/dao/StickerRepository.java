package com.dilly.gift.dao;

import com.dilly.gift.domain.sticker.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickerRepository extends JpaRepository<Sticker, Long> {

}
