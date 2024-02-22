package com.dilly.admin.adaptor;

import com.dilly.gift.dao.AdminGiftBoxRepository;
import com.dilly.gift.domain.giftbox.admin.AdminGiftBox;
import com.dilly.gift.domain.giftbox.admin.AdminType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminGiftBoxReader {

    private final AdminGiftBoxRepository adminGiftBoxRepository;

    public AdminGiftBox findByAdminType(AdminType adminType) {
        return adminGiftBoxRepository.findByAdminType(adminType);
    }
}
