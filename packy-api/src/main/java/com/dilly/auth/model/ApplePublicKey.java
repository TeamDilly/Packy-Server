package com.dilly.auth.model;

import java.util.ArrayList;
import java.util.Optional;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApplePublicKey {

	ArrayList<Key> keys;

	@Getter
	@ToString
	public static class Key {

		String kty;
		String kid;
		String use;
		String alg;
		String n;
		String e;
	}

	public Optional<Key> getMatchedKeyBy(String kid, String alg) {
		return this.keys.stream()
			.filter(key -> key.kid.equals(kid) && key.alg.equals(alg))
			.findFirst();
	}
}
