
package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
                            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] lukujono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        alustaMuuttujat(KAPASITEETTI, OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti < 0) {
            return;
        }
        alustaMuuttujat(kapasiteetti, OLETUSKASVATUS);
    }
    
    
    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasiteetti ei voi olla negatiivinen");
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("kasvatuskoko ei voi olla negatiivinen");
        }
        alustaMuuttujat(kapasiteetti, kasvatuskoko);
    }
    
    public boolean lisaa(int luku) {
        if(!kuuluu(luku)) {
            lukujono[alkioidenLkm]=luku;
            alkioidenLkm++;
            if(lukujono.length == alkioidenLkm) {
                taulukonKasvatus();
            }
            return true;
        }
        return false;
        }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == lukujono[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean poista(int luku) {
        for(int i = 0; i < alkioidenLkm; i++) {
            if(luku == lukujono[i]) {
                siirraVasemmalle(i);
                alkioidenLkm--;
                return true;
            }
        }
        return false;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }


    @Override
    public String toString() {
        if (alkioidenLkm == 0) {
            return "{}";
        } 
            String tuotos = "{";
            for (int i = 0; i < alkioidenLkm - 1; i++) {
                tuotos += lukujono[i] + ", ";  
            }
            tuotos += lukujono[alkioidenLkm - 1];
            tuotos += "}";
            return tuotos;
        }
    

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = lukujono[i];
        }
        return taulu;
    }
    public int[] getLukujono() {
        return this.lukujono;
    }
   
    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        int[] aTaulu = a.getLukujono();
        int[] bTaulu = b.getLukujono();
        for (int i = 0; i < a.mahtavuus(); i++) {
            x.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < b.mahtavuus(); i++) {
            x.lisaa(bTaulu[i]);
        }
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        int[] aTaulu = a.getLukujono();
        int[] bTaulu = b.getLukujono();
        for (int i = 0; i < a.mahtavuus(); i++) {
            for (int j = 0; j < b.mahtavuus(); j++) {
                if (aTaulu[i] == bTaulu[j]) {
                    y.lisaa(bTaulu[j]);
                }
            }
        }
        return y;

    }
    
    public static IntJoukko erotus ( IntJoukko a, IntJoukko b) {
        IntJoukko z = new IntJoukko();
        int[] aTaulu = a.getLukujono();
        int[] bTaulu = b.getLukujono();
        for (int i = 0; i < a.mahtavuus(); i++) {
            z.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < b.mahtavuus(); i++) {
            z.poista(bTaulu[i]);
        }
 
        return z;
    }

    private void alustaMuuttujat(int kapasiteetti, int kasvatus) {
        lukujono = new int[kapasiteetti];
        
        for(int i = 0; i < lukujono.length;i++) {
            lukujono[i] = 0;
        }
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatus;
    }
    private void taulukonKasvatus() {
        int[] taulukko = new int[lukujono.length + OLETUSKASVATUS];
            kopioiTaulukko(lukujono, taulukko);
            this.lukujono = taulukko;
    }


    private void siirraVasemmalle(int mista) {
        for(int i = mista; i < alkioidenLkm; i++) {
            lukujono[i] = lukujono[i + 1];
        }
    }
        
}
