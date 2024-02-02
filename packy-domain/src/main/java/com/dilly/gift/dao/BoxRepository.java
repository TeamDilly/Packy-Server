package com.dilly.gift.dao;

import com.dilly.gift.domain.Box;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxRepository extends JpaRepository<Box, Long> {

    List<Box> findAllByOrderBySequenceAsc();
}
