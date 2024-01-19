package com.dilly.gift.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilly.gift.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
