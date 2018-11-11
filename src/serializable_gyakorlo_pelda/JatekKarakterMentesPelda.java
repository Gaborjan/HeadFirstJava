/**
 * @author Gábor_1
 */

package serializable_gyakorlo_pelda;

import java.io.*;
import java.util.*;

/**
 * 
 * A {@code JatekKarakterMentesPelda} sorosítást megvalósító gyakorló példa.
 * Elsőként egy arraylist-et feltöltünk hátom jéték karakterrel, egyben
 * létrehozva 3 játékost is. Majd az tömblista elemeit konzolra írjuk. Ezután a
 * tömblista elemeit kiírjuk fájlba. A fájlba a bónusz mező értéke nem kerül
 * kiírásra. A jatekosDb statikus változó értéke 3, ez nem változik a fájlból
 * történő visszaolvasás után sem, mivel a JatekKarekter konstruktora nem fut
 * le. Ezt a változót kiírás után nullázni kell, majd olvasáskor mindig növelni
 * az értékét eggyel, amikor beolvastunk egy új objektumot. A kiírásnál az utolsó
 * tömblista elem után egy 'null' objektumot is kiírunk, ezzel jelezve, hogy itt
 * a fájl vége. Beolvasáskor ezt figyeljük, ha null értéket olvastunk be, akkor
 * nincs több objektum a fájlban. Beolvasás után minden karakternek beállítunk
 * egy új bónusz értéket, mivel ez nem került kiírásra a fájlba. Kiírásnál
 * látható, hogy a kezdeti bónusz értékek és a beolvasás utáni bónusz értékek
 * nem ugyanazok.
 * 
 * @version 1.00 2018. november
 * @author Jánvári Gábor
 */
public class JatekKarakterMentesPelda {

	public static void main(String[] args) {
		ArrayList<JatekKarakter> JatekKarakterek = new ArrayList<JatekKarakter>();
		JatekKarakterek.add(new JatekKarakter(5, "Tünde", new String[] { "íj", "kard", "varázspor" },
				new Jatekos("Tibor", 21, 9450.0)));
		JatekKarakterek.add(new JatekKarakter(7, "Troll", new String[] { "csupasz kéz", "nagy fejsze" },
				new Jatekos("Gábor", 30, 5000.0)));
		JatekKarakterek.add(new JatekKarakter(4, "Mágus", new String[] { "varázsigék", "láthatatlanság" },
				new Jatekos("Alexandra", 19, 10154.5)));

		System.out.println("A 'Jatekos' osztály 'jatekosDb' statikus változó értéke: " + Jatekos.getJatekosDb());
		System.out.println("A játékosok adatai mentés előtt:\n");

		for (JatekKarakter e : JatekKarakterek) {
			System.out.println(e.toString());
		}
		// Itt kezdődik a mentés.
		try {
			// Objektum kimeneti stream létrehozása és a fájl kimeneti stream
			// hozzáláncolása.
			ObjectOutputStream kiStream = new ObjectOutputStream(new FileOutputStream("Játékosok_adatai.ser"));
			// Végigmegyönk a tömblistán és minden elemét kiírjuk a fájlba./
			for (JatekKarakter e : JatekKarakterek) {
				kiStream.writeObject(e);
			}
			// Utolsó elemként egy null értékű objektumot is kiírunk, ezzel jelezve, hogy
			// vége a fájlnak.
			kiStream.writeObject(null);
			kiStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		// Töröljük a tömblista elemeit.
		JatekKarakterek.clear();

		System.out.println("Az adatok fájlba kerültek, a tárolásra használt ArrayList minden eleme törölve, elemszám: "
				+ JatekKarakterek.size() + "\n");
		// Itt kezdődik a beolvasás.
		try {
			// Bemeneti strem létrehozása és fájl bemeneti streamhez kapcsolása.
			ObjectInputStream beStream = new ObjectInputStream(new FileInputStream("Játékosok_adatai.ser"));
			JatekKarakter e;
			// Beolvassuk az első objektumot a fájlból
			e = (JatekKarakter) beStream.readObject();
			// Amíg nem null értéket olvasunk be a tömblistába felvesszük a beolvasott
			// objektumot, majd
			// beolvassuk a következő objektumot a fájlból.
			while (e != null) {
				JatekKarakterek.add(e);
				e = (JatekKarakter) beStream.readObject();
			}
			beStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.getMessage();
		}
		/*
		 * Mivel a bónusz mezőt nem írtuk ki a fájlba, most minden karakternek
		 * generálunk egy friss bónusz értéket. majd kiírjuk a sorban kövezkető karakter
		 * adatait.
		 */
		System.out.println("A játékosok adatai amiket beolvastunk a fájlból: \n");
		for (JatekKarakter e : JatekKarakterek) {
			e.setBonusz();
			System.out.println(e);
		}
		System.out.println("A 'Jatekos' osztály 'jatekosDb' statikus változó értéke: " + Jatekos.getJatekosDb());
	}

}
