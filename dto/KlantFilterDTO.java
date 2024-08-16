package dto;

import domain.Bedrijf;

public class KlantFilterDTO {
	public String search;
	public String naam;
	public String adres;

	public boolean isContained(Bedrijf klant) {
		if(!this.isNaamContained(klant) || !this.isAdresContained(klant))
			return false;

		return true;
	}

	public boolean isSearchContained(Bedrijf klant) {
		if(search == null || search.isEmpty()) return true;
		return klant.getNaam().toLowerCase().contains(this.search.toLowerCase()) ||
				klant.getAdres().getAdresString().contains(this.search.toLowerCase());
	}

	private boolean isNaamContained(Bedrijf klant) {
		if(this.naam == null || this.naam.isEmpty()) {
			return true;
		}
		return klant.getNaam().toLowerCase().contains(this.naam.toLowerCase());
	}

	private boolean isAdresContained(Bedrijf klant) {
		if(this.adres == null || this.adres.isEmpty()) {
			return true;
		}

		return klant.getAdres().getAdresString().toLowerCase().contains(this.adres.toLowerCase());
	}
}
