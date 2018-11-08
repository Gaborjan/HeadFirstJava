
public class Jatekos {
	
	static int jatekosDb=0;
	
	String nev="";
	int kor=0;
	double egyenleg=0;
	
	public Jatekos(String nev, int kor, double egyenleg) {
		this.nev=nev;
		this.kor=kor;
		this.egyenleg=egyenleg;
		jatekosDb++;
	}

	public double getEgyenleg() {
		return egyenleg;
	}

	public void setEgyenleg(double egyenleg) {
		this.egyenleg = egyenleg;
	}

	public static int getJatekosDb() {
		return jatekosDb;
	}

	public String getNev() {
		return nev;
	}

	public int getKor() {
		return kor;
	}
}
