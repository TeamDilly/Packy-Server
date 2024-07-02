package com.dilly.gift.adaptor;

import com.dilly.gift.dao.BoxRepository;
import com.dilly.gift.domain.Box;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoxWriter {

    private final BoxRepository boxRepository;

    public void saveAll(List<Box> boxes) {
        boxRepository.saveAll(boxes);
    }
}
