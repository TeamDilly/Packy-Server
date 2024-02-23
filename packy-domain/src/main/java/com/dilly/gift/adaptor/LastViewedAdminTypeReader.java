package com.dilly.gift.adaptor;

import com.dilly.gift.dao.LastViewdAdminTypeRepository;
import com.dilly.gift.domain.giftbox.admin.LastViewdAdminType;
import com.dilly.member.domain.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LastViewedAdminTypeReader {

    private final LastViewdAdminTypeRepository lastViewdAdminTypeRepository;

    public Optional<LastViewdAdminType> findByMember(Member member) {
        return lastViewdAdminTypeRepository.findByMember(member);
    }
}
