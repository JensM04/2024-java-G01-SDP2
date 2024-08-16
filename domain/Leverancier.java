package domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;
import java.util.function.Function;

import dto.GebruikerDTO;
import exceptions.RegistreerGebruikerException;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import repository.BedrijfDao;
import repository.BestellingDao;
import repository.BestellingDaoJpa;
import repository.BetalingDao;
import repository.BetalingDaoJpa;
import repository.ProductDao;
import utils.BestellingStatusEnum;
import utils.BetaalStatusEnum;
import utils.JPAUtil;

@Entity
public class Leverancier extends Gebruiker implements Serializable {

	private static final long serialVersionUID = 1L;
	@Transient
	private B2BBestelling huidigeBestelling;

	@Transient
	private Product huidigProduct;

	@ManyToOne
	@JoinTable
    private B2BBedrijf bedrijf;

	@Transient
	private BestellingDao bestellingRepo;
	@Transient
	private BedrijfDao bedrijfRepo;

	@Transient
	private ProductDao productRepo;
	
	@Transient
	private BetalingDao betalingRepo;
	
	protected Leverancier() {
	}

	public Leverancier(GebruikerDTO gebruiker, B2BBedrijf bedrijf) throws RegistreerGebruikerException {
		super(gebruiker);
		if(bedrijf == null)
			throw new IllegalArgumentException("Bedrijf mag niet leeg zijn");
		setBedrijf(bedrijf);
		this.bedrijf = bedrijf;
	}

	private void setBedrijf(B2BBedrijf bedrijf) {
		this.bedrijf = bedrijf;
		bedrijf.addLeverancierAccount(this);

	}

	public List<? extends Bestelling> getAlleBestellingen() {
		return this.bedrijf.getInkomendeBestellingen();

	}

	public Bestelling getBestelling(UUID orderUuid) {
		B2BBestelling bestelling = this.bedrijf.getInkomendeBestellingByUuid(orderUuid);
		if(bestelling == null) throw new IllegalArgumentException("Ongeldig bestel id, bestaat niet");
		this.huidigeBestelling = bestelling;
		return huidigeBestelling;
	}

	public void setHuidigeBestelling(Bestelling huidigeBestelling) {
		this.huidigeBestelling = (B2BBestelling) huidigeBestelling;
	}

	public Bestelling getHuidigeBestelling() {
		return this.huidigeBestelling;
	}
	
	public Bedrijf getBedrijf() {
		return bedrijf;
	}

	public void wijzigBestelling(BestellingStatusEnum orderstatus, BetaalStatusEnum betaalstatus) {
		if (this.bestellingRepo == null) {
			this.bestellingRepo = new BestellingDaoJpa();
		}
		bestellingRepo.startTransaction();
		huidigeBestelling.wijzigBestelling(orderstatus, betaalstatus);
		this.bestellingRepo.commitTransaction();
	}

	public List<Bedrijf> getAlleKlanten() {
		return this.bedrijf.getAlleKlanten();
	}

	public Bedrijf getKlantById(int klantId) {
		throw new UnsupportedOperationException();
	}

	public int getOpenstaandeBestellingen() {
		return (int) getAlleBestellingen().stream().filter(b -> b.getBetaalStatus() != BetaalStatusEnum.BETAALD).count();
	}

	public List<Product> getBeschikbareProducten() {
		List<Product> beschikbareProducten = new ArrayList<>();
		for(ProductInBestellingInterface productInBestelling : huidigeBestelling.getProductenInBestelling()) {
			if(productInBestelling.getIsInStock()) {
				beschikbareProducten.add(
						new Product(productInBestelling.getNaam(),
									productInBestelling.getEenheidsprijs(),
									productInBestelling.getAantal()
						)
				);
			}
		}
		return beschikbareProducten;
	}

	public void wijzigProduct(String naam, BigDecimal prijs, int aantalInStock) {
		this.productRepo.startTransaction();
		huidigProduct.wijzigProduct(naam, prijs, aantalInStock);
		this.productRepo.commitTransaction();
	}

	public void addProduct(String naam, BigDecimal prijs, int aantalInStock) {
		Product nieuwProduct = new Product(naam,prijs,aantalInStock);
		productRepo.addProduct(nieuwProduct);
	}

	public Product getHuidigProduct() {
		return this.huidigProduct;
	}

	public void voerBestellingCommandUitOpBestellingList(List<Bestelling> bestellingen, Function<B2BBestelling, ConcreteCommand<B2BBestelling>> commandFunction) {
		ListIterator<Bestelling> iterator = bestellingen.listIterator();
		JPAUtil.beginTransaction();
		while(iterator.hasNext()) {
			if(iterator.next() instanceof B2BBestelling bestelling) {
				ConcreteCommand<B2BBestelling> command = commandFunction.apply(bestelling);
				command.execute();
			}
		}
		JPAUtil.commitTransaction();
	}

	public void verwerkBetalingen() {
		if (this.betalingRepo == null) {
			this.betalingRepo = new BetalingDaoJpa();
		}
		List<Betaling> betalingen = betalingRepo.findAll();
		
		this.betalingRepo.startTransaction();
		betalingen.forEach(Betaling::verwerk);
		this.betalingRepo.commitTransaction();
	}

	public boolean isWachtwoordGewijzigd() {
		return this.isWachtwoordVeranderd;
	}
	
	public List<Betaling> getBetalingenBedrijf(Bedrijf bedrijf){
		if (this.betalingRepo == null) {
			this.betalingRepo = new BetalingDaoJpa();
		}
		return betalingRepo.findByBedrijf(bedrijf);
	}
}
