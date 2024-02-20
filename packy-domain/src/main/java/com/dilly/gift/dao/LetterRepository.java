package com.dilly.gift.dao;

import com.dilly.gift.domain.letter.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, Long> {
}
