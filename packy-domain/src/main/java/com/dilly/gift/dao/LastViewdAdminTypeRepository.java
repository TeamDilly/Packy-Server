package com.dilly.gift.dao;

import com.dilly.gift.domain.giftbox.admin.LastViewdAdminType;
import com.dilly.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LastViewdAdminTypeRepository extends JpaRepository<LastViewdAdminType, Long> {

    Optional<LastViewdAdminType> findByMember(Member member);
}
