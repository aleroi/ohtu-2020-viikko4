package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {
    
    Pankki pankki;
    Viitegeneraattori viite;
    Varasto varasto;
    Kauppa k;
    
    @Before
    public void setUp() {

        pankki = mock(Pankki.class);
        viite = mock(Viitegeneraattori.class);
        varasto = mock(Varasto.class);

        when(viite.uusi()).thenReturn(42);
    
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));          

        when(varasto.saldo(2)).thenReturn(3); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "kakku", 7));
        
        when(varasto.saldo(3)).thenReturn(0); 
        when(varasto.haeTuote(3)).thenReturn(new Tuote(2, "kakku", 6));
         
        k = new Kauppa(varasto, pankki, viite);              
        }

   
    @Test
    public void ostoksenPaatyttyaPankinMetodiaTilisiirtoKutsutaan() {

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(),anyInt());   
    }
    
    @Test
    public void ostoksenPaatyttyaPankinMetodiaTilisiirtoKutsutaanOikeillaArvoilla() {
        
        k.aloitaAsiointi();
        k.lisaaKoriin(1); 
        k.tilimaksu("pekka", "12345");      
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), eq("33333-44455"), eq(5));   
                
    }
    
    @Test
    public void ostetaanKaksiEriTuotettaJaTilisiirtoKutsutaanOikeillaArvoilla() {
        
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2); 
        k.tilimaksu("pekka", "12345");        
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), eq("33333-44455"), eq(12));   
                
    }
    
    @Test
    public void ostetaanKaksiSamaaTuotettaJaTilisiirtoKutsutaanOikeillaArvoilla() {
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");        
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), eq("33333-44455"), eq(10));   
                
    }
    
    @Test
    public void ostetaanLoppunutTuoteJaNormaaliTuoteJaTilisiirtoKutsutaanOikeillaArvoilla() {

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(3); 
        k.tilimaksu("pekka", "12345");      
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), eq("33333-44455"), eq(5));   
                
    }
    
    @Test
    public void aloitaAsiointiMetodiNollaaOstoskorinTiedot() {
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.aloitaAsiointi();
        k.tilimaksu("pekka", "12345");      
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), eq(0));   
                
    }
    
    @Test
    public void uusiViitenumeroJokaMaksulla() {
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");
        verify(viite, times(1)).uusi();
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");
        verify(viite, times(2)).uusi();
                
    }

    @Test
    public void TuotepoistetaanTuoteOstoskorista() {
        k.aloitaAsiointi();

        k.lisaaKoriin(1);
        k.lisaaKoriin(1);     
        k.lisaaKoriin(1);            
        Tuote testituote=varasto.haeTuote(1);
        verify(varasto, times(0)).palautaVarastoon(testituote);
        k.poistaKorista(1);
        verify(varasto, times(1)).palautaVarastoon(testituote);

    }   
}
