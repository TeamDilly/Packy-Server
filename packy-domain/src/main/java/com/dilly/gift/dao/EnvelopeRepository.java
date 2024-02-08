package com.dilly.gift.dao;

import com.dilly.gift.domain.Envelope;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnvelopeRepository extends JpaRepository<Envelope, Long> {

    List<Envelope> findAllByOrderBySequenceAsc();
}
