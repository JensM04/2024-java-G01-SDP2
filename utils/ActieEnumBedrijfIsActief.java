package utils;

public enum ActieEnumBedrijfIsActief {
	IS_ACTIEF("Is actief"),
	NIET_ACTIEF("Is niet actief");

	public final String label;

	ActieEnumBedrijfIsActief(String label) {
		this.label = label;
	}
}
