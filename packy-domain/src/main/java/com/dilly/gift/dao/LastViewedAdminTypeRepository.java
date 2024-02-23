package com.dilly.gift.dao;

import com.dilly.gift.domain.giftbox.admin.LastViewedAdminType;
import com.dilly.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LastViewedAdminTypeRepository extends JpaRepository<LastViewedAdminType, Long> {

    Optional<LastViewedAdminType> findByMember(Member member);
}
