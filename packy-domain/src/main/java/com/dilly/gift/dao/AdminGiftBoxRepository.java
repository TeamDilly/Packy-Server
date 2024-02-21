package com.dilly.gift.dao;

import com.dilly.admin.domain.giftbox.ScreenType;
import com.dilly.gift.domain.giftbox.AdminGiftBox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminGiftBoxRepository extends JpaRepository<AdminGiftBox, Long> {

    AdminGiftBox findByScreenType(ScreenType screenType);
}
