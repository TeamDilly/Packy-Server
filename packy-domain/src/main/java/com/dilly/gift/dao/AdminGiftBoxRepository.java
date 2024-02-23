package com.dilly.gift.dao;

import com.dilly.gift.domain.giftbox.admin.AdminGiftBox;
import com.dilly.gift.domain.giftbox.admin.AdminType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminGiftBoxRepository extends JpaRepository<AdminGiftBox, Long> {

    Optional<AdminGiftBox> findByAdminType(AdminType adminType);
}
