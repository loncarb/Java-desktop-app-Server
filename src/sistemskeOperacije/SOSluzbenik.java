/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistemskeOperacije;

import domen.DomenskiObjekat;
import domen.Sluzbenik;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class SOSluzbenik extends SistemskaOperacija{

    @Override
    protected void proveriPreduslov() {
        
    }

    @Override
    protected void izvrsiOperaciju(DomenskiObjekat domObj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<DomenskiObjekat> vrati(DomenskiObjekat dom) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<DomenskiObjekat> nadji(DomenskiObjekat dom) throws Exception {
        List<DomenskiObjekat> listaSluzbenika = new ArrayList<>();
        ResultSet rs = db.nadji(dom, kreirajUpit(dom));
        while (rs.next()) {
            String kor = rs.getString("KorisnickoIme");
            String sif = rs.getString("KoorisnickaSifra");
            Sluzbenik s = new Sluzbenik(kor, sif);
            listaSluzbenika.add(s);
        }
        return listaSluzbenika;
    }

    private String kreirajUpit(DomenskiObjekat dom) {
        Sluzbenik s = (Sluzbenik) dom;
        return "SELECT * FROM Sluzbenik WHERE KorisnickoIme='" + s.getKorisnickoIme() + "' AND KoorisnickaSifra='" + s.getKorisnickaSifra() + "'";
    }

    @Override
    protected void izbrisi(DomenskiObjekat dom) throws Exception{
        
    }
    
}
