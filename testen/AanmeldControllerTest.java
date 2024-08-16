package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import controllers.AanmeldController;
import controllers.AdminController;
import controllers.LeverancierController;
import domain.Administrator;
import domain.B2BBedrijf;
import domain.GebruikerFactory;
import domain.Leverancier;
import exceptions.AanmeldException;
import exceptions.RegistreerGebruikerException;
import jakarta.persistence.EntityNotFoundException;
import repository.GebruikerDao;

@ExtendWith(MockitoExtension.class)
public class AanmeldControllerTest {
	@Mock
	private GebruikerDao gebruikerRepoMock;
	@InjectMocks
	private AanmeldController controller;

	private B2BBedrijf b;

	@BeforeEach
	void before() {
		b = Mockito.mock(B2BBedrijf.class);
	}

	@ParameterizedTest
	@NullAndEmptySource
	public void meldAanFoutieveGebruikersnaam_werptException(String naam) {
		Assertions.assertThrows(AanmeldException.class,() -> {controller.meldAan(naam, "geldigWachtwoord");});
	}

	@ParameterizedTest
	@NullAndEmptySource
	public void meldAanFoutiefWachtwoord_werptException(String ww) {
		Assertions.assertThrows(AanmeldException.class,() -> {controller.meldAan("geldigeGebruikersnaam", ww);});
	}

	@Test
	public void meldAanFouteCombinatie_werptException() throws RegistreerGebruikerException {
		B2BBedrijf b = Mockito.mock(B2BBedrijf.class);
		Leverancier test = GebruikerFactory.maakLeverancier("naam", "ww", "naam@gmail.com", b);
		Mockito.when(gebruikerRepoMock.zoekLeverancier("naam")).thenReturn(test);
		//Mockito.when(gebruikerRepoMock.zoekAdmin("naam")).thenThrow(new EntityNotFoundException());

		Assertions.assertThrows(AanmeldException.class, () -> {controller.meldAan("naam", "wachtwoord");});

		Mockito.verify(gebruikerRepoMock).zoekLeverancier("naam");
		//Mockito.verify(gebruikerRepoMock).zoekAdmin("naam");
	}

	@Test
	public void meldAanNietBestaandeGebruiker_werptException() {
		Mockito.when(gebruikerRepoMock.zoekLeverancier("bestaatNiet")).thenThrow(new EntityNotFoundException());
		Mockito.when(gebruikerRepoMock.zoekAdmin("bestaatNiet")).thenThrow(new EntityNotFoundException());

		Assertions.assertThrows(AanmeldException.class, () -> {controller.meldAan("bestaatNiet", "wachtwoord");});

		Mockito.verify(gebruikerRepoMock).zoekLeverancier("bestaatNiet");
		Mockito.verify(gebruikerRepoMock).zoekAdmin("bestaatNiet");
	}

	@Test
	public void meldAanMetGeldigeLeverancier() throws RegistreerGebruikerException, AanmeldException {
		B2BBedrijf b = Mockito.mock(B2BBedrijf.class);
		Leverancier l = GebruikerFactory.maakLeverancier("leverancier", "ww", "leverancier@gmail.com", b);

		Mockito.when(gebruikerRepoMock.zoekLeverancier("leverancier")).thenReturn(l);

		Assertions.assertInstanceOf(LeverancierController.class, controller.meldAan("leverancier", "ww"));

		Mockito.verify(gebruikerRepoMock).zoekLeverancier("leverancier");
	}

	@Test
	public void meldAanMetGeldigeAdministrator() throws RegistreerGebruikerException, AanmeldException {
		Administrator a = GebruikerFactory.maakAdministrator("admin", "wachtwoord", "admin@gmail.com");

		Mockito.when(gebruikerRepoMock.zoekLeverancier("administrator")).thenThrow(new EntityNotFoundException());
		Mockito.when(gebruikerRepoMock.zoekAdmin("administrator")).thenReturn(a);

		Assertions.assertInstanceOf(AdminController.class, controller.meldAan("administrator", "wachtwoord"));

		Mockito.verify(gebruikerRepoMock).zoekLeverancier("administrator");
		Mockito.verify(gebruikerRepoMock).zoekAdmin("administrator");
	}

}
