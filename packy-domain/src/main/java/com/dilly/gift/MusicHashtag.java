package com.dilly.gift;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class MusicHashtag {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String hashtag;

	@ManyToOne(fetch = FetchType.LAZY)
	private Music music;
}
