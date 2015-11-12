/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistemskeOperacije;

import domen.DomenskiObjekat;
import java.util.List;

/**
 *
 * @author user
 */
public class SOSacuvajRacun extends SistemskaOperacija{

    
    @Override
    protected void izvrsiOperaciju(DomenskiObjekat domObj) throws Exception {
        try{
            db.sacuvaj(domObj);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    protected void proveriPreduslov() {
       
    }

    

    @Override
    protected List<DomenskiObjekat> vrati(DomenskiObjekat dom) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<DomenskiObjekat> nadji(DomenskiObjekat dom) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void izbrisi(DomenskiObjekat dom) throws Exception{
        
    }
    
    
    
}
