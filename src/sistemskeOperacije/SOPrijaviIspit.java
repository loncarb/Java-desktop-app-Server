/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistemskeOperacije;

import domen.DomenskiObjekat;
import domen.Ispit;
import domen.PolozenIspit;
import domen.Student;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class SOPrijaviIspit extends SistemskaOperacija{

    @Override
    protected void proveriPreduslov() {
        
    }

    @Override
    protected void izvrsiOperaciju(DomenskiObjekat domObj) throws Exception {
        try{
            db.sacuvaj(domObj);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new Exception("greska");
        }
    }

    @Override
    protected List<DomenskiObjekat> vrati(DomenskiObjekat dom) throws Exception {
        List<DomenskiObjekat> listaPolozenih = new ArrayList<>();
        PolozenIspit po = new PolozenIspit();
        Student s = (Student) dom;
        po.setStudent(s);
        ResultSet rs = db.vratiDomObj(po);
        while (rs.next()) {
            
            PolozenIspit p = new PolozenIspit();
            int ocena = rs.getInt("Ocena");
            Ispit i = new Ispit();
            i.setIspitID(rs.getInt("IspitID"));
            i.setNaziv(rs.getString("Naziv"));
            p.setOcena(ocena);
            p.setIspit(i);
            listaPolozenih.add(p);
        }
        
        return listaPolozenih;
    }

    @Override
    protected List<DomenskiObjekat> nadji(DomenskiObjekat dom) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void izbrisi(DomenskiObjekat dom) throws Exception{
        
    }
    
    
}
