import java.util.ArrayList;
import java.util.*;
import java.io.*;


class Oblig4 {

    public static void main(String[] args) {
	new Test();
	UserInterface use = new UserInterface();
	use.lesFil();
	
    }
}

abstract class Person{
    private String navn;
    private int nummer;
    static int antallPersoner = 1; //
    private YngsteFoerstReseptListe mineResepter = new YngsteFoerstReseptListe();
    
    Person(String navn){
	System.out.println(navn);
	this.navn = navn;
	nummer = antallPersoner++;
    }

    public void add(Resept res){
	mineResepter.add(res);
    }

    public String getName() {
	return navn;
    }
    public Resept getResept(int reseptNr) {
	return mineResepter.finnResept(reseptNr);
    }
    public void skriv() {
	System.out.println("Navn: " + navn);
	System.out.println("Personnummer: " + nummer);
    }

    public void skrivResepter() {
	int teller = 0;
	
	for (Resept r : mineResepter) {
	     if (r.blaa() && r.gyldig()) {
	 	teller++;
	 	r.skriv();
	     }
	 }
	System.out.println("Gyldige blaa resepter: " + teller);
    }

    public int antVaneLegemidler(){
	int teller = 0;
	
	for (Resept r : mineResepter){
	    if((r.getLegemiddel() instanceof LegemiddelB)&& r.gyldig()){
		teller++;
	    }
	}
	return teller;
    }
}

class Kvinne extends Person{
    Kvinne(String navn){
	super(navn);
    }
    
}


class Mann extends Person{
    Mann(String navn){
	super(navn);
    }    
}

class Lege implements Comparable<Lege>, Lik, Avtale { //) ENDRE HER SENERE
    private String navn;
    private EldsteFoerstReseptListe resepterSkrevet = new EldsteFoerstReseptListe();
    private int avtaleNr;

    Lege(String navn, int avtaleNr){
	this.navn = navn;
	this.avtaleNr = avtaleNr;
    }

    public String getNavn() {
	return navn;
    }

    public boolean samme(String navn) {
	return navn.equals(this.navn);
    }
    
    //returnerer 1 om denne legen kommer foer den andre, eller -1, eller 0 om de er like
    public int compareTo(Lege lege) {
	return lege.getNavn().compareToIgnoreCase(this.navn);
    }

    public void add(Resept res) {
	resepterSkrevet.add(res);
    }

    public void skriv() {
	System.out.println("Navn: " + navn);
	System.out.print("Avtale: ");
	if(avtaleNr == 0) {
	    System.out.println("ingen avtale");
	}
	else {
	    System.out.println(avtaleNr);
	}
	
    }

    public String getName() {
	return navn;
    }

    public boolean avtale() {
	if (avtaleNr == 0) {
	    return false;
	}
	return true;
    }

    public int antAMidler() {
	int teller = 0;

	for (Resept resept : resepterSkrevet) {
	    if (resept.getLegemiddel() instanceof LegemiddelAInjeksjon || resept.getLegemiddel() instanceof LegemiddelALiniment || resept.getLegemiddel() instanceof LegemiddelAPille) {
		teller++;
	    }
	}
	return teller;
    }
    
}

class SpesialistLege extends Lege {
    
    SpesialistLege(String navn,int avtaleNr) {
	super(navn,avtaleNr);
    }
}

interface Avtale {
}


interface Lik {
    public boolean samme(String navn);
}

class LegemiddelC {
     private String navn;
     private int pris;
     private int idNummer;
     
     static int antallLegemidler = 1;  //en mer
     
     LegemiddelC(String navn, int pris) {
	 this.navn = navn;
	 this.pris = pris;
	 idNummer = antallLegemidler++;
     }
     
     public int getPris() {
	 return pris;
     }

     public void skriv() {
	 System.out.println("Legemiddel: " + navn);
	 System.out.println("\tId nummer: " + idNummer);
     }


}

class LegemiddelCInjeksjon extends LegemiddelC implements Injeksjon {
    private double dose;

    LegemiddelCInjeksjon(String navn, int pris, double dose) {
	super(navn, pris);
	this.dose = dose;
    }

    public double getDose() {
	return dose;
    }
    
    public void skriv() {
	super.skriv();
	System.out.println("\tDose: " + dose);
     }
}
class LegemiddelAInjeksjon extends LegemiddelA implements Injeksjon {
    private double dose;

    LegemiddelAInjeksjon (String navn, int pris, int narko, double dose) {
	super(navn, pris, narko);
	this.dose = dose;
    }

    public double getDose() {
	return dose;
    }

    public void skriv() {
	super.skriv();
	System.out.println("\tDose: " + dose);
     }
}
class LegemiddelBInjeksjon extends LegemiddelB implements Injeksjon {
    private double dose;

    LegemiddelBInjeksjon (String navn, int pris, int vane, double dose) {
	super(navn, pris, vane);
	this.dose = dose;
    }
    public double getDose() {
	return dose;
    }

    public void skriv() {
	super.skriv();
	System.out.println("\tDose: " + dose);
     }
}

class LegemiddelCPille extends LegemiddelC implements Pille {
    private double antPiller;

    LegemiddelCPille(String navn, int pris, double antPiller) {
	super(navn, pris);
	this.antPiller = antPiller;	
    }

    public double getAntPiller() {
	return antPiller;
    }

    public void skriv() {
	super.skriv();
	System.out.println("\tAntall piller: " + antPiller);
     }
}
class LegemiddelAPille extends LegemiddelA implements Pille {
    private double antPiller;

    LegemiddelAPille (String navn, int pris, int narko, double antPiller) {
	super(navn, pris, narko);
	this.antPiller = antPiller;
    }
    public double getAntPiller() {
	return antPiller;
    }

    public void skriv() {
	super.skriv();
	System.out.println("\tAntall piller: " + antPiller);	
     }
}
class LegemiddelBPille extends LegemiddelB implements Pille {
    private double antPiller;

    LegemiddelBPille (String navn, int pris, int vane, double  antPiller) {
	super(navn, pris, vane);
	this.antPiller = antPiller;
    }
    public double getAntPiller() {
	return antPiller;
    }
    public void skriv() {
	super.skriv();
	System.out.println("\tAntall piller: " + antPiller);
     }
}

class LegemiddelCLiniment extends LegemiddelC implements Liniment {
    private double volum;

    LegemiddelCLiniment(String navn, int pris, double volum) {
	super(navn, pris);
	this.volum = volum;	
    }
    public double getVolum() {
	return volum;
    }

    public void skriv() {
	super.skriv();
	System.out.println("\tVolum: " + volum);
     }
}
class LegemiddelALiniment extends LegemiddelA implements Liniment {
    private double volum;

    LegemiddelALiniment (String navn, int pris, int narko, double volum) {
	super(navn, pris, narko);
	this.volum = volum;
    }
    public double getVolum() {
	return volum;
    }

    public void skriv() {
	super.skriv();
	System.out.println("\tVolum: " + volum);
    }
}
class LegemiddelBLiniment extends LegemiddelB implements Liniment {
    private double volum;

    LegemiddelBLiniment (String navn, int pris, int vane, double volum) {
	super(navn, pris, vane);
	this.volum = volum;
    }
    public double getVolum() {
	return volum;
    }
    
    public void skriv() {
	super.skriv();
	System.out.println("\tVolum: " + volum);
    } 
}

class LegemiddelB extends LegemiddelC {

    private int vanedannelsesGrad;

    LegemiddelB (String navn, int pris, int vane) {
	super(navn, pris);
	vanedannelsesGrad = vane;
    }

    public void skriv() {
	super.skriv();
	System.out.println("Vanedannelsesgrad: " + vanedannelsesGrad);
    }
}

class LegemiddelA extends LegemiddelC {

    private int narkotiskGrad;

    LegemiddelA (String navn, int pris, int narko) {
	super(navn, pris);
	narkotiskGrad = narko;
    }

    public void skriv() {
	super.skriv();
	System.out.println("Narkotisk grad: " + narkotiskGrad);
    }
}

interface Pille {    
    public double getAntPiller();
}

interface Liniment {  
    public double getVolum(); //i cm^3
}

interface Injeksjon {
    public double getDose();
}

class Resept {
    static int antResept = 1;
    private LegemiddelC medisin;
    private Lege lege;
    private int personNr;
    private int reit;
    private boolean blaa;
    private int idNummer;

    Resept(LegemiddelC medisin, Lege lege, int persNr, int tall, boolean b) {
	this.medisin = medisin;
	this.lege = lege;
	personNr = persNr;
	reit = tall;
	blaa = b;
	idNummer = antResept++;
    }

    public int getIdNummer() {
	return idNummer;
    }

    public int  getPersonNr() {
	return personNr;
    }

    public LegemiddelC brukLegemiddel() {
	if (reit > 0) {
	    reit--;
	    return medisin;
	}
	return null;
    }
    
    public LegemiddelC getLegemiddel() {
	return medisin;
    }

    public int getPris() {
	if (blaa) {
	    return 0;
	}
	return medisin.getPris();
    }

    public Lege getLege() {
	return lege;
    }

    public boolean gyldig() {
	if (reit > 0) {
	    return true;
	}
	return false;
    }
    public boolean blaa() {
	return blaa;
    }
    
    public void skriv() {
	System.out.println("Resept id: " + idNummer + "\t" + "Injeksjonsdoser: " + reit);
    }
}

interface AbstraktTabell<T> {
    public boolean add(int indeks, T objekt);

    public T finnObjekt(int indeks);

    public Iterator iterator();
	
}

interface AbstraktSortertEnkelListe<T extends Comparable & Lik>{
    public void add(T objekt);

    public T finnObjekt(String nokkel);

    public Iterator iterator();
}

class  Tabell<T> implements AbstraktTabell<T>, Iterable<T> {

    private T[] beholder;
    
    Tabell(int lengde){
	beholder = (T[]) new Object[lengde];
    }
    public boolean add(int indeks, T objekt){
	if (beholder.length - 1 >= indeks && beholder[indeks] == null){
		beholder[indeks] = objekt;
		return true;
	}

	if (beholder.length - 1 >= indeks && beholder[indeks] != null) return false;

	T[] nybeholder;
	nybeholder = (T[]) new Object[beholder.length*2];

	for (int i = 0; i < beholder.length; i++){
	    nybeholder[i] = beholder[i];
	}
	beholder = nybeholder;
	this.add(indeks, objekt);
	return true;
    }

    public T finnObjekt(int indeks) {
	if(beholder.length - 1 >= indeks){
	    return beholder[indeks];
	}
	return null;

    }

    public Iterator<T> iterator() {
	Iterator <T> it = new IteratorFactory();
	return it;
    }

    private class IteratorFactory<U> implements Iterator {
	private int teller = 0;
	private U[] elementer;
	
	IteratorFactory() {
	    elementer = (U[]) beholder;
	}

	public boolean hasNext() {
	    for(int i = teller; i < elementer.length; i++){
		if (elementer[i] !=  null) {
		    return true;
		}
	    }
	    return false;
	}
	
	public U next() {
	    for(int i = teller; i < elementer.length; i++){
		if (elementer[i] != null){
		    teller = i + 1;
		    return elementer[i];
		}
	    }
	    return null;
	}
	public void remove() throws UnsupportedOperationException {
	}
    }
}

class SortertEnkelListe<T extends Comparable & Lik> implements AbstraktSortertEnkelListe<T>, Iterable<T> {
    private Node start;
    private int teller = 0;
   

    public void add(T objekt){
	Node nynode = new Node(objekt);
	Node n;
	if (start == null) {
	    start = nynode;
	    return;
	}

	if (start.objekt.compareTo(objekt)<0){
	    nynode.neste = start;
	    start = nynode;
	    return;
	}

	for(n = start; n.neste != null; n = n.neste){
	    if (n.neste.objekt.compareTo(objekt)< 0){
		nynode.neste = n.neste;
		n.neste = nynode;
		teller++;
		return;
	    }
	}
		
	n.neste = nynode;
	teller++;
    }
	
	

    public T finnObjekt(String nokkel){
	for(Node n =start; n.neste != null; n = n.neste){
	    if (n.objekt.samme(nokkel)){
		return n.objekt;
	    }
	}
	return null;
    }

    public Iterator<T> iterator() {
	Iterator<T> it = new Iteratorfactory2();
	return it;
    }

    class Iteratorfactory2 implements Iterator<T>{
	Node posisjonnode = start;

	public boolean hasNext(){
	    if(posisjonnode.neste != null)return true;
	    return false;
	}

	public T next(){
	    if(hasNext()){
		T t = posisjonnode.objekt;
		posisjonnode = posisjonnode.neste;
		return t;
	    }
	    return null;
	}

	public void remove(){}
    }

    private class Node{
	T objekt;
	Node neste;
	Node(T objekt){
	    this.objekt = objekt;
	}

    }
}

class EnkelReseptListe implements Iterable<Resept> {
    
    ReseptNode start;
    ReseptNode siste;

    public void add(Resept res) {
	ReseptNode nyNode = new ReseptNode(res);

	if (start == null) {
	    start = nyNode;
	    siste = nyNode;
	    return;
	}
	siste.neste = nyNode;
	siste = nyNode;
    }

    public Resept finnResept(int i) {
	for (ReseptNode n = start; n.neste != null; n = n.neste) {
	    if (n.resept.getIdNummer() == i) {
		return n.resept;
	    }
	}
	return null; // FIX THIS LATER (Exception)
    }

    public Iterator<Resept> iterator() {
	Iterator<Resept> it = new IteratorFabrikk(start);
	return it;
    }

    class ReseptNode {
	Resept resept;
	ReseptNode neste;

	ReseptNode(Resept r) {
	    resept = r;
	}
    }
    class IteratorFabrikk implements Iterator<Resept> {
	ReseptNode posisjonsNode;

	IteratorFabrikk(ReseptNode r) {
	    posisjonsNode = r;
	}
	
	public boolean hasNext() {
	    if (posisjonsNode == null) {
		return false;
	    }
	    return true;
	}

	public Resept next() {
	    Resept r  = posisjonsNode.resept;
	    posisjonsNode = posisjonsNode.neste;
	    return r;
	}

	public void remove() {
	}
    }
}

class EldsteFoerstReseptListe extends EnkelReseptListe { 
} 

class YngsteFoerstReseptListe extends EnkelReseptListe {

    public void add(Resept res) {
	ReseptNode nyNode = new ReseptNode(res);
	
	if (start == null) {
	    start = nyNode;
	    siste = nyNode;
	    return;
	}
	nyNode.neste = start;
	start = nyNode;
	
    }
}	

class Test {

    Test() {
	Tabell<String> tabell = new Tabell<String>(4);
	
	System.out.println(tabell.add(0, "1"));
	System.out.println(tabell.add(1, "2"));
	System.out.println(tabell.add(2, "3"));
	System.out.println(tabell.add(3, "4"));
	System.out.println(tabell.add(100,"100"));

    }
}

class UserInterface{
    Tabell<LegemiddelC> legemiddelTabell = new Tabell<LegemiddelC>(10);
    SortertEnkelListe<Lege> legeListe = new SortertEnkelListe<Lege>();
    Tabell<Person> personTabell = new Tabell<Person>(10);
    EnkelReseptListe reseptListe = new EnkelReseptListe();
    Scanner scan = new Scanner(System.in);
    Scanner les;
    
    public void controll(){
	int valg = 0;

	while(valg != 5){
	    System.out.println("1.Opprette og legge inne et nytt legemiddel.");
	    System.out.println("2.Opprette og legge inne en ny lege.");
	    System.out.println("3.Opprette og legge inne en ny person.");
	    System.out.println("4.Opprette og legge inne en ny resept.");
	    System.out.println("Gi et nummer:");
	    valg = scan.nextInt();
	    switch(valg) {
	    case 1:
		nyLegemiddel();
		break;
	    case 2:
		nyLege();
		break;
	    case 3:
		nyPerson();
		break;
	    case 4:
		nyResept();
		break;
	    case 5:
		break;
	    }
	}
    }
    public void nyLegemiddel(){
	String typeabc = null;
	String type = null;
	String navn;
	int pris;
	int grad;
	double volum;
	LegemiddelC nyLegemiddel = null;


	System.out.println("Gi navn av Legemiddelet: ");
	navn = scan.next();
	System.out.println("Gi pris av Legemiddelet: ");
	pris = scan.nextInt();
	
	while(!(typeabc.equals("A")||typeabc.equals("B")||typeabc.equals("C"))){
	    System.out.println("Gi type av Legemiddelet: A, B, eller C ");
	    typeabc = scan.next();
	    switch(typeabc){
	    case "A":
		while(!(type.equals("Injeksjon")||type.equals("Liniment")||type.equals("Pille"))){
		    System.out.println("Gi pakkingform av Legemiddelet: Injeksjon,Liniment,eller Pille");
		    type = scan.next();
		    switch(type){
		    case "Injeksjon":
			System.out.println("Gi narkotiskgrad:");
			grad = scan.nextInt();
			System.out.println("Gi dose:");
			volum = scan.nextDouble();
			nyLegemiddel = new LegemiddelAInjeksjon(navn,pris,grad, volum);
			legemiddelTabell.add(LegemiddelC.antallLegemidler,nyLegemiddel);
			break;
		    case"Liniment":
			System.out.println("Gi narkotiskgrad:");
			grad = scan.nextInt();
			System.out.println("Gi volum:");
			volum = scan.nextDouble();
			nyLegemiddel = new LegemiddelALiniment(navn,pris,grad, volum);
			legemiddelTabell.add(LegemiddelC.antallLegemidler,nyLegemiddel);
			break;
		    case"Pille":
			System.out.println("Gi narkotiskgrad:");
			grad = scan.nextInt();
			System.out.println("Gi Antallpiller:");
			volum = scan.nextDouble();
			nyLegemiddel = new LegemiddelAPille(navn,pris,grad, volum);
			legemiddelTabell.add(LegemiddelC.antallLegemidler,nyLegemiddel);
			break;
		    }
		}
		
	    case "B":
		while(!(type.equals("Injeksjon")||type.equals("Liniment")||type.equals("Pille"))){
		    System.out.println("Gi pakkingform av Legemiddelet: Injeksjon,Liniment,eller Pille");
		    type = scan.next();
		    switch(type){
		    case "Injeksjon":
			System.out.println("Gi vanedannelsesgrad:");
			grad = scan.nextInt();
			System.out.println("Gi dose:");
			volum = scan.nextDouble();
			nyLegemiddel = new LegemiddelBInjeksjon(navn,pris,grad, volum);
			legemiddelTabell.add(LegemiddelC.antallLegemidler,nyLegemiddel);
			break;
		    case"Liniment":
			System.out.println("Gi vanedannelsesgrad:");
			grad = scan.nextInt();
			System.out.println("Gi volum:");
			volum = scan.nextDouble();
			nyLegemiddel = new LegemiddelBLiniment(navn,pris,grad, volum);
			legemiddelTabell.add(LegemiddelC.antallLegemidler,nyLegemiddel);
			break;
		    case"Pille":
			System.out.println("Gi vanedannelsesgrad:");
			grad = scan.nextInt();
			System.out.println("Gi Antallpiller:");
			volum = scan.nextDouble();
			nyLegemiddel = new LegemiddelBPille(navn,pris,grad, volum);
			legemiddelTabell.add(LegemiddelC.antallLegemidler,nyLegemiddel);
			break;
		    }
		}
		
	    case "C":
		while(!(type.equals("Injeksjon")||type.equals("Liniment")||type.equals("Pille"))){
		    System.out.println("Gi pakkingform av Legemiddelet: Injeksjon,Liniment,eller Pille");
		    type = scan.next();
		    switch(type){
		    case "Injeksjon":
			System.out.println("Gi dose:");
			volum = scan.nextDouble();
			nyLegemiddel = new LegemiddelCInjeksjon(navn,pris,volum);
			legemiddelTabell.add(LegemiddelC.antallLegemidler,nyLegemiddel);
			break;
		    case"Liniment":
			System.out.println("Gi volum:");
			volum = scan.nextDouble();
			nyLegemiddel = new LegemiddelCLiniment(navn,pris,volum);
			legemiddelTabell.add(LegemiddelC.antallLegemidler,nyLegemiddel);
			break;
		    case"Pille":
			System.out.println("Gi Antallpiller:");
			volum = scan.nextDouble();
			nyLegemiddel = new LegemiddelCPille(navn,pris,volum);
			legemiddelTabell.add(LegemiddelC.antallLegemidler,nyLegemiddel);
			break;
		    }
		}
	    }
	}
    }
    public void nyLege(){
	String navn;
	int avtaleNr = 0;
	boolean spesialistLege = false;
	boolean avtaleLege = false;
	String yn = "";
	String jn = "";
	Lege nyLege = null;

	System.out.println("Gi legens navn:");
	navn = scan.next(); 

	System.out.println("Er legen spesialistlege? y/n");
	yn = scan.next();
	if(yn.equals("y"))spesialistLege = true;

	System.out.println("Er legen avtalelege? y/n");
	jn = scan.next();
	if(jn.equals("y"))avtaleLege = true;

	if (avtaleLege){
	    System.out.println("Gi legens avtalenummer:");
	    avtaleNr = scan.nextInt();
	}

	if (spesialistLege){
	    if (avtaleLege){
		nyLege = new SpesialistLegeMedAvtale(navn, avtaleNr);
	    }else if (!avtaleLege){
		nyLege = new SpesialistLege(navn,avtaleNr);
	    }
	}else if (!spesialistLege){ 
	    if (avtaleLege){
		nyLege = new LegeMedAvtale(navn, avtaleNr);
	    }else if (!avtaleLege){
		nyLege = new Lege(navn,avtaleNr);
	    }
	}

	legeListe.add(nyLege);
    }

    public void nyPerson() {
	System.out.println("Navn: ");
	String navn = scan.next();
	
	if (jaNei("Mann?")) {
	    personTabell.add(Person.antallPersoner, new Mann(navn));
	}
	else {
	    personTabell.add(Person.antallPersoner, new Kvinne(navn));
	}
    }

    public void nyResept() {
	Resept nyResept = null;
	
	System.out.println("Legemiddel nummer: ");
	int nr = scan.nextInt();
	LegemiddelC legemiddel = legemiddelTabell.finnObjekt(nr);

	System.out.println("Legens navn: ");
	String legeNavn = scan.next();
	Lege lege = legeListe.finnObjekt(legeNavn);

	System.out.println("Personnummer: ");
	int  persNr = scan.nextInt();

	System.out.println("Antall: ");
	int reit = scan.nextInt();

	boolean blaa = jaNei("Blaatt resept?");	 

	nyResept = new Resept(legemiddel, lege, persNr, reit, blaa);
	reseptListe.add(nyResept);
	lege.add(nyResept);
	personTabell.finnObjekt(persNr).add(nyResept);
    }

    public boolean jaNei(String spoersmaal) {

	while (true) {
	    System.out.println(spoersmaal + " (y/n)");
	    String svar = scan.next();

	    if (svar.equals("y")) {
		return true;
	    }
	    if (svar.equals("n")) {
		return false;
	    }
	}
    }

    public void lesFil() {
	try {
	    les = new Scanner(new File("filnavn.txt"));
	}
	catch (Exception e) {
	    System.out.println("damn");
	}
	les.useDelimiter(",+|\\s+");

	les.nextLine();
	while (!les.hasNext("#")) {
	    String[] info = les.nextLine().split(", ");
	    if (info[2].equals("m")) {
		personTabell.add(Integer.valueOf(info[0]), new Mann(info[1]));
	    }
	    else {
		personTabell.add(Integer.valueOf(info[0]), new Kvinne(info[1]));
	    }
	}
	les.nextLine();
	les.nextLine();
	while (!les.hasNext("#")) {
	    String[] info = les.nextLine().split(", ");
	    String navn = info[1];
	    String form = info[2];
	    form = form.replaceFirst(".", String.valueOf(form.charAt(0)).toUpperCase());
	    System.out.println(form);
	    String type = info[3].toUpperCase();
	    int pris = Integer.valueOf(info[4]);
	    double mengde = Double.valueOf(info[5]);

	    try {
		int styrke = Integer.valueOf(info[6]);
		String klasseNavn = "Legemiddel" + type + form;
		Class klasse = Class.forName(klasseNavn);
		//Contructor <Class.forName(klasseNavn)> c = klasse.getContructor(String.Class, Integer.TYPE, Double.TYPE, Integer.TYPE);
		
	    }

	    catch (Exception e) {
	    }
	}
	
	les.nextLine();
	les.nextLine();
	while(!les.hasNext("#")) {
	    String[] info = les.nextLine().split(", ");
	    String navn = info[1];
	    int avtaleNr = Integer.valueOf(info[3]);
	    if (info[2].equals("1")){
		SpesialistLege nyLege = new SpesialistLege(navn,avtaleNr);
		legeListe.add(nyLege);
	    }else{ 
		Lege nyLege = new Lege(navn,avtaleNr); 
		legeListe.add(nyLege);
	    }

	}
	
	les.nextLine();
	les.nextLine();
	while(!les.hasNext("#")){
	    String[] info = les.nextLine().split(", ");
	    boolean blaa = false;
	    if(info[2].equals("b")) blaa = true;
	    int persNr =Integer.valueOf(info[2]);
	    String legeNavn = info[3];
	    int legemiddelNr = Integer.valueOf(info[4]);
	    int reit = Integer.valueOf(info[5]);
	    LegemiddelC legemiddel = legemiddelTabell.finnObjekt(legemiddelNr);
	    Lege lege = legeListe.finnObjekt(legeNavn);
	    Resept nyResept = new Resept(legemiddel, lege, persNr, reit, blaa);
	    reseptListe.add(nyResept);
	    lege.add(nyResept);
	    int personNr = Integer.valueOf(persNr);
	    personTabell.finnObjekt(persNr).add(nyResept);
	}
	
    }

    public LegemiddelC hentResept(int persNr, int reseptNr) {
	Person person = personTabell.finnObjekt(persNr);
	Resept res = person.getResept(reseptNr);
	LegemiddelC drug  = res.getLegemiddel();
	if (drug == null) {
	    System.out.println("Ugyldig resept");
	    return null;
	}
	else {
	    System.out.println("Det blir " + res.getPris() + "kr takk.");
	}

	System.out.println("Legens navn: " + res.getLege().getName());
	System.out.println("Pasientens navn: " + person.getName());
	res.getLegemiddel().skriv();
	return res.getLegemiddel();
    }

    public void skrivLeger() {
	
	for (Lege lege : legeListe) {
	    if (lege.avtale()) {
	 	lege.skriv();
	 	System.out.println("Antall narkotiske resepter skrevet: " + lege.antAMidler());
	    }
	}  
	           		           
    }

    public void skrivPersoner(){
	int menn = 0;
	int kvinner = 0;
	int total = 0;
	for (Person pers : personTabell){
	    System.out.println(pers.getName());
	    int antall =  pers.antVaneLegemidler();
	    System.out.println("Antall Vane Legemidler: " +  antall);
	    if(pers instanceof Mann){
		menn = menn + antall;
		total = total + antall;
	    }else{
		kvinner += antall ;
		total += antall;
	    }
	}
	System.out.println("Antall vane legemidler skrevet totalt: " + total);
	System.out.println("Antall vane legemidler skrevet til menn: " + menn);
	System.out.println("Antall vane legemidler skrevet til kvinner: " + kvinner);
    }
    
    public void skrivAlle(){
	for(LegemiddelC drug : legemiddelTabell){
	    drug.skriv();
	    System.out.println();
	}

	for(Person pers : personTabell){
	    pers.skriv();
	    System.out.println();
	}
	
	for(Lege lege : legeListe){
	    lege.skriv();
	    System.out.println();
	}
    }
	

	
    
}
	
	
	    

	


	    
	



















































	