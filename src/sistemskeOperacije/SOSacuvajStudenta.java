/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemskeOperacije;

import domen.Adresa;
import domen.DomenskiObjekat;
import domen.Mesto;
import domen.Student;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class SOSacuvajStudenta extends SistemskaOperacija {

    @Override
    protected void proveriPreduslov() {

    }

    @Override
    protected void izvrsiOperaciju(DomenskiObjekat domObj) throws Exception {
        try{
            db.sacuvaj(domObj);
        }catch(Exception ex){
            throw new Exception("Operacija cuvanja nije izvrsena!");
        }
        
        
    }

    @Override
    protected List<DomenskiObjekat> vrati(DomenskiObjekat dom) throws Exception {
        List<DomenskiObjekat> listaStudents = new ArrayList<>();

        ResultSet rs = db.vratiDomObj(dom);
        while (rs.next()) {
            Student s = new Student();
            s.setJMBG((int) rs.getLong("JMBG"));
            s.setIme(rs.getString("Ime"));
            s.setPrezime(rs.getString("Prezime"));
            s.setBrIndeksa(rs.getString("BrIndeksa"));

            Adresa a = new Adresa();
            a.setBroj(rs.getString("Broj"));
            a.setUlica(rs.getString("Ulica"));

            Mesto m = new Mesto();
            m.setNaziv(rs.getString("Naziv"));
            m.setPostanskiBr(rs.getLong("PostanskiBroj"));

            a.setMesto(m);
            s.setAdresa(a);
            listaStudents.add(s);
        }
        return listaStudents;
    }

    @Override
    protected List<DomenskiObjekat> nadji(DomenskiObjekat dom) throws Exception {
        List<DomenskiObjekat> listaStudenata = new ArrayList<>();
        ResultSet rs = db.nadji(dom, kreirajUpit(dom));
        while (rs.next()) {
            Student s = new Student();
            s.setJMBG((int) rs.getLong("JMBG"));
            s.setIme(rs.getString("Ime"));
            s.setPrezime(rs.getString("Prezime"));
            s.setBrIndeksa(rs.getString("BrIndeksa"));

            Adresa a = new Adresa();
            a.setBroj(rs.getString("Broj"));
            a.setUlica(rs.getString("Ulica"));

            Mesto m = new Mesto();
            
            m.setNaziv(rs.getString("Naziv"));
            m.setPostanskiBr(rs.getLong("PostanskiBroj"));

            a.setMesto(m);
            s.setAdresa(a);
            listaStudenata.add(s);
        }
        return listaStudenata;
    }
    
    public String kreirajUpit (DomenskiObjekat dom){
        Student s = (Student) dom;
        String upit = "SELECT * From Mesto INNER JOIN Student ON Mesto.PostanskiBroj = Student.Mesto WHERE ";
        int brojac =0;
        if (s.getJMBG()!=0){
            upit = upit+"(JMBG = "+s.getJMBG()+" ";
            brojac++;
        }
        if (s.getAdresa().getMesto()!=null){
            if(brojac>0){
                upit = upit+"AND Mesto = '"+s.getAdresa().getMesto().getPostanskiBr()+"'";
                
            }else{
                upit = upit+"(Mesto = "+s.getAdresa().getMesto().getPostanskiBr();
            }
            brojac++; 
        }
        if(!s.getIme().equals("")){
            if(brojac>0){
                upit = upit+" AND Ime = '"+s.getIme()+"'";
                
            }else{
                upit = upit+"(Ime='"+s.getIme()+"'";
            }
            brojac++;
        }
        
        if(!s.getPrezime().equals("")){
            if(brojac>0){
                upit = upit+" AND Prezime = '"+s.getPrezime()+"'";
                
            }else{
                upit = upit+"(Prezime = '"+s.getPrezime()+"'";
            }
            brojac++; 
        }
        upit=upit+")";
        return upit;
    }

    @Override
    protected void izbrisi(DomenskiObjekat dom) {
        try{
            Student s = (Student) dom;
        String upit = kreirajUpitZaDelete(s);
        db.obrisi(upit);
        }catch (Exception ex){
            
        }
        
        
    }
    
    public String kreirajUpitZaDelete (Student s) {
        return "DELETE FROM "+s.vratiNazivTabele()+" WHERE JMBG=" + s.getJMBG();
    }

}
