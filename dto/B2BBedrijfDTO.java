package dto;

import domain.Bedrijf;
import utils.BedrijfSectorEnum;

public class B2BBedrijfDTO {
	public String search;
	public String naam;
	public String email;
	public String telefoonnummer;
	public AdresDTO adres;
	public String kortAdres;
	public Boolean isActief;
	public String websiteURL;
	public BedrijfSectorEnum sector;
	public String aantalOpenstaandeBestellingen;
	public String btw;

//	public boolean isSearchContained(Bedrijf bedrijf) {
//		if (search == null || search.isBlank()) {
//			return true;
//		}
//		return bedrijf.getNaam().contains(search)
//		|| bedrijf.getKortAdres().get().contains(search)
//		|| String.format("%d", bedrijf.aantalOpenstaandeBestellingenProperty().get()).equals(aantalOpenstaandeBestellingen);
//		//TODO: search op sector
//	}
//
//	public boolean isNaamContained(Bedrijf bedrijf) {
//		if (naam == null || naam.isBlank()) {
//			return true;
//		}
//		return bedrijf.getNaam().contains(naam);
//	}
//
//	public boolean isAdresContained(Bedrijf bedrijf) {
//		if (kortAdres == null || kortAdres.isBlank())
//			return true;
//		return bedrijf.getKortAdres().get().contains(kortAdres);
//	}
//
//	public boolean isAantalOpenstaandeBestellingenContained(Bedrijf bedrijf) {
//		if (aantalOpenstaandeBestellingen == null || aantalOpenstaandeBestellingen.isBlank()) {
//			return true;
//		}
//		return String.format("%d", bedrijf.aantalOpenstaandeBestellingenProperty().get()).equals(aantalOpenstaandeBestellingen);
//	}
//
//	public boolean isSectorContained(Bedrijf bedrijf) {
//		if (sector == null) {
//			return true;
//		}
//		return bedrijf.getSector() == sector;
//	}


	public String adresString;
	public boolean hasError = false;
	public String naamError;
	public String adresError;
	public String emailError;
	public String telefoonnummerError;
	public String websiteUrlError;
	public String sectorError;
	public String sectorString;
}
