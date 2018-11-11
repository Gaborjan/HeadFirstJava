/**
 * @author Gábor_1
 */
package serializable_gyakorlo_pelda;

import java.io.Serializable;

/**
 * A {@code Jatekos} objektum egy valós személyt reprezentál, akit hozzá tudunk
 * kötni egy játékkarakterhez. Az osztály sorosítható, mivel értékeit fájlba is
 * szeretnénk írni.
 * 
 * @version 1.00 2018. november
 * @author Jánvári Gábor
 */
public class Jatekos implements Serializable {
	/**
	 * Nyilvántartjuk hány játékosunk van.
	 */
	private static int jatekosDb = 0;
	/** A játékos neve. */
	private String nev = "";
	/** A játékos kora. */
	private int kor = 0;
	/** A játékos egyenlege, forintban. */
	private double egyenleg = 0;

	/**
	 * A Jatékos objektum konstruktora
	 * 
	 * @param nev      A játékos neve.
	 * @param kor      A játékos kora.
	 * @param egyenleg A játékos egyenlege, forintban.
	 */
	public Jatekos(String nev, int kor, double egyenleg) {
		this.nev = nev;
		this.kor = kor;
		this.egyenleg = egyenleg;
		jatekosDb++;
	}

	/**
	 * Visszaadja a játékos egyenlegét.
	 * 
	 * @return A játékos egyenlege
	 */
	public double getEgyenleg() {
		return egyenleg;
	}

	/**
	 * A játékos egyenlegét állíthatjuk be vele.
	 * 
	 * @param egyenleg Az egyenleg, amit be szeretnénk állítani.
	 */
	public void setEgyenleg(double egyenleg) {
		this.egyenleg = egyenleg;
	}

	/**
	 * Visszaadja hány játékos objektumot hoztunk létre.
	 * 
	 * @return A létrehozott játékosok száma.
	 */
	public static int getJatekosDb() {
		return jatekosDb;
	}

	/**
	 * A játékos nevét adja vissza.
	 * 
	 * @return A játékos neve.
	 */
	public String getNev() {
		return nev;
	}

	/**
	 * A játékos korát adja vissza.
	 * 
	 * @return A játékos kora.
	 */
	public int getKor() {
		return kor;
	}
}
