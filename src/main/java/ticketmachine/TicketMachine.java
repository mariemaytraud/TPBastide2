package ticketmachine;

/**
 * TicketMachine modélise une machine à tickets basique qui délivre des tickets à tarif fixe.
 * Le prix d'un ticket est spécifié via le constructeur. C'est une machine basique au sens où
 * elle suppose que les utilisateurs insèrent suffisamment d'argent avant d'essayer d'imprimer un ticket.
 * Elle suppose aussi que les montants saisis sont raisonnables.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2006.03.30
 */
public class TicketMachine {
	// Le prix d'un ticket pour cette machine.
	private final int price;
	// Le montant d'argent inséré par un client jusqu'à présent.
	private int balance;
	// Le montant total collecté par cette machine.
	private int total;

	/**
	 * Crée une machine qui délivre des tickets du prix indiqué.
	 *
	 * @param ticketCost le prix d'un ticket, >= 0
	 */
	public TicketMachine(int ticketCost) {
		// Test de validité du paramètre
		// Le prix ne doit pas être négatif (0 est accepté)
		if (ticketCost < 0) {
			throw new IllegalArgumentException("Ticket price must not be negative");
		}
		price = ticketCost;
		balance = 0;
		total = 0;
	}

	/**
	 * Retourne le prix d'un ticket.
	 *
	 * @return le prix des tickets pour cette machine
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Retourne le montant total collecté par la machine.
	 *
	 * @return le montant total collecté par la machine.
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @return le montant d'argent déjà inséré pour le prochain ticket.
	 */
	public int getBalance() {
		return balance;
	}

	/**
	 * Reçoit un montant (en centimes) d'un client.
	 *
	 * @param amount le montant inséré, en centimes (positif)
	 * @throws IllegalArgumentException si le montant n'est pas positif
	 */
	public void insertMoney(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Cannot insert a negative amount");
		}
		balance = balance + amount;
	}

	/**
	 * Rend la monnaie au client.
	 *
	 * @return le montant remboursé
	 */
	public int refund() {
		int amountToReturn = balance;
		// Remet la balance à zéro et retourne le montant remboursé
		balance = 0;
		System.out.println("Je vous rends : " + amountToReturn + " centimes");
		return amountToReturn;
	}

	/**
	 * Imprime un ticket. Met à jour le total collecté et réduit la balance.
	 *
	 * @return vrai si le ticket a été imprimé, faux sinon
	 */
	public boolean printTicket() {
		// Vérifier si l'utilisateur a inséré suffisamment d'argent
		if (balance < price) {
			// Pas assez d'argent : ne pas imprimer, ne rien changer
			return false;
		}
		// Simule l'impression d'un ticket.
		System.out.println("##################");
		System.out.println("# The BlueJ Line");
		System.out.println("# Ticket");
		System.out.println("# " + price + " cents.");
		System.out.println("##################");
		System.out.println();
		// Mettre à jour les totaux et décrémenter la balance
		total = total + price;
		balance = balance - price;
		return true;
	}
}
