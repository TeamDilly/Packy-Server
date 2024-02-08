package com.dilly.gift.dao;

import com.dilly.gift.domain.GiftBox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftBoxRepository extends JpaRepository<GiftBox, Long> {
	
	GiftBox findTopByOrderByIdDesc();
}
