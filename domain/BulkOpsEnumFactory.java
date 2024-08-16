package domain;

import java.util.Collections;
import java.util.List;

import utils.BulkOpsEnum;

public class BulkOpsEnumFactory {
	public static List<BulkOpsEnum> createBulkOpsEnumListVoorBestellingen() {
		List<BulkOpsEnum> bulkOps = List.of(
				BulkOpsEnum.WIJZIG_BESTELLING_STATUS,
				BulkOpsEnum.WIJZIG_BESTELLING_BETAAL_STATUS
				);
		return Collections.unmodifiableList(bulkOps);
	}

	public static List<BulkOpsEnum> createBulkOpsEnumListVoorBedrijven() {
		return Collections.unmodifiableList(List.of(
				BulkOpsEnum.WIJZIG_BEDRIJF_SECTOR,
				BulkOpsEnum.WIJZIG_BEDRIJF_IS_ACTIEF
		));
	}

}
