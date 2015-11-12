/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistemskeOperacije;

import domen.DomenskiObjekat;
import domen.Proizvod;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class SOProizvod extends SistemskaOperacija{

    @Override
    protected void proveriPreduslov() {
        
    }

    @Override
    protected void izvrsiOperaciju(DomenskiObjekat domObj) throws Exception {
        
    }

    @Override
    protected List<DomenskiObjekat> vrati(DomenskiObjekat dom) throws Exception {
        List<DomenskiObjekat> listaProizvoda = new ArrayList<>();
        ResultSet rs = db.vratiDomObj(dom);
        while (rs.next()) {

            String naziv = rs.getString("Naziv");
            double c = rs.getDouble("Cena");
            
            Proizvod p = new Proizvod();
            p.setCena(c);
            p.setNaziv(naziv);
            listaProizvoda.add(p);
        }
        
        return listaProizvoda;
    }

    @Override
    protected List<DomenskiObjekat> nadji(DomenskiObjekat dom) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void izbrisi(DomenskiObjekat dom) throws Exception{
        
    }
    
}
