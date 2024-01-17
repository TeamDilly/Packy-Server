package com.dilly.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilly.admin.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
