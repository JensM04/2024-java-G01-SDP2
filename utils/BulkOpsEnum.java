package utils;

public enum BulkOpsEnum {
	WIJZIG_BESTELLING_STATUS("Wijzig status"),
	WIJZIG_BESTELLING_BETAAL_STATUS("Wijzig betaal status"),
	WIJZIG_BEDRIJF_SECTOR("Wijzig sector"),
	WIJZIG_BEDRIJF_IS_ACTIEF("Wijzig is actief");

	public final String label;

	BulkOpsEnum(String label) {
		this.label = label;
	}
}
