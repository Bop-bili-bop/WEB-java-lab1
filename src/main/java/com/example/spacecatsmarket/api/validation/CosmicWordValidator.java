package com.example.spacecatsmarket.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Locale;
import java.util.Set;

public class CosmicWordValidator implements ConstraintValidator<CosmicWordCheck, String> {

	private static final Set<String> COSMIC_WORDS = Set.of(
			"star",
			"galaxy",
			"comet",
			"nebula",
			"cosmic",
			"lunar",
			"orbit",
			"asteroid",
			"meteor"
	);

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.isBlank()) {
			return true;
		}

		String normalized = value.toLowerCase(Locale.ROOT);
		return COSMIC_WORDS.stream().anyMatch(normalized::contains);
	}
}
