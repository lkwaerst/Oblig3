import java.util.ArrayList;
import java.util.*;
import java.io.*;


class Oblig4commit {

    public static void main(String[] args) {
	Scanner s = new Scanner(System.in);
	UserInterface use = new UserInterface(s);
	use.lesFil();

	//loekke for aa haandtere unntak fra input
	while (true) {
	    try {
		use.controll();
		break;
	    }

	    catch (FantIkkeReseptException e) {
		System.out.println("Fant ikke reseptet, sjekk staving");
	    }
	    catch (FantIkkeObjektException e) {
		System.out.println("Fant ikke i systemet, sjekk staving");
	    }
	    catch (InputMismatchException e) {
		System.out.println("Feil type informasjon, proev igjen");
		s.next();
	    }
	    catch (Exception e) {
		e.printStackTrace();
	    }
	}
	
    }
}

abstract class Person{
    private String navn;
    private int nummer;
    static int antallPersoner = 1; //
    private YngsteFoerstReseptListe mineResepter = new YngsteFoerstReseptListe();
    
    Person(String navn){
	this.navn = navn;
	nummer = antallPersoner++;
    }

    public void add(Resept res){
	mineResepter.add(res);
    }

    public String getName() {
	return navn;
    }

    public Resept getResept(int reseptNr) throws FantIkkeReseptException {
	return mineResepter.finnResept(reseptNr);
    }

    public void skriv() {
	System.out.println("Navn: " + navn);
	System.out.println("Personnummer: " + nummer);
    }

    //skriver ut informasjon om alle resepter som er blaa og gyldige
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

    //returnerer antall resepter med vanedannende legemidler personen har fått
    public int antVaneLegemidler(){
	int teller = 0;
	
	for (Resept r : mineResepter){
	    if((r.getLegemiddel() instanceof LegemiddelB)&& r.gyldig()){
		teller++;
	    }
	}
	return teller;
    }

    public String hentInfo() {
	return nummer + ", " + navn;
    }
}

class Kvinne extends Person{
    Kvinne(String navn){
	super(navn);
    }

    public String hentInfo() {
	return super.hentInfo() + ", k";
    }
}


class Mann extends Person{
    Mann(String navn){
	super(navn);
    } 

    public String hentInfo() {
	return super.hentInfo() + ", m";

    }
}

class Lege implements Comparable<Lege>, Lik, Avtale { //) ENDRE HER SENERE
    private String navn;
    private EldsteFoerstReseptListe resepterSkrevet = new EldsteFoerstReseptListe();
    private int avtaleNr; //0 om ingen avtale

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
    
    //returnerer positivt om denne legen kommer foer den andre, ellers negativt. 0 om de er like
    public int compareTo(Lege lege) {
	return lege.getNavn().compareToIgnoreCase(this.navn);
    }

    public void add(Resept res) {
	resepterSkrevet.add(res);
    }

    //skriver ut navn og eventuelt avtalenummer
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
	return avtaleNr != 0;
    }

    //returnerer antall narkotiske legemidler legen har skrevet resept paa
    public int antAMidler() {
	int teller = 0;

	for (Resept resept : resepterSkrevet) {
	    if (resept.getLegemiddel() instanceof LegemiddelA) {
		teller++;
	    }
	}
	return teller;
    }

    public String hentInfo() {
	if (this instanceof SpesialistLege) {
	    return navn + ", 1, " + avtaleNr;
	}
	return navn + ", 0, " + avtaleNr;
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

abstract class LegemiddelC {
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

    //Skriver ut navn og id nummer
     public void skriv() {
	 System.out.println("Legemiddel: " + navn);
	 System.out.println("\tId nummer: " + idNummer);
     }

    public String hentInfo() {
	return idNummer + ", " + navn;
    }

    public int getidNummer() {
	return idNummer;
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

    public String hentInfo() {
	return super.hentInfo() + ", c, injeksjon, " + dose;
    }
}
class LegemiddelAInjeksjon extends LegemiddelA implements Injeksjon {
    private double dose;

    LegemiddelAInjeksjon (String navn, int pris, double dose, int narko) {
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

    public String hentInfo() {
	return super.hentInfo() + ", injeksjon, " + dose;
    }
}
class LegemiddelBInjeksjon extends LegemiddelB implements Injeksjon {
    private double dose;

    LegemiddelBInjeksjon (String navn, int pris, double dose, int vane) {
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

    public String hentInfo() {
	return super.hentInfo() + ", injeksjon, " + dose;
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

    public String hentInfo() {
	return super.hentInfo() + ", c, pille, " + antPiller;
    }
}
class LegemiddelAPille extends LegemiddelA implements Pille {
    private double antPiller;

    LegemiddelAPille (String navn, int pris, double antPiller, int narko) {
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

    public String hentInfo() {
	return super.hentInfo() + ", pille, " + antPiller;
    }
}
class LegemiddelBPille extends LegemiddelB implements Pille {
    private double antPiller;

    LegemiddelBPille (String navn, int pris, double  antPiller, int vane) {
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

    public String hentInfo() {
	return super.hentInfo() + ", pille, " + antPiller;
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

    public String hentInfo() {
	return super.hentInfo() + ", c, liniment, " + volum;
    }
}
class LegemiddelALiniment extends LegemiddelA implements Liniment {
    private double volum;

    LegemiddelALiniment (String navn, int pris, double volum, int narko) {
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

    public String hentInfo() {
	return super.hentInfo() + ", liniment, " + volum;
    }
}
class LegemiddelBLiniment extends LegemiddelB implements Liniment {
    private double volum;

    LegemiddelBLiniment (String navn, int pris, double volum, int vane) {
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

    public String hentInfo() {
	return super.hentInfo() + ", liniment, " + volum;
    }
}

abstract class LegemiddelB extends LegemiddelC {

    private int vanedannelsesGrad;

    LegemiddelB (String navn, int pris, int vane) {
	super(navn, pris);
	vanedannelsesGrad = vane;
    }

    public void skriv() {
	super.skriv();
	System.out.println("\tVanedannelsesgrad: " + vanedannelsesGrad);
    }

    public String hentInfo() {
	return super.hentInfo() + ", b, " + vanedannelsesGrad;
    }
}

abstract class LegemiddelA extends LegemiddelC {

    private int narkotiskGrad;

    LegemiddelA (String navn, int pris, int narko) {
	super(navn, pris);
	narkotiskGrad = narko;
    }

    public void skriv() {
	super.skriv();
	System.out.println("\tNarkotisk grad: " + narkotiskGrad);
    }

    public String hentInfo() {
	return super.hentInfo() + ", a, " + narkotiskGrad;
    }
}

interface Pille {    
    public double getAntPiller(); //per boks
}

interface Liniment {  
    public double getVolum(); //i cm^3
}

interface Injeksjon {
    public double getDose(); //i mg
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

    //kalles naar personen bruker reseptet
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
    
    //skriver ut resept id og antall paafyll igjen
    public void skriv() {
	System.out.println("Resept id: " + idNummer + "\t" + "Injeksjonsdoser: " + reit);
    }

    public String hentInfo() {
	return idNummer + ", h, " + personNr + lege.getName() + String.valueOf(medisin.getidNummer()) + reit;
    }
}

interface AbstraktTabell<T> {
    public boolean add(int indeks, T objekt);

    public T finnObjekt(int indeks) throws FantIkkeObjektException;

    public Iterator iterator();
	
}

interface AbstraktSortertEnkelListe<T extends Comparable & Lik>{
    public void add(T objekt);

    public T finnObjekt(String nokkel) throws FantIkkeObjektException;

    public Iterator iterator();
}

class  Tabell<T> implements AbstraktTabell<T>, Iterable<T> {

    private T[] beholder;
    
    Tabell(int lengde){
	beholder = (T[]) new Object[lengde];
    }

    //returnerer false om indeks plassen er optatt, ellers true
    public boolean add(int indeks, T objekt) {
	if (beholder.length > indeks){
	    if(beholder[indeks] == null) {
		beholder[indeks] = objekt;
		return true;
	    }
	    return false;
	}

	//dobler lengden paa arrayen til den er stor nok til aa sette inn paa indeks
	T[] nybeholder;
	nybeholder = (T[]) new Object[beholder.length*2];

	for (int i = 0; i < beholder.length; i++){
	    nybeholder[i] = beholder[i];
	}
	beholder = nybeholder;
	return this.add(indeks, objekt);
    }

    //returnerer objekt paa plass indeks, kaster Exception om plassen ikke finnes
    public T finnObjekt(int indeks) throws FantIkkeObjektException{
	if(beholder.length > indeks){
	    return beholder[indeks];
	}
	throw new FantIkkeObjektException();

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

	//sjekker framover i tabellen om det finnes flere objekter
	public boolean hasNext() {
	    for(int i = teller; teller < elementer.length; teller++){
		if (elementer[i] !=  null) {
		    return true;
		}
	    }
	    return false;
	}
	
	public U next() {
	    if (hasNext()) {
		return elementer[teller++];
	    }
	    return null;  //kaste unntak kanskje?
	}

	public void remove() throws UnsupportedOperationException {
	}
    }
}

class SortertEnkelListe<T extends Comparable & Lik> implements AbstraktSortertEnkelListe<T>, Iterable<T> {
    private Node start;
    private int antNoder = 0;
   
    //legger objektet foerst i listen
    public void add(T nyttObjekt){
	Node nynode = new Node(nyttObjekt);
	Node n;

	//om listen er tom
	if (start == null) {
	    start = nynode;
	    antNoder++;
	    return;
	}
	
	//om listen bare har ett element
	if (start.objekt.compareTo(nyttObjekt) < 0){
	    nynode.neste = start;
	    start = nynode;
	    antNoder++;
	    return;
	}
	
	//setter ny node mellom n og n.neste om den er alfabetisk mindre enn n.neste
	for(n = start; n.neste != null; n = n.neste){
	    if (n.neste.objekt.compareTo(nyttObjekt) < 0){
		nynode.neste = n.neste;
		n.neste = nynode;
		antNoder++;
		return;
	    }
	}
	
	//om det nye objektet skal sist i lista
	n.neste = nynode;
	antNoder++;
    }
	
	
    public T finnObjekt(String nokkel) throws FantIkkeObjektException {
	for(Node n =start; n != null; n = n.neste){
	    if (n.objekt.samme(nokkel)){
		return n.objekt;
	    }
	}
	throw new FantIkkeObjektException();
    }

    public Iterator<T> iterator() {
	Iterator<T> it = new Iteratorfactory2();
	return it;
    }

    class Iteratorfactory2 implements Iterator<T>{
	Node posisjonsNode = start;

	public boolean hasNext(){
	    if(posisjonsNode != null && posisjonsNode.neste != null) return true;
	    return false;
	}

	public T next(){
	    if(hasNext()){
		T t = posisjonsNode.objekt;
		posisjonsNode = posisjonsNode.neste;
		return t;
	    }
	    return null; //unntak?
	}

	public void remove(){}
    }

    private class Node {
	T objekt;
	Node neste;

	Node(T objekt) {
	    this.objekt = objekt;
	}
    }
}

class EnkelReseptListe implements Iterable<Resept> {
    
    ReseptNode start;
    ReseptNode siste;

    //legger inn det nye reseptet sist i listen
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

    //returnerer reseptet med id nummer like i, kaster unntak om ikke funnet
    public Resept finnResept(int i) throws FantIkkeReseptException {
	for (ReseptNode n = start; start != null && n.neste != null; n = n.neste) {
	    if (n.resept.getIdNummer() == i) {
		return n.resept;
	    }
	}
	throw new FantIkkeReseptException();
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

    private class IteratorFabrikk implements Iterator<Resept> {
	ReseptNode posisjonsNode;

	IteratorFabrikk(ReseptNode start) {
	    posisjonsNode = start;
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

    //setter inn nytt objekt foerst
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

class FantIkkeReseptException extends Exception {
}
class FantIkkeObjektException extends Exception {
}


class UserInterface {

    Tabell<LegemiddelC> legemiddelTabell = new Tabell<LegemiddelC>(10);
    SortertEnkelListe<Lege> legeListe = new SortertEnkelListe<Lege>();
    Tabell<Person> personTabell = new Tabell<Person>(10);
    EnkelReseptListe reseptListe = new EnkelReseptListe();
    Scanner scan;
    Scanner les;

    UserInterface(Scanner s) {
	scan = s;
    }
    
    //kommandoloekke
    public void controll() throws Exception {
	int valg = 0;

	while(valg != 6){
	    System.out.println("\n\n1.Opprette og legge inn et nytt legemiddel.");
	    System.out.println("2.Opprette og legge inn en ny lege.");
	    System.out.println("3.Opprette og legge inn en ny person.");
	    System.out.println("4.Opprette og legge inn et nytt resept.");
	    System.out.println("5.Skriv all info.");
	    System.out.println("6.Lagre og avslutt.\n");
	    System.out.print("Gi et nummer: ");
	    valg = scan.nextInt();

	    switch(valg) {
	    case 1: nyLegemiddel();break;
	    case 2: nyLege(); break;
	    case 3: nyPerson(); break;
	    case 4: nyResept(); break;
	    case 5: skrivAlle(); break;
	    case 6: skrivTilFil(); break;
	    }
	}
    }

    public void nyLegemiddel(){
	String typeabc = "";
	String type = "";
	String navn;
	int pris;
	int grad;
	double volum;
	LegemiddelC nyLegemiddel = null;


	System.out.print("Gi navn av Legemiddelet: ");
	navn = scan.next();
	System.out.print("Gi pris av Legemiddelet: ");
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
			nyLegemiddel = new LegemiddelAInjeksjon(navn,pris,volum, grad);
			legemiddelTabell.add(LegemiddelC.antallLegemidler,nyLegemiddel);
			break;
		    case"Liniment":
			System.out.println("Gi narkotiskgrad:");
			grad = scan.nextInt();
			System.out.println("Gi volum:");
			volum = scan.nextDouble();
			nyLegemiddel = new LegemiddelALiniment(navn,pris,volum, grad);
			legemiddelTabell.add(LegemiddelC.antallLegemidler,nyLegemiddel);
			break;
		    case"Pille":
			System.out.println("Gi narkotiskgrad:");
			grad = scan.nextInt();
			System.out.println("Gi Antallpiller:");
			volum = scan.nextDouble();
			nyLegemiddel = new LegemiddelAPille(navn,pris,volum, grad);
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
			nyLegemiddel = new LegemiddelBInjeksjon(navn,pris,volum, grad);
			legemiddelTabell.add(LegemiddelC.antallLegemidler,nyLegemiddel);
			break;
		    case"Liniment":
			System.out.println("Gi vanedannelsesgrad:");
			grad = scan.nextInt();
			System.out.println("Gi volum:");
			volum = scan.nextDouble();
			nyLegemiddel = new LegemiddelBLiniment(navn,pris,volum, grad);
			legemiddelTabell.add(LegemiddelC.antallLegemidler,nyLegemiddel);
			break;
		    case"Pille":
			System.out.println("Gi vanedannelsesgrad:");
			grad = scan.nextInt();
			System.out.println("Gi Antallpiller:");
			volum = scan.nextDouble();
			nyLegemiddel = new LegemiddelBPille(navn,pris,volum, grad);
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
	Lege nyLege = null;

	System.out.print("Gi legens navn: ");
	navn = scan.next(); 

	spesialistLege = jaNei("Er legen spesialist?");

	if (jaNei("Har legen en avtale?")) {
	    System.out.print("Avtalenummer: ");
	    System.out.println();
	    avtaleNr = scan.nextInt();
	}
	
	if (spesialistLege) {
	    nyLege = new SpesialistLege(navn, avtaleNr);
	}
	else {
	    nyLege = new Lege(navn, avtaleNr);
	}

	legeListe.add(nyLege);
    }

    public void nyPerson() {
	System.out.print("Navn: ");
	String navn = scan.next();
	
	if (jaNei("Mann?")) {
	    personTabell.add(Person.antallPersoner, new Mann(navn));
	}
	else {
	    personTabell.add(Person.antallPersoner, new Kvinne(navn));
	}
    }

    public void nyResept() throws FantIkkeObjektException{
	Resept nyResept = null;
	
	System.out.print("Legemiddel nummer: ");
	int nr = scan.nextInt();
	LegemiddelC legemiddel = legemiddelTabell.finnObjekt(nr);

	System.out.print("Legens navn: ");
	String legeNavn = scan.next();
	Lege lege = legeListe.finnObjekt(legeNavn);

	System.out.print("Personnummer: ");
	int  persNr = scan.nextInt();

	System.out.print("Antall: ");
	int reit = scan.nextInt();

	boolean blaa = jaNei("Blaatt resept?");	 

	nyResept = new Resept(legemiddel, lege, persNr, reit, blaa);
	reseptListe.add(nyResept);
	lege.add(nyResept);
	personTabell.finnObjekt(persNr).add(nyResept);
    }

    public boolean jaNei(String spoersmaal) {

	while (true) {
	    System.out.print(spoersmaal + " (y/n): ");
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
	int linjeTeller = 0;

	try {
	    les = new Scanner(new File("filnavn.txt"));
	}
	catch (Exception e) {
	    System.out.println("noe gikk galt");
	    e.printStackTrace();
	}
	les.useDelimiter("(,+)|(\\s+)");

	//leser og oppretter personer
	les.nextLine(); linjeTeller++;
	while (!les.hasNext("#")) {
	    String[] info = les.nextLine().split(", "); linjeTeller++;
	    if (info[2].equals("m")) {
		personTabell.add(Integer.valueOf(info[0]), new Mann(info[1]));
	    }
	    else {
		personTabell.add(Integer.valueOf(info[0]), new Kvinne(info[1]));
	    }
	}

	//leser og oppretter Legemidler
	les.nextLine(); linjeTeller++;
	les.nextLine(); linjeTeller++;
	while (!les.hasNext("#")) {
	    LegemiddelC nyttLegemiddel = null;
	    int styrke;
	    String[] info = les.nextLine().split(", "); linjeTeller++;
	    int idNummer = Integer.valueOf(info[0]);
	    String navn = info[1];
	    String form = info[2];
	    String type = info[3].toUpperCase();
	    int pris = Integer.valueOf(info[4]);
	    double mengde = Double.valueOf(info[5]);

	    //Opretter riktig type legiddel objekt (for A og B)
	    try {
		styrke = Integer.valueOf(info[6]);
		switch (type) {
		case "A":
		    switch (form) {
		    case "injeksjon": nyttLegemiddel = new LegemiddelAInjeksjon(navn, pris, mengde, styrke); break;
		    case "pille": nyttLegemiddel = new LegemiddelAPille(navn, pris, mengde, styrke); break;	
		    case "liniment": nyttLegemiddel = new LegemiddelALiniment(navn, pris, mengde, styrke); break; 
		    }
		case "B":
		    switch (form) {
		    case "injeksjon": nyttLegemiddel = new LegemiddelBInjeksjon(navn, pris, mengde, styrke); break;
		    case "pille": nyttLegemiddel = new LegemiddelBPille(navn, pris, mengde, styrke); break;	
		    case "liniment": nyttLegemiddel = new LegemiddelBLiniment(navn, pris, mengde, styrke); break; 
		    }
		}
	    }

	    //Om legemiddelet skal vaere av typen C
	    catch(ArrayIndexOutOfBoundsException e) {
		switch (form) {
		case "injeksjon": nyttLegemiddel = new LegemiddelCInjeksjon(navn, pris, mengde); break;
		case "pille": nyttLegemiddel = new LegemiddelCPille(navn, pris, mengde); break;	
		case "liniment": nyttLegemiddel = new LegemiddelCLiniment(navn, pris, mengde); break;
		}
	    }
	    
	    legemiddelTabell.add(idNummer, nyttLegemiddel);
	}
		
	//leser og oppretter leger
	les.nextLine(); linjeTeller++;
	les.nextLine(); linjeTeller++;
	while(!les.hasNext("#")) {
	    Lege nyLege;
	    String[] info = les.nextLine().split(", "); linjeTeller++;
	    String navn = info[0];
	    int avtaleNr = Integer.valueOf(info[2]);

	    if (info[1].equals("1")){
		nyLege = new SpesialistLege(navn, avtaleNr);
	    }
	    else{ 
		nyLege = new Lege(navn, avtaleNr); 
	    }
	    legeListe.add(nyLege);

	}
	

	//leser og oppretter resepter
	les.nextLine(); linjeTeller++;
	les.nextLine(); linjeTeller++;
	while(!les.hasNext("#")){
	    String[] info = les.nextLine().split(", "); linjeTeller++;
	    boolean blaa = false;
	    if(info[1].equals("b")) blaa = true;
	    int persNr = Integer.valueOf(info[2]);
	    String legeNavn = info[3];
	    int legemiddelNr = Integer.valueOf(info[4]);
	    int reit = Integer.valueOf(info[5]);

	    try {
		LegemiddelC legemiddel = legemiddelTabell.finnObjekt(legemiddelNr);
		Lege lege = legeListe.finnObjekt(legeNavn);
		Resept nyResept = new Resept(legemiddel, lege, persNr, reit, blaa);

		reseptListe.add(nyResept);
		lege.add(nyResept);
		int personNr = Integer.valueOf(persNr);
		personTabell.finnObjekt(persNr).add(nyResept);
	    }

	    catch (FantIkkeObjektException e) {
		System.out.println("Noe gikk galt paa linje " + linjeTeller + " under lesing av filen");
	    }
	}
	
    }
    
    
    public LegemiddelC hentResept(int persNr, int reseptNr) throws FantIkkeReseptException, FantIkkeObjektException {
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
	drug.skriv();
	return drug;
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

    public void skrivTilFil() {
	PrintWriter filSkriv = null;
	
	try {
	    filSkriv = new PrintWriter(new FileWriter(new File("filnavn.txt"), false));
	}

	catch (Exception e) {
	    System.out.println("uff da");
	}

	filSkriv.println("# Personer (nr, navn, kj�nn)");
	for (Person p : personTabell) {
	    filSkriv.println(p.hentInfo());
	}
	
	filSkriv.println("\n# Legemidler (nr, navn, form, type, pris, mengde [, styrke])");
	for (LegemiddelC l : legemiddelTabell) {
	    filSkriv.println(l.hentInfo());
	}

	filSkriv.println("\n# Leger (navn, spesialist, avtalenr / 0 hvis ingen avtale)");
	for (Lege lege : legeListe) {
	    filSkriv.println(lege.hentInfo());
	}

	filSkriv.println("\n# Resepter (nr, hvit/blå, persNummer, legeNavn, legemiddelNummer, reit)");
	for (Resept r : reseptListe) {
	    filSkriv.println(r.hentInfo());
	}

	filSkriv.println("# Slutt");
	filSkriv.close();
    }
}
	
	
	    

	


	    
	



















































	