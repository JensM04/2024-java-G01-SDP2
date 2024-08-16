package domain;

import java.util.function.Function;

import utils.ActieEnumBedrijfIsActief;
import utils.BedrijfSectorEnum;
import utils.BulkOpsEnum;

public class BedrijfCommandFactory {
	public static Function<B2BBedrijf, ConcreteCommand<B2BBedrijf>> createBedrijfCommand(BulkOpsEnum bulkOps, Enum actie) {
		return switch(bulkOps)  {
		case WIJZIG_BEDRIJF_SECTOR -> wijzigBedrijfSector(actie);
		case WIJZIG_BEDRIJF_IS_ACTIEF -> wijzigBedrijfIsActief(actie);
		default -> throw new IllegalArgumentException("Unexpected value: " + bulkOps);
		};
	}

	public static Function<B2BBedrijf, ConcreteCommand<B2BBedrijf>> wijzigBedrijfSector(Enum actie) {
		if(actie instanceof BedrijfSectorEnum sectorEnum) {
			return (B2BBedrijf bedrijf) -> new ConcreteCommand<B2BBedrijf>(bedrijf) {
				@Override
				public void execute() {
					this.item.setSector(sectorEnum);
				}
			};
		} else {
			throw new IllegalArgumentException("Moet een sector zijn");
		}
	}

	public static Function<B2BBedrijf, ConcreteCommand<B2BBedrijf>> wijzigBedrijfIsActief(Enum actie) {
		if(actie instanceof ActieEnumBedrijfIsActief bedrijfActie) {
			return (B2BBedrijf bedrijf) -> new ConcreteCommand<B2BBedrijf>(bedrijf) {
				@Override
				public void execute() {
					this.item.setIsActief((bedrijfActie == ActieEnumBedrijfIsActief.IS_ACTIEF));
				}
			};
		} else {
			throw new IllegalArgumentException("Moet een bedrijfactie zijn");
		}
	}
}
