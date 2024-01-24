package com.dilly.gift.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilly.gift.Envelope;

public interface EnvelopeRepository extends JpaRepository<Envelope, Long> {
}
