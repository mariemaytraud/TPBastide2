package ticketmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(10);
		machine.insertMoney(20);
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
	// S3 : on n'imprime pas le ticket si le montant inséré est insuffisant
	void printTicketNotWhenInsufficient() {
		machine.insertMoney(PRICE - 1);
		assertFalse(machine.printTicket(), "Le ticket ne devrait pas être imprimé si fonds insuffisants");
		// la balance inchangée et le total toujours à zéro
		assertEquals(PRICE - 1, machine.getBalance(), "La balance ne doit pas changer après tentative d'impression échouée");
		assertEquals(0, machine.getTotal(), "Le total ne doit pas être incrémenté si aucun ticket imprimé");
	}

	@Test
	// S4 & S5 & S6 : on imprime si le montant est suffisant; balance décrémentée et total mis à jour
	void printTicketWhenSufficientUpdatesState() {
		// Insérer un peu plus que le prix
		machine.insertMoney(PRICE + 20);
		// Avant impression, le total doit être 0
		assertEquals(0, machine.getTotal(), "Le total doit être 0 avant impression");
		assertTrue(machine.printTicket(), "Le ticket doit être imprimé si fonds suffisants");
		// Après impression, la balance doit être décrémentée du prix
		assertEquals(20, machine.getBalance(), "La balance doit être décrémentée du prix du ticket");
		// Le montant collecté doit être mis à jour
		assertEquals(PRICE, machine.getTotal(), "Le total doit être incrémenté du prix du ticket après impression");
	}

	@Test
	// S7 & S8 : refund rend la monnaie et remet la balance à zéro
	void refundReturnsMoneyAndResetsBalance() {
		machine.insertMoney(30);
		int refunded = machine.refund();
		assertEquals(30, refunded, "refund() doit retourner le montant inséré");
		assertEquals(0, machine.getBalance(), "refund() doit remettre la balance à zéro");
	}

	@Test
	// S9 : on ne peut pas insérer un montant négatif
	void cannotInsertNegativeAmount() {
		assertThrows(IllegalArgumentException.class, () -> machine.insertMoney(-10), "L'insertion d'un montant négatif doit lancer IllegalArgumentException");
	}

	@Test
	// S10 : on ne peut pas créer de machine avec un prix négatif
	void cannotCreateMachineWithNegativePrice() {
		assertThrows(IllegalArgumentException.class, () -> new TicketMachine(-1), "La création d'une machine avec prix négatif doit lancer IllegalArgumentException");
	}

}
