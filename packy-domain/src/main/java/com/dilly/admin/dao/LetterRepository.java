package com.dilly.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilly.admin.Letter;

public interface LetterRepository extends JpaRepository<Letter, Long> {
}
