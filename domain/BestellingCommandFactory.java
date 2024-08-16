package domain;

import java.util.function.Function;

import utils.BestellingStatusEnum;
import utils.BetaalStatusEnum;
import utils.BulkOpsEnum;

public class BestellingCommandFactory {
	public static Function<B2BBestelling, ConcreteCommand<B2BBestelling>> createBestellingCommand(BulkOpsEnum bulkOps,
			Enum actie) {
		return switch (bulkOps) {
		case WIJZIG_BESTELLING_BETAAL_STATUS -> wijzigBetaalStatus(actie);
		case WIJZIG_BESTELLING_STATUS -> wijzigBestellingStatus(actie);
		default -> throw new IllegalArgumentException("Unexpected value: " + bulkOps);
		};
	}

	private static Function<B2BBestelling, ConcreteCommand<B2BBestelling>> wijzigBestellingStatus(Enum actie) {
		if (actie instanceof BestellingStatusEnum bestellingstatus) {
			return (B2BBestelling b) -> new ConcreteCommand<B2BBestelling>(b) {
				@Override
				public void execute() {
					this.item.wijzigBestellingStatus(bestellingstatus);
				}
			};
		} else {
			throw new IllegalArgumentException("Ongeldige enum");
		}
	}

	private static Function<B2BBestelling, ConcreteCommand<B2BBestelling>> wijzigBetaalStatus(Enum actie) {
		if (actie instanceof BetaalStatusEnum betaalstatus) {
			return (B2BBestelling b) -> new ConcreteCommand<B2BBestelling>(b) {
				@Override
				public void execute() {
					this.item.setBetaalStatus(betaalstatus);
				}
			};
		} else {
			throw new IllegalArgumentException("Ongeldige enum");
		}
	}
}
