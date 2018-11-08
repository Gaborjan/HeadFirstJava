import java.io.*;

public class JatekKarakter  implements Serializable {
	
	int ero;
	String tipus;
	String[] fegyverek;
	Jatekos szemely;
	
	public JatekKarakter(int ero, String tipus, String[] fegyverek, Jatekos szemely) {
		this.ero=ero;
		this.tipus=tipus;
		this.fegyverek=fegyverek;
		this.szemely=szemely;
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
		for (int i=0;i<fegyverek.length;i++) {
			flista=flista+fegyverek[i]+" ";
		}
		return flista;
	}
	
	
}
