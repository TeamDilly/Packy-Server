package com.dilly.gift.adaptor;

import com.dilly.gift.Music;
import com.dilly.gift.dao.MusicRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MusicReader {

	private final MusicRepository musicRepository;

	public List<Music> findAllByOrderBySequenceAsc() {
		return musicRepository.findAllByOrderBySequenceAsc();
	}

}
