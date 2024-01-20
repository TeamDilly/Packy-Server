package com.dilly.gift.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilly.gift.GiftBox;
import com.dilly.gift.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

	List<Photo> findAllByGiftBox(GiftBox giftBox);
}
