/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import domen.Adresa;
import domen.DomenskiObjekat;
import domen.Ispit;
import domen.Mesto;
import domen.PolozenIspit;
import domen.Proizvod;
import domen.Racun;
import domen.Sluzbenik;
import domen.Student;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class RadSaBazom {

    Connection konekcija;

    public void otvoriTransakciju() throws Exception {
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

        konekcija = DriverManager.getConnection("jdbc:odbc:studentskaSluzba");
        konekcija.setAutoCommit(false);
    }

    public void potvrdiTransakciju() throws SQLException {
        konekcija.commit();
        konekcija.close();
        System.out.println("Uradjen commit.");
    }

    public void ponistiTransakciju() throws SQLException {
        if (konekcija != null) {
            konekcija.rollback();
            konekcija.close();
        }
        System.out.println("Uradjen rollback.");
    }

    public ResultSet vratiDomObj (DomenskiObjekat dom) throws SQLException {
        konekcija = DriverManager.getConnection("jdbc:odbc:studentskaSluzba");
        
        String upit = "SELECT * FROM "+dom.vratiNazivTabeleZaJoin();
        
        Statement s = konekcija.createStatement();
        ResultSet rs = s.executeQuery(upit);
        ResultSet rss =rs;
        
        return rss;
        
    }

    
    
    public void sacuvaj(DomenskiObjekat domenskiObjekat) throws ClassNotFoundException, SQLException, Exception {

        try {
            String upit = "INSERT INTO " + domenskiObjekat.vratiNazivTabele() + " VALUES (" + domenskiObjekat.vratiVrednostiZaInsert() + ")";
            Statement s = konekcija.createStatement();
            s.executeUpdate(upit);
            System.out.println("Uspesno je odradjena operacija");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Operacija cuvanja nije izvrsena!");
        }

    }
    
    public void izmeni (DomenskiObjekat domenskiObjekat, int jmbg) throws ClassNotFoundException, SQLException, Exception {
        try{
            BigDecimal b = new BigDecimal(jmbg);
            konekcija = DriverManager.getConnection("jdbc:odbc:studentskaSluzba");
            Student s = (Student) domenskiObjekat;
            int jmbgNovi = s.getJMBG();
            String ime = s.getIme();
            String prezime = s.getPrezime();
            String brIndeksa = s.getBrIndeksa();
            String ulica = s.getAdresa().getUlica();
            String broj = s.getAdresa().getBroj();
            Mesto m = s.getAdresa().getMesto();
            
             String upit = "UPDATE " + domenskiObjekat.vratiNazivTabele() + " SET JMBG= "+jmbgNovi+", Ime= '"+ime+"', Prezime= '"+prezime+"', "
                     + "BrIndeksa= '"+brIndeksa+"', Ulica= '"+ulica+"', Broj= '"+broj+"', Mesto= "+m.getPostanskiBr()+" WHERE JMBG= "+jmbg;
            System.out.println(upit);
            Statement state = konekcija.createStatement();
            
            state.executeUpdate(upit);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new Exception("greska");
        }
    }

//    public List<Mesto> vratiSvaMesta() throws SQLException {
//        List<Mesto> listaMesta = new ArrayList<>();
//        konekcija = DriverManager.getConnection("jdbc:odbc:studentskaSluzba");
//        String upit = "SELECT * FROM Mesto";
//        Statement s = konekcija.createStatement();
//        ResultSet rs = s.executeQuery(upit);
//        while (rs.next()) {
//
//            String naziv = rs.getString("Naziv");
//            long ptt = rs.getLong("PostanskiBroj");
//            Mesto m = new Mesto(naziv, ptt);
//            listaMesta.add(m);
//        }
//        konekcija.close();
//        return listaMesta;
//    }
    
//    public List<Student> vratiSveStudenta() throws SQLException {
//        List<Student> listaStudenata = new ArrayList<>();
//        konekcija = DriverManager.getConnection("jdbc:odbc:studentskaSluzba");
//        String upit = "SELECT * FROM Mesto INNER JOIN Student ON Mesto.PostanskiBroj = Student.Mesto";
//        Statement st = konekcija.createStatement();
//        ResultSet rs = st.executeQuery(upit);
//        while (rs.next()) {
//            Student s = new Student();
//            s.setJMBG((int) rs.getLong("JMBG"));
//            s.setIme(rs.getString("Ime"));
//            s.setPrezime(rs.getString("Prezime"));
//            s.setBrIndeksa(rs.getString("BrIndeksa"));
//
//            Adresa a = new Adresa();
//            a.setBroj(rs.getString("Broj"));
//            a.setUlica(rs.getString("Ulica"));
//
//            Mesto m = new Mesto();
//            m.setNaziv(rs.getString("Naziv"));
//            m.setPostanskiBr(rs.getLong("PostanskiBroj"));
//
//            a.setMesto(m);
//            s.setAdresa(a);
//            listaStudenata.add(s);
//        }
//        konekcija.close();
//        return listaStudenata;
//    }

//    public boolean nadjiSluzbenika(Sluzbenik sluzbenik) throws SQLException {
//        String korisnicko = sluzbenik.getKorisnickoIme();
//        String sifra = sluzbenik.getKorisnickaSifra();
//        konekcija = DriverManager.getConnection("jdbc:odbc:studentskaSluzba");
//        String upit = "SELECT * FROM Sluzbenik WHERE KorisnickoIme='" + korisnicko + "' AND KoorisnickaSifra='" + sifra + "'";
//        Statement s = konekcija.createStatement();
//        ResultSet rs = s.executeQuery(upit);
//        if (rs.next()) {
//            String kor = rs.getString("KorisnickoIme");
//            String sif = rs.getString("KoorisnickaSifra");
//            if (kor.equals(korisnicko) && sif.equals(sifra)) {
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            return false;
//        }
//    }

    public ResultSet nadji (DomenskiObjekat dom, String upit) throws SQLException {
        konekcija = DriverManager.getConnection("jdbc:odbc:studentskaSluzba");
        Statement s = konekcija.createStatement();
        ResultSet rs = s.executeQuery(upit);
        return rs;
    }

//    public List<PolozenIspit> vratiPolozeneIspite(Student s) throws SQLException {
//        List<PolozenIspit> listaIspita = new ArrayList<>();
//        konekcija = DriverManager.getConnection("jdbc:odbc:studentskaSluzba");
//        String upit = "SELECT * FROM Ispit INNER JOIN PolozenIspit ON Ispit.IspitID = PolozenIspit.Ispit WHERE PolozenIspit.Ocena > 5 AND PolozenIspit.Student=" + s.getJMBG();
//        Statement st = konekcija.createStatement();
//        ResultSet rs = st.executeQuery(upit);
//        
//        while (rs.next()) {
//            ;
//            PolozenIspit p = new PolozenIspit();
//            int ocena = rs.getInt("Ocena");
//            Ispit i = new Ispit();
//            i.setIspitID(rs.getInt("IspitID"));
//            i.setNaziv(rs.getString("Naziv"));
//            p.setOcena(ocena);
//            p.setIspit(i);
//            listaIspita.add(p);
//        }
//        konekcija.close();
//        return listaIspita;
//    }

    public boolean obrisi(String up) {
        try {
            konekcija = DriverManager.getConnection("jdbc:odbc:studentskaSluzba");
            
            Statement st = konekcija.createStatement();
            st.executeUpdate(up);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public int vratiBrojRacuna() throws SQLException {
        konekcija = DriverManager.getConnection("jdbc:odbc:studentskaSluzba");
        String upit = "SELECT * FROM Racun ORDER BY RacunID DESC";
        Statement st = konekcija.createStatement();
        ResultSet rs = st.executeQuery(upit);
        if (rs.next()) {
            int i = rs.getInt("RacunID");

            return i;

        } else {
            return 0;
        }
    }

    public List<Proizvod> vratiProizvode() throws SQLException {
        List<Proizvod> lista = new ArrayList<>();
        konekcija = DriverManager.getConnection("jdbc:odbc:studentskaSluzba");
        String upit = "SELECT * FROM Proizvod";
        Statement s = konekcija.createStatement();
        ResultSet rs = s.executeQuery(upit);
        while (rs.next()) {

            String naziv = rs.getString("Naziv");
            double c = rs.getDouble("Cena");
            int sif = rs.getInt("SifraProizvoda");
            Proizvod p = new Proizvod(sif, naziv, c);
            lista.add(p);
        }
        konekcija.close();
        return lista;
    }

    public boolean prijaviIspit(PolozenIspit ispit) {
        try {
            konekcija = DriverManager.getConnection("jdbc:odbc:studentskaSluzba");
            String upit = "INSERT INTO PolozenIspit VALUES (?,?,?,?,?)";
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setBigDecimal(1, new BigDecimal(ispit.getIspit().getIspitID()));
            ps.setBigDecimal(2, new BigDecimal(ispit.getStudent().getJMBG()));
            ps.setString(3, ispit.getSluzbenik().getKorisnickoIme());
            ps.setBigDecimal(4, new BigDecimal(0));
            ps.setBoolean(5, true);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
