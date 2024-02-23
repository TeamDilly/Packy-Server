package com.dilly.gift.adaptor;

import com.dilly.gift.dao.LastViewedAdminTypeRepository;
import com.dilly.gift.domain.giftbox.admin.LastViewedAdminType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LastViewedAdminTypeWriter {

    private final LastViewedAdminTypeRepository lastViewedAdminTypeRepository;

    public void save(LastViewedAdminType lastViewedAdminType) {
        lastViewedAdminTypeRepository.save(lastViewedAdminType);
    }
}
