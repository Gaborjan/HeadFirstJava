/**
 * @author Gábor_1
 */

package serializable_gyakorlo_pelda;

import java.io.*;
import java.util.*;

/**
 * A {@code JatekKarakter} osztály játék karaktereket valósít meg. A játék
 * karakter egy kitalált szerep. A játék karakterhez hozzákapcsolunk egy valós
 * személyt is, egy {@link serializable_gyakorlo_pelda.Jatekos} objektumot. Az
 * osztály sorosítható, hogy az objektumok fájlba írhatóak legyenek.
 * 
 * 
 * @version 1.00 2018. november
 * @author Jánvári Gábor
 */

public class JatekKarakter implements Serializable {
	/** A karakter ereje. */
	private int ero;
	/** A karakter típusa, pl. tünde, varázsló, stb. */
	private String tipus;
	/** A karakter milyen fegyverekkel rendelkezik? */
	private String[] fegyverek;
	/**
	 * A karakterhez tartozó valós személy, amelyet a Jatekos osztály valósít meg.
	 */
	private Jatekos szemely;
	/**
	 * Minden játék elején generálunk egy bónuszt. Ezt a mezőt nem menjtük a fáljba
	 * ('transient'), mert minden játék kezdetekor újból beállítjuk a bónuszt
	 * véletlenszerűen.
	 */
	private transient int bonusz = 0;

	/**
	 * A {@code JatekKarakter} konstruktora.
	 * 
	 * @param ero       A karakter ereje.
	 * @param tipus     A karakter típusa.
	 * @param fegyverek A karakter fegyverei, tömbként.
	 * @param szemely   A karakterhez tartozó valós személy, amely egy
	 *                  {@link serializable_gyakorlo_pelda.Jatekos} objektumra
	 *                  mutat.
	 */
	public JatekKarakter(int ero, String tipus, String[] fegyverek, Jatekos szemely) {
		this.ero = ero;
		this.tipus = tipus;
		this.fegyverek = fegyverek;
		this.szemely = szemely;
		this.bonusz = generateBonusz(); // Generálunk egy véletlenszerű bónusz értéket./
	}

	/**
	 * A karekter aktuális erejét adja vissza.
	 * 
	 * @return A karakter ereje.
	 */
	public int getEro() {
		return ero;
	}

	/**
	 * A karakter erejét állíthatjuk be.
	 * 
	 * @param ero A beállítani kívánt erő.
	 */
	public void setEro(int ero) {
		this.ero = ero;
	}

	/**
	 * A karakter típusát adja vissza.
	 * 
	 * @return A karakter típusa.
	 */
	public String getTipus() {
		return tipus;
	}

	/**
	 * A karakter aktuálisan birtokolt fegyvereit adja vissza, stringként, szögletes
	 * zárójelek között, vesszővel elválasztva.
	 * 
	 * @return A fegyverek szringként, szögletes zárojelek között, vesszővel
	 *         elválasztva.
	 */
	public String getFegyverek() {
		String flista = "";
		flista = Arrays.toString(this.fegyverek);
		return flista;
	}

	/**
	 * A karakter aktuális bónuszát adja vissza.
	 * 
	 * @return A karakter bónusza.
	 */
	public int getBonusz() {
		return this.bonusz;
	}

	/**
	 * A karakter bónuszát állíthatjuk be a metódussal.
	 */

	public void setBonusz() {
		this.bonusz = generateBonusz();
	}

	/**
	 * A karakter adatait adja vissza, stringként, több sorba tördelve és igazítva.
	 * 
	 * @return A játékos adatai sztringként, több sorba tördelve és igazítva.
	 */
	public String toString() {
		String szoveg = "";
		szoveg = "Játékos neve     :" + this.szemely.getNev() + "\n" + "Játékos kora     :" + this.szemely.getKor()
				+ "\n" + "Játékos egyenlege:" + String.format("%,8.2f HUF", this.szemely.getEgyenleg()) + "\n"
				+ "Játékos típusa   :" + this.tipus + "\n" + "Játékos ereje    :" + this.ero + "\n"
				+ "Játékos fegyverei:" + this.getFegyverek() + "\n" + "Játékos bónusza  :" + this.bonusz + "\n"
				+ "---------------------------------------------------------------------------\n";
		return szoveg;

	}

	/**
	 * A karakter bónuszát generáló metódus, segét metódus.
	 * 
	 * @return Egy véletlen szám 1 és 10 közötti értékkel.
	 */
	private static int generateBonusz() {
		Random rand = new Random();
		return rand.nextInt(10) + 1;
	}
}
