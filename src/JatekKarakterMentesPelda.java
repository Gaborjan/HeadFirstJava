import java.io.*;
import java.util.*;

public class JatekKarakterMentesPelda {

	public static void main(String[] args) {
		ArrayList<JatekKarakter> JatekKarakterek = new ArrayList<JatekKarakter>(); 
		JatekKarakterek.add(new JatekKarakter(5, "Tünde", new String[] {"íj", "kard","varázspor"}, new Jatekos("Tibor",21,9450.0)));
		JatekKarakterek.add(new JatekKarakter(7, "Troll", new String[] {"csupasz kéz", "nagy fejsze"}, new Jatekos("Gábor",30,5000.0)));
		JatekKarakterek.add(new JatekKarakter(4, "Mágus", new String[] {"varázsigék", "láthatatlanság"}, new Jatekos("Alexandra",19,10154.5)));
		System.out.println("A 'Jatekos' osztály 'jatekosDb' statikus változó értéke: " +Jatekos.getJatekosDb());
		System.out.println("A játékosok adatai mentés előtt:\n");
		for (JatekKarakter e : JatekKarakterek) {
			System.out.println(e.toString());
		}
		try {
			ObjectOutputStream kiStream = new ObjectOutputStream(new FileOutputStream("Játékosok_adatai.ser"));
			for (JatekKarakter e : JatekKarakterek) {
				kiStream.writeObject(e);
			}
			kiStream.writeObject(null);
			kiStream.close();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		JatekKarakterek.clear();
		System.out.println("Az adatok fájlba kerültek, a tárolásra használt ArraList minden eleme törölve, elemszám: "+JatekKarakterek.size()+"\n");
		
		try {
			ObjectInputStream beStream = new ObjectInputStream(new FileInputStream("Játékosok_adatai.ser"));
			JatekKarakter e;
			e = (JatekKarakter) beStream.readObject();
			while (e != null) {
				JatekKarakterek.add(e);
				e = (JatekKarakter) beStream.readObject();
			}
			beStream.close();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex) {
			ex.getMessage();
		}
		
		System.out.println("A játékosok adatai amiket beolvastunk a fájlból: \n");
		for (JatekKarakter e : JatekKarakterek) {
			e.setBonusz();
			System.out.println(e);
		}
		System.out.println("A 'Jatekos' osztály 'jatekosDb' statikus változó értéke: " +Jatekos.getJatekosDb());
	}

}
