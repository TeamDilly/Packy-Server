package com.dilly.admin.adaptor;

import com.dilly.admin.domain.giftbox.ScreenType;
import com.dilly.gift.dao.AdminGiftBoxRepository;
import com.dilly.gift.domain.giftbox.AdminGiftBox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminGiftBoxReader {

    private final AdminGiftBoxRepository adminGiftBoxRepository;

    public AdminGiftBox findByScreenType(ScreenType screenType) {
        return adminGiftBoxRepository.findByScreenType(screenType);
    }
}
