package com.dilly.gift.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilly.gift.GiftBox;

public interface GiftBoxRepository extends JpaRepository<GiftBox, Long> {
	
	GiftBox findTopByOrderByIdDesc();
}
