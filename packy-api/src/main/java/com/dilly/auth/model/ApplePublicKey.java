package com.dilly.auth.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

import java.util.ArrayList;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(NON_NULL)
public class ApplePublicKey {

	ArrayList<Key> keys;

	@Getter
	@JsonInclude(NON_NULL)
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
