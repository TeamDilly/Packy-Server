package com.dilly.gift;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Gift {

	@Enumerated(EnumType.STRING)
	private GiftType giftType;
	private String giftUrl;
	private String giftMessage;
}
