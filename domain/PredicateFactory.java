package domain;

import java.util.function.Predicate;

import dto.AdresDTO;
import dto.B2BBedrijfDTO;
import dto.BestellingDTO;
import dto.GebruikerDTO;
import dto.LeverancierDTO;
import dto.ProductDTO;

public class PredicateFactory {

	public static Predicate<Bestelling> createBestellingFilterPredicate(BestellingDTO bestellingDTO) {
		return new Predicate<Bestelling>() {
			@Override
			public boolean test(Bestelling t) {
				if (bestellingDTO.uuidString != null && !t.getUuid().toString().toLowerCase().contains(bestellingDTO.uuidString.toLowerCase())) {
					return false;
				}
				// alle waarden afgaan, als er 1 niet overeenkomt, false returnen
				if (bestellingDTO.klantNaam != null && !t.getKlant().getNaam().toLowerCase().contains(bestellingDTO.klantNaam.toLowerCase())) {
					return false;
				}
				if (bestellingDTO.klant != null && !t.getKlant().equals(bestellingDTO.klant)) {
					return false;
				}
				if (bestellingDTO.leveradres != null && !t.getLeveradres().equals(bestellingDTO.leveradres)) {
					return false;
				}
				if (bestellingDTO.bestellingStatus != null
						&& !t.getBestellingStatus().equals(bestellingDTO.bestellingStatus)) {
					return false;
				}
				if (bestellingDTO.betaalStatus != null && t.getBetaalStatus() != bestellingDTO.betaalStatus) {
					return false;
				}
				if(bestellingDTO.bestellingDatum != null && !t.getBestellingDatum().equals(bestellingDTO.bestellingDatum)) {
			          return false;
			    }
				return true;
			}
		};
	}

	public static Predicate<Bestelling> createBestellingSearchPredicate(String query) {
		return new Predicate<Bestelling>() {
			@Override
			public boolean test(Bestelling t) {
				if (String.valueOf(t.getUuid()).contains(query)) {
					return true;
				}
				// alle waarden afgaan en kijken of de query in 1 van die waardes zit, als dat
				// zo is, true returnen
				if (t.getKlant().getNaam().contains(query)) {
					return true;
				}
				if (t.getLeveradres().toString().contains(query)) {
					return true;
				}
				if (t.getBestellingStatus().toString().contains(query)) {
					return true;
				}
				if (String.valueOf(t.getBetaalStatus()).contains(query)) {
					return true;
				}
				if(t.getBestellingDatum().toString().contains(query)) {
			          return true;
			        }
				return false;

			}
		};
	}

	public static Predicate<Bedrijf> createBedrijfFilterPredicate(B2BBedrijfDTO b2bBedrijfDTO) {
		return new Predicate<Bedrijf>() {
			@Override
			public boolean test(Bedrijf t) {
				if (b2bBedrijfDTO.naam != null
						&& !String.valueOf(t.getNaam()).toString().toLowerCase().contains(String.valueOf(b2bBedrijfDTO.naam).toString().toLowerCase())) {
					return false;
				}
				if (b2bBedrijfDTO.email != null && !t.getEmail().contains(b2bBedrijfDTO.email)) {
					return false;
				}
				if (b2bBedrijfDTO.telefoonnummer != null
						&& !t.getTelefoonnummer().contains(b2bBedrijfDTO.telefoonnummer)) {
					return false;
				}
				if (b2bBedrijfDTO.adresString != null
						&& !t.getAdresProperty().getValue().toLowerCase().contains(b2bBedrijfDTO.adresString.toLowerCase())) {
					return false;
				}
				if (b2bBedrijfDTO.aantalOpenstaandeBestellingen != null && !t.aantalOpenstaandeBestellingenInkomendProperty().getValue().toString().toLowerCase().contains(b2bBedrijfDTO.aantalOpenstaandeBestellingen.toLowerCase())) {
					return false;
				}
				if (b2bBedrijfDTO.adres != null && !t.getAdres().equals(b2bBedrijfDTO.adres)) {
					return false;
				}
				if (b2bBedrijfDTO.isActief != null && !t.isActiefProperty().equals(b2bBedrijfDTO.isActief)) {
					return false;
				}
				if (b2bBedrijfDTO.websiteURL != null && !t.getWebsiteURL().contains(b2bBedrijfDTO.websiteURL)) {
					return false;
				}
				if (b2bBedrijfDTO.sector != null && !t.getSector().equals(b2bBedrijfDTO.sector)) {
					return false;
				}
				if (b2bBedrijfDTO.aantalOpenstaandeBestellingen != null && !String.valueOf(t.getUitgaandeBestellingen())
						.contains(b2bBedrijfDTO.aantalOpenstaandeBestellingen)) {
					return false;
				}

				return true;
			}
		};
	}

	public static Predicate<Bedrijf> createBedrijfSearchPredicate(String query) {
		return new Predicate<Bedrijf>() {
			@Override
			public boolean test(Bedrijf t) {
				if(query == null) return true;
				if (t.getNaam().toLowerCase().contains(query.toLowerCase())) {
					return true;
				}
				if (t.getEmail().toLowerCase().contains(query.toLowerCase())) {
					return true;
				}
				if (t.getAdresProperty().getValue().toLowerCase().contains(query.toLowerCase()))
					return true;
				if (t.getTelefoonnummer().contains(query.toLowerCase())) {
					return true;
				}
				if (t.getAdres().toString().contains(query.toLowerCase())) {
					return true;
				}
				if (t.isActiefProperty().toString().toLowerCase().contains(query.toLowerCase())) {
					return true;
				}
				if (t.getWebsiteURL().toLowerCase().contains(query.toLowerCase())) {
					return true;
				}
				if (t.getSector().toString().toLowerCase().equals(query.toLowerCase())) {
					return true;
				}
				if (t.aantalOpenstaandeBestellingenInkomendProperty().toString().toLowerCase().equals(query.toLowerCase())) {
					return true;
				}
				return false;
			}
		};
	}

	public static Predicate<Adres> createAdresFilterPredicate(AdresDTO adresDTO) {
		return new Predicate<Adres>() {
			@Override
			public boolean test(Adres t) {
				if (adresDTO.straat != null && !t.getStraat().contains(adresDTO.straat)) {
					return false;
				}
				if (adresDTO.huisnummer != null && !String.valueOf(t.getHuisnummer()).equals(adresDTO.huisnummer)) {
					return false;
				}
				if (adresDTO.bus != null && !t.getBus().contains(adresDTO.bus)) {
					return false;
				}
				if (adresDTO.postcode != null && !(t.getPostcode() == adresDTO.postcode.intValue())) {
					return false;
				}
				if (adresDTO.gemeente != null && !t.getGemeente().contains(adresDTO.gemeente)) {
					return false;
				}
				return true;
			}
		};
	}

	public static Predicate<Adres> createAdresSearchPredicate(String query) {
		return new Predicate<Adres>() {
			@Override
			public boolean test(Adres t) {
				if (t.getStraat().contains(query)) {
					return true;
				}
				if (String.valueOf(t.getHuisnummer()).contains(query)) {
					return true;
				}
				if (t.getBus().contains(query)) {
					return true;
				}
				if (String.valueOf(t.getPostcode()).contains(query)) {
					return true;
				}
				if (t.getGemeente().contains(query)) {
					return true;
				}
				return false;
			}
		};
	}

	public static Predicate<Gebruiker> createGebruikerFilterPredicate(GebruikerDTO gebruikerDTO) {
		return new Predicate<Gebruiker>() {
			@Override
			public boolean test(Gebruiker t) {
				if (gebruikerDTO.naam != null && !t.getGebruikersnaam().contains(gebruikerDTO.naam)) {
					return false;
				}
				if (gebruikerDTO.wachtwoord != null && !t.getWachtwoord().contains(gebruikerDTO.wachtwoord)) {
					return false;
				}
				return true;
			}
		};
	}

	public static Predicate<Gebruiker> createGebruikerSearchPredicate(String query) {
		return new Predicate<Gebruiker>() {
			@Override
			public boolean test(Gebruiker t) {
				if (t.getGebruikersnaam().contains(query)) {
					return true;
				}
				if (t.getWachtwoord().contains(query)) {
					return true;
				}
				return false;
			}
		};
	}

	// Klant is eigenlijk een bedrijf, dus overbodig
//				  public static Predicate<Klant> createKlantFilterPredicate(KlantDTO klantDTO) {
//				    return new Predicate<Klant>() {
//				      @Override
//				      public boolean test(Klant t) {
//				        if(klantDTO.naam != null && !t.getGebruikersnaam().contains(klantDTO.naam)) {
//				          return false;
//				        }
//				        if(klantDTO.email != null && !t.getEmail().contains(klantDTO.email)) {
//				          return false;
//				        }
//				        if(klantDTO.telefoonnummer != null && !t.get().contains(klantDTO.telefoonnummer)) {
//				          return false;
//				        }
//				        return true;
//				      }
//				    };
//				  }

//				  public static Predicate<Klant> createKlantSearchPredicate(String query) {
//				    return new Predicate<Klant>() {
//				      @Override
//				      public boolean test(Klant t) {
//				        if(t.getGebruikersnaam().contains(query)) {
//				          return true;
//				        }
//				        if(t.getEmail().contains(query)) {
//				          return true;
//				        }
//				        if(t.getTelefoonnummer().contains(query)) {
//				          return true;
//				        }
//				        return false;
//				      }
//				    };
//				  }

	public static Predicate<Leverancier> createLeverancierFilterPredicate(LeverancierDTO leverancierDTO) {
		return new Predicate<Leverancier>() {
			@Override
			public boolean test(Leverancier t) {
				// lijst van bestellingen?
				// huidige bestelling?
				if (leverancierDTO.naam != null && !t.getGebruikersnaam().contains(leverancierDTO.naam)) {
					return false;
				}
				if (leverancierDTO.wachtwoord != null && !t.getWachtwoord().contains(leverancierDTO.wachtwoord)) {
					return false;
				}
				// is huidige bestelling nodig?
				// bedrijf?
				return true;
			}
		};
	}

	public static Predicate<Leverancier> createLeverancierSearchPredicate(String query) {
		return new Predicate<Leverancier>() {
			@Override
			public boolean test(Leverancier t) {
				if (t.getGebruikersnaam().contains(query)) {
					return true;
				}
				if (t.getWachtwoord().contains(query)) {
					return true;
				}
				return false;
			}
		};
	}

	public static Predicate<Product> createProductFilterPredicate(ProductDTO productDTO) {
		return new Predicate<Product>() {
			@Override
			public boolean test(Product t) {
				if (productDTO.naam != null && !t.getNaam().contains(productDTO.naam)) {
					return false;
				}
				if (productDTO.aantalInStock != null
						&& !String.valueOf(t.getAantalInStock()).contains(String.valueOf(productDTO.aantalInStock))) {
					return false;
				}
				if (productDTO.eenheidsprijs != null && !t.getEenheidsprijs().equals(productDTO.eenheidsprijs)) {
					return false;
				}
				return true;
			}
		};
	}

	public static Predicate<Product> createProductSearchPredicate(String query) {
		return new Predicate<Product>() {
			@Override
			public boolean test(Product t) {
				if (t.getNaam().contains(query)) {
					return true;
				}
				if (String.valueOf(t.getAantalInStock()).contains(query)) {
					return true;
				}
				if (t.getEenheidsprijs().equals(query)) {
					return true;
				}
				return false;
			}
		};
	}
}
