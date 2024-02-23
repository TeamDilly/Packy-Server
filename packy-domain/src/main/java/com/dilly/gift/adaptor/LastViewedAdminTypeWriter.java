package com.dilly.gift.adaptor;

import com.dilly.gift.dao.LastViewdAdminTypeRepository;
import com.dilly.gift.domain.giftbox.admin.LastViewdAdminType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LastViewedAdminTypeWriter {

    private final LastViewdAdminTypeRepository lastViewdAdminTypeRepository;

    public void save(LastViewdAdminType lastViewdAdminType) {
        lastViewdAdminTypeRepository.save(lastViewdAdminType);
    }
}
