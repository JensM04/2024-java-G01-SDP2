package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.B2BBedrijf;
import domain.B2BPortaal;
import domain.Bedrijf;
import repository.BedrijfDao;
import repository.GebruikerDao;

@ExtendWith(MockitoExtension.class)
class B2BPortaalTest {

	@Mock
	GebruikerDao gebruikerDao;

	@Mock
	BedrijfDao bedrijfDao;
//	@InjectMocks
//	B2BPortaal b2bPortaal;


	@Test
	void testGetAlleBedrijven_GeenBedrijven_LegeLijstNietNull() {
		List<B2BBedrijf> legeLijst = new ArrayList<>();
		Mockito.lenient().when(bedrijfDao.findAll()).thenReturn(legeLijst);

		B2BPortaal b2bPortaal = new B2BPortaal(bedrijfDao, gebruikerDao);

		List<Bedrijf> bedrijven = (List<Bedrijf>) b2bPortaal.getAlleBedrijven();

		assertNotNull(bedrijven);
		assertEquals(0, bedrijven.size());

		Mockito.verify(bedrijfDao).findAll();
	}

	@Test
	void testGetAlleBedrijven_3Bedrijven_UnmodifiableList() {
		List<B2BBedrijf> mockLijst = new ArrayList<>();
		mockLijst.add(Mockito.mock(B2BBedrijf.class));
		mockLijst.add(Mockito.mock(B2BBedrijf.class));
		mockLijst.add(Mockito.mock(B2BBedrijf.class));
		B2BBedrijf mockBedrijf = Mockito.mock(B2BBedrijf.class);

		Mockito.lenient().when(bedrijfDao.findAll()).thenReturn(mockLijst);

		B2BPortaal b2bPortaal = new B2BPortaal(bedrijfDao, gebruikerDao);

		
		List<Bedrijf> bedrijven = (List<Bedrijf>) b2bPortaal.getAlleBedrijven();
		assertNotNull(bedrijven);
		assertEquals(3, bedrijven.size());
		assertThrows(UnsupportedOperationException.class, () -> bedrijven.add(mockBedrijf));
		assertThrows(UnsupportedOperationException.class, () -> bedrijven.remove(0));
	}

	@Test
	// TODO GeenBedrijfObject maken?
	void testGetBedrijf_BedrijfBestaatNiet_Null() {
		List<B2BBedrijf> mockLijst = new ArrayList<>();
		for(int i = 0; i < 3; i++) {
			B2BBedrijf mock = Mockito.mock(B2BBedrijf.class);
			Mockito.lenient().when(mock.getId()).thenReturn((long) (i+1));
			mockLijst.add(mock);
		}

		Mockito.when(bedrijfDao.findAll()).thenReturn(mockLijst);

		B2BPortaal b2bPortaal = new B2BPortaal(bedrijfDao, gebruikerDao);

		B2BBedrijf bedrijf = b2bPortaal.getBedrijfById(-1);
		assertNull(bedrijf);
	}

	// TODO -> test bedrijfBestaatWel
	@Test
	void testGetBedrijf_BedrijfBestaatWel_getBedrijf() {
		List<B2BBedrijf> mockLijst = new ArrayList<>();
		for(int i = 0; i < 100; i++) {
			B2BBedrijf mock = Mockito.mock(B2BBedrijf.class);
			Mockito.lenient().when(mock.getId()).thenReturn((long) (i+1));
			mockLijst.add(mock);
		}

		Mockito.when(bedrijfDao.findAll()).thenReturn(mockLijst);

		B2BPortaal b2bPortaal = new B2BPortaal(bedrijfDao, gebruikerDao);

		for(int i = 100; i > 0; i--) {
			B2BBedrijf bedrijf = b2bPortaal.getBedrijfById(i);
			assertNotNull(bedrijf);
			assertEquals(i, bedrijf.getId());
		}
	}

	// TODO -> test voegBedrijfToe -> null mag niet
	// TODO -> test voeg bedrijf toe -> geldig object
	// TODO -> test verwijder bedrijf -> dat niet bestaat
	// TODO -> test verwijder bedrijf -> dat wel bestaat
	// TODO -> wijzig bedrijf (persisteren)
}
