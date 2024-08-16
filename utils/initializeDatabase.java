package utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import domain.Adres;
import domain.B2BBedrijf;
import domain.B2BBestelling;
import domain.Betaling;
import domain.Gebruiker;
import domain.GebruikerFactory;
import domain.Product;
import domain.ProductInBestelling;
import exceptions.RegistreerGebruikerException;
import jakarta.persistence.EntityManager;

public class initializeDatabase {

	public static void initDB() throws RegistreerGebruikerException {
		EntityManager entityManager = JPAUtil.getEntityManager();

		// adressen
		Adres adres1 = new Adres("Reepstraat", 12, "A", 9000, "Gent");
		Adres adres2 = new Adres("Zavelstraat", 12, "A", 7000, "Brussel");
		Adres adres3 = new Adres("Hoekstraat", 12, "A", 4000, "Antwerpen");
		Adres adres4 = new Adres("Example Street", 123, "A", 4000, "City");
		Adres adres5 = new Adres("Main Boulevard", 456, "B", 4000, "Gotham");
		Adres adres6 = new Adres("Park Avenue", 789, "C", 4000, "Metropolis");

		// bedrijven
		B2BBedrijf proximus = new B2BBedrijf("Proximus", "proximus@gmail.com", "+32 463 49 30 44", adres1,
				"http://www.proximus.be", BedrijfSectorEnum.IT);
		B2BBedrijf google = new B2BBedrijf("Google LLC", "info@google.com", "+32 463 49 30 44", adres4,
				"http://www.google.com", BedrijfSectorEnum.IT);
		B2BBedrijf microsoft = new B2BBedrijf("Microsoft", "Microsoft@gmail.com", "+32 463 49 30 44", adres2,
				"http://www.Microsoft.be", BedrijfSectorEnum.IT);
		B2BBedrijf apple = new B2BBedrijf("Apple Inc.", "Apple@gmail.com", "+32 463 49 30 44", adres3, "http://www.Apple.be",
				BedrijfSectorEnum.IT);
		B2BBedrijf amazon = new B2BBedrijf("Amazon.com, Inc.", "contact@amazon.com", "+32 463 49 30 44", adres5,
				"http://www.amazon.com", BedrijfSectorEnum.ENERGY);
		B2BBedrijf tesla = new B2BBedrijf("Tesla, Inc.", "contact@tesla.com", "+32 463 49 30 44", adres6,
				"http://www.tesla.com", BedrijfSectorEnum.AUTOMOTIVE);
		B2BBedrijf facebook = new B2BBedrijf("Facebook, Inc.", "info@facebook.com", "+32 463 49 30 44", adres3,
				"http://www.facebook.com", BedrijfSectorEnum.SOCIALMEDIA);

		// gebruikers
		Gebruiker proximusLeverancier = GebruikerFactory.maakLeverancier("Proximus_Test_Leverancier", "wachtwoord",
				"email@email.com", proximus);
		Gebruiker microsoftLeverancier = GebruikerFactory.maakLeverancier("Microsoft_Test_Leverancier", "wachtwoord",
				"email@email.com", microsoft);
		Gebruiker leverancierAccount = GebruikerFactory.maakLeverancier("Leverancier_Test", "ww", "lev@test.com",
				microsoft);
		Gebruiker adminAccount = GebruikerFactory.maakAdministrator("Administrator_Test", "ww", "admin@test.com");

		// producten
		Product product1 = new Product("Router", new BigDecimal(55.99), 59);
		Product product2 = new Product("Switch", new BigDecimal(99.85), 20);
		Product product3 = new Product("CPU", new BigDecimal(145.45), 200);
		Product product4 = new Product("Access Point", new BigDecimal(75.50), 100);
		Product product5 = new Product("Ethernet Cable", new BigDecimal(5.99), 500);
		Product product6 = new Product("Network Adapter", new BigDecimal(19.99), 50);
		Product product7 = new Product("Server Rack", new BigDecimal(399.99), 25);
		Product product8 = new Product("Printer", new BigDecimal(199.99), 30);

		// bestelling 1 microsoft besteld bij proximus
		List<ProductInBestelling> producten = new ArrayList<ProductInBestelling>();
		B2BBestelling b2b = new B2BBestelling(microsoft, adres1, producten, proximus);
		ProductInBestelling productInBestelling1 = new ProductInBestelling(product1, 10, b2b);
		ProductInBestelling productInBestelling2 = new ProductInBestelling(product2, 5, b2b);
		ProductInBestelling productInBestelling3 = new ProductInBestelling(product3, 7, b2b);
		ProductInBestelling productInBestelling4 = new ProductInBestelling(product4, 2, b2b);
		producten.add(productInBestelling1);
		producten.add(productInBestelling2);
		producten.add(productInBestelling3);
		producten.add(productInBestelling4);
		Betaling betalingMicroProx = new Betaling(b2b, b2b.getTotaalBestellingbedragMetBTW());

		// bestelling 2 microsoft besteld bij proximus
		List<ProductInBestelling> producten2 = new ArrayList<ProductInBestelling>();
		B2BBestelling b2b2 = new B2BBestelling(microsoft, adres1, producten2, proximus);
		ProductInBestelling productInBestelling5 = new ProductInBestelling(product5, 3, b2b2);
		ProductInBestelling productInBestelling6 = new ProductInBestelling(product6, 8, b2b2);
		ProductInBestelling productInBestelling7 = new ProductInBestelling(product7, 5, b2b2);
		ProductInBestelling productInBestelling8 = new ProductInBestelling(product8, 1, b2b2);
		producten2.add(productInBestelling5);
		producten2.add(productInBestelling6);
		producten2.add(productInBestelling7);
		producten2.add(productInBestelling8);
		Betaling betalingMicroProx2 = new Betaling(b2b2,
				b2b2.getTotaalBestellingbedragExclBTW().subtract(new BigDecimal(200)));

		// bestelling 3 apple besteld bij proximus
		List<ProductInBestelling> producten3 = new ArrayList<ProductInBestelling>();
		B2BBestelling b2b3 = new B2BBestelling(apple, adres1, producten3, proximus);
		ProductInBestelling productInBestelling9 = new ProductInBestelling(product1, 5, b2b3);
		ProductInBestelling productInBestelling10 = new ProductInBestelling(product2, 3, b2b3);
		producten3.add(productInBestelling9);
		producten3.add(productInBestelling10);
		Betaling betalingAppProx = new Betaling(b2b3, b2b3.getTotaalBestellingbedragExclBTW());
		// bestelling 4 tesla besteld bij proximus
		List<ProductInBestelling> producten4 = new ArrayList<ProductInBestelling>();
		B2BBestelling b2b4 = new B2BBestelling(tesla, adres1, producten4, proximus);
		ProductInBestelling productInBestelling11 = new ProductInBestelling(product3, 8, b2b4);
		ProductInBestelling productInBestelling12 = new ProductInBestelling(product4, 4, b2b4);
		producten4.add(productInBestelling11);
		producten4.add(productInBestelling12);
		Betaling betalingTesProx = new Betaling(b2b4, b2b4.getTotaalBestellingbedragExclBTW().subtract(new BigDecimal(219)));

		// bestelling 5 facebook besteld bij proximus
		List<ProductInBestelling> producten5 = new ArrayList<ProductInBestelling>();
		B2BBestelling b2b5 = new B2BBestelling(facebook, adres1, producten5, proximus);
		ProductInBestelling productInBestelling13 = new ProductInBestelling(product5, 2, b2b5);
		ProductInBestelling productInBestelling14 = new ProductInBestelling(product6, 6, b2b5);
		producten5.add(productInBestelling13);
		producten5.add(productInBestelling14);
		Betaling betalingFaceProx = new Betaling(b2b5, b2b5.getTotaalBestellingbedragExclBTW());

		// Bestelling 6: Google besteld bij Microsoft
		List<ProductInBestelling> producten6 = new ArrayList<>();
		B2BBestelling b2b6 = new B2BBestelling(google, adres2, producten6, microsoft);
		ProductInBestelling productInBestelling15 = new ProductInBestelling(product1, 15, b2b6);
		ProductInBestelling productInBestelling16 = new ProductInBestelling(product2, 10, b2b6);
		producten6.add(productInBestelling15);
		producten6.add(productInBestelling16);
		Betaling betalingGoogMicro = new Betaling(b2b6, b2b6.getTotaalBestellingbedragExclBTW());

		// Bestelling 7: Amazon besteld bij Microsoft
		List<ProductInBestelling> producten7 = new ArrayList<>();
		B2BBestelling b2b7 = new B2BBestelling(amazon, adres2, producten7, microsoft);
		ProductInBestelling productInBestelling17 = new ProductInBestelling(product3, 20, b2b7);
		ProductInBestelling productInBestelling18 = new ProductInBestelling(product4, 5, b2b7);
		producten7.add(productInBestelling17);
		producten7.add(productInBestelling18);
		Betaling betalingAmaMicro = new Betaling(b2b7, b2b7.getTotaalBestellingbedragExclBTW());

		// Bestelling 8: Tesla besteld bij Microsoft
		List<ProductInBestelling> producten8 = new ArrayList<>();
		B2BBestelling b2b8 = new B2BBestelling(tesla, adres2, producten8, microsoft);
		ProductInBestelling productInBestelling19 = new ProductInBestelling(product5, 8, b2b8);
		ProductInBestelling productInBestelling20 = new ProductInBestelling(product6, 12, b2b8);
		producten8.add(productInBestelling19);
		producten8.add(productInBestelling20);
		Betaling betalingTesMicro = new Betaling(b2b8, b2b8.getTotaalBestellingbedragExclBTW());

		// Bestelling 9: Facebook besteld bij Microsoft
		List<ProductInBestelling> producten9 = new ArrayList<>();
		B2BBestelling b2b9 = new B2BBestelling(facebook, adres2, producten9, microsoft);
		ProductInBestelling productInBestelling21 = new ProductInBestelling(product7, 3, b2b9);
		ProductInBestelling productInBestelling22 = new ProductInBestelling(product8, 6, b2b9);
		producten9.add(productInBestelling21);
		producten9.add(productInBestelling22);
		Betaling betalingFaceMicro = new Betaling(b2b9, b2b9.getTotaalBestellingbedragExclBTW().subtract(new BigDecimal(340)));

		entityManager.getTransaction().begin();
		// persisteren producten
		entityManager.persist(product1);
		entityManager.persist(product2);
		entityManager.persist(product3);
		entityManager.persist(product4);
		entityManager.persist(product5);
		entityManager.persist(product6);
		entityManager.persist(product7);
		entityManager.persist(product8);

		// persisteren producten in bestelling
		entityManager.persist(productInBestelling1);
		entityManager.persist(productInBestelling2);
		entityManager.persist(productInBestelling3);
		entityManager.persist(productInBestelling4);
		entityManager.persist(productInBestelling5);
		entityManager.persist(productInBestelling6);
		entityManager.persist(productInBestelling7);
		entityManager.persist(productInBestelling8);
		entityManager.persist(productInBestelling9);
		entityManager.persist(productInBestelling10);
		entityManager.persist(productInBestelling11);
		entityManager.persist(productInBestelling12);
		entityManager.persist(productInBestelling13);
		entityManager.persist(productInBestelling14);
		entityManager.persist(productInBestelling15);
		entityManager.persist(productInBestelling16);
		entityManager.persist(productInBestelling17);
		entityManager.persist(productInBestelling18);
		entityManager.persist(productInBestelling19);
		entityManager.persist(productInBestelling20);
		entityManager.persist(productInBestelling21);
		entityManager.persist(productInBestelling22);

		// persisteren bedrijven
		entityManager.persist(proximus);
		entityManager.persist(google);
		entityManager.persist(microsoft);
		entityManager.persist(apple);
		entityManager.persist(amazon);
		entityManager.persist(tesla);
		entityManager.persist(facebook);

		// persisteren leveranciers
		entityManager.persist(proximusLeverancier);
		entityManager.persist(microsoftLeverancier);

		// persisteren testleverancier
		entityManager.persist(leverancierAccount);
		// persisteren admin
		entityManager.persist(adminAccount);

		// persisteren bestellingen
		entityManager.persist(b2b);
		entityManager.persist(b2b2);
		entityManager.persist(b2b3);
		entityManager.persist(b2b4);
		entityManager.persist(b2b5);
		entityManager.persist(b2b6);
		entityManager.persist(b2b7);
		entityManager.persist(b2b8);
		entityManager.persist(b2b9);
		
		//toevoegen betalingen database
		entityManager.persist(betalingMicroProx);
		entityManager.persist(betalingMicroProx2);
		entityManager.persist(betalingAppProx);
		entityManager.persist(betalingTesProx);
		entityManager.persist(betalingFaceProx);
		entityManager.persist(betalingGoogMicro);
		entityManager.persist(betalingAmaMicro);
		entityManager.persist(betalingTesMicro);
		entityManager.persist(betalingFaceMicro);

		entityManager.getTransaction().commit();
	}
}
