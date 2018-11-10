import java.io.*;
import java.util.*;


public class JatekKarakter  implements Serializable {
	
	int ero;
	String tipus;
	String[] fegyverek;
	Jatekos szemely;
	transient int bonusz=0;
	
	public JatekKarakter(int ero, String tipus, String[] fegyverek, Jatekos szemely) {
		this.ero=ero;
		this.tipus=tipus;
		this.fegyverek=fegyverek;
		this.szemely=szemely;
		this.bonusz=generateBonusz();
	}

	public int getEro() {
		return ero;
	}

	public void setEro(int ero) {
		this.ero = ero;
	}

	public String getTipus() {
		return tipus;
	}

	public String getFegyverek() {
		String flista="";
		flista=Arrays.toString(this.fegyverek);
		return flista;
	}
	
	public int getBonusz() {
		return this.bonusz;
	}
	
	public void setBonusz() {
		this.bonusz=generateBonusz();
	}
	
	public String toString() {
		String szoveg="";
		szoveg=	"Játékos neve     :"+this.szemely.getNev()+"\n"+
					"Játékos kora     :"+this.szemely.getKor()+"\n"+
					"Játékos egyenlege:"+String.format("%,8.2f HUF", this.szemely.getEgyenleg())+"\n"+
					"Játékos típusa   :"+this.tipus+"\n"+
					"Játékos ereje    :"+this.ero+"\n"+
					"Játékos fegyverei:"+this.getFegyverek()+"\n"+
					"Játékos bónusza  :"+this.bonusz+"\n"+
					"---------------------------------------------------------------------------\n";
		return szoveg;
		
	
	}
	
	public static int generateBonusz() {
		Random rand = new Random();
		return rand.nextInt(10) + 1;
	}
}
