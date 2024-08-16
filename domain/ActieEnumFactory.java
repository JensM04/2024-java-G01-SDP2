package domain;

import java.util.Collections;
import java.util.List;

import utils.ActieEnumBedrijfIsActief;
import utils.BedrijfSectorEnum;
import utils.BestellingStatusEnum;
import utils.BetaalStatusEnum;
import utils.BulkOpsEnum;

public class ActieEnumFactory {
	public static List<? extends Enum> createActieEnumListVoorBulkOpsBestellingen(BulkOpsEnum bulkOps) {
		return switch (bulkOps) {
		case WIJZIG_BESTELLING_BETAAL_STATUS -> actieListVoorWijzigBestellingBetaaldStatus();
		case WIJZIG_BESTELLING_STATUS -> actieListVoorWijzigBestellingStatus();
		default -> throw new IllegalArgumentException("Unexpected value: " + bulkOps);
		};
	}

	public static List<? extends Enum> createEnumListVoorBulkOpsBedrijven(BulkOpsEnum bulkOps) {
		return switch (bulkOps) {
		case WIJZIG_BEDRIJF_SECTOR -> actieListVoorWijzigBedrijfSector();
		case WIJZIG_BEDRIJF_IS_ACTIEF -> actieListVoorWijzigIsActief();
		default -> throw new IllegalArgumentException("Unexpected value: " + bulkOps);
		};
	}

	private static List<ActieEnumBedrijfIsActief> actieListVoorWijzigIsActief() {
		return List.of(ActieEnumBedrijfIsActief.values());
	}

	private static List<BetaalStatusEnum> actieListVoorWijzigBestellingBetaaldStatus() {
		List<BetaalStatusEnum> actieEnums = List.of(BetaalStatusEnum.values());
		return Collections.unmodifiableList(actieEnums);
	}

	private static List<BestellingStatusEnum> actieListVoorWijzigBestellingStatus() {
		List<BestellingStatusEnum> actieEnums = List.of(
				BestellingStatusEnum.values());
		return Collections.unmodifiableList(actieEnums);
	}

	private static List<BedrijfSectorEnum> actieListVoorWijzigBedrijfSector() {
		List<BedrijfSectorEnum> sectors = List.of(
				BedrijfSectorEnum.values());
		return sectors;
	}

}
