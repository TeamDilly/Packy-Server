package com.dilly.gift.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilly.gift.Letter;

public interface LetterRepository extends JpaRepository<Letter, Long> {
}
