package com.dilly.gift.dao;

import com.dilly.member.ProfileImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

    List<ProfileImage> findAllByOrderBySequenceAsc();
}
