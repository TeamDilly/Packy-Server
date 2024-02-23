package com.dilly.gift.adaptor;

import com.dilly.gift.dao.LastViewedAdminTypeRepository;
import com.dilly.gift.domain.giftbox.admin.LastViewedAdminType;
import com.dilly.member.domain.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LastViewedAdminTypeReader {

    private final LastViewedAdminTypeRepository lastViewedAdminTypeRepository;

    public Optional<LastViewedAdminType> findByMember(Member member) {
        return lastViewedAdminTypeRepository.findByMember(member);
    }
}
