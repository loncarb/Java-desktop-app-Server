/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import db.RadSaBazom;
import domen.DomenskiObjekat;
import domen.Mesto;
import domen.PolozenIspit;
import domen.Proizvod;
import domen.Racun;
import domen.Sluzbenik;
import domen.StavkaRacuna;
import domen.Student;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sistemskeOperacije.SOIspit;
import sistemskeOperacije.SOMesto;
import sistemskeOperacije.SOPrijaviIspit;
import sistemskeOperacije.SOProizvod;
import sistemskeOperacije.SOSacuvajRacun;
import sistemskeOperacije.SOSacuvajStavkuRacuna;
import sistemskeOperacije.SOSacuvajStudenta;
import sistemskeOperacije.SOSluzbenik;
import sistemskeOperacije.SistemskaOperacija;
import transfer.TObjekat;

/**
 *
 * @author user
 */
public class Server extends Thread{

    private ObjectInputStream inSoket;

    /**
     * @param args the command line arguments
     */
    

    Socket soket;

    public void setSoket(Socket soket) {
        this.soket = soket;
    }
    
    @Override
    public void run() {
        try {
            pokreniKomunikaciju(soket);
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void pokreni() throws SQLException, Exception {
        try {
            ServerSocket ssoket = new ServerSocket(9000);
            Socket soket = ssoket.accept();
            pokreniKomunikaciju(soket);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void pokreniKomunikaciju(Socket soket) throws IOException, ClassNotFoundException, SQLException, Exception {
        while (true) {

            inSoket = new ObjectInputStream(soket.getInputStream());

            TObjekat zahtev = (TObjekat) inSoket.readObject();

            String poruka = zahtev.getPoruka();
            izvrsiOperaciju(zahtev, soket);

        }
    }

    public void izvrsiOperaciju(TObjekat zahtev, Socket soket) throws IOException, SQLException, Exception {

        String poruka = zahtev.getPoruka();

        try {
            if (poruka.equals("student")) {
                SistemskaOperacija opsta = new SOSacuvajStudenta();
                TObjekat odgovor;
                try {
                    opsta.izvrsi((DomenskiObjekat) zahtev.getObjekat());
                    odgovor = new TObjekat(null, "ok");
                } catch (Exception ex) {
                    odgovor = new TObjekat(null, "greska");
                }

                ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
                out.writeObject(odgovor);
            }

            if (poruka.equals("prijavi")) {
                TObjekat odgovor;
                try{
                    SistemskaOperacija opsta = new SOPrijaviIspit();
                opsta.izvrsi((DomenskiObjekat) zahtev.getObjekat());
                odgovor = new TObjekat(null, "ok");
                }catch(Exception ex){
                    odgovor = new TObjekat(null, "greska");
                }
                
                ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
                out.writeObject(odgovor);

            }

            if (poruka.equals("vratiSveStudente")) {
                SistemskaOperacija opsta = new SOSacuvajStudenta();
                List<DomenskiObjekat> lista = opsta.vratiSve((DomenskiObjekat) zahtev.getObjekat());
                List<Student> listaStudenata = (List<Student>) ((List<?>) lista);
                TObjekat odgovor = new TObjekat(listaStudenata, "ok");
                ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
                out.writeObject(odgovor);
            }

            if (poruka.equals("vratiMesta")) {

                SistemskaOperacija opsta = new SOMesto();
                List<DomenskiObjekat> lista = opsta.vratiSve((DomenskiObjekat) zahtev.getObjekat());

                List<Mesto> listaMesta = (List<Mesto>) ((List<?>) lista);
                TObjekat odgovor = new TObjekat(listaMesta, "ok");
                ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
                out.writeObject(odgovor);

            }

            if (poruka.equals("polozeniIspiti")) {
                SistemskaOperacija opsta = new SOPrijaviIspit();
                List<DomenskiObjekat> lista = opsta.vratiSve((DomenskiObjekat) zahtev.getObjekat());

                List<Mesto> listaPolozenih = (List<Mesto>) ((List<?>) lista);
                TObjekat odgovor = new TObjekat(listaPolozenih, "ok");
                ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
                out.writeObject(odgovor);
            }

            if (poruka.equals("vratiProizvode")) {
                SistemskaOperacija opsta = new SOProizvod();
                List<DomenskiObjekat> lista = opsta.vratiSve((DomenskiObjekat) zahtev.getObjekat());

                List<Mesto> listaProizvoda = (List<Mesto>) ((List<?>) lista);
                TObjekat odgovor = new TObjekat(listaProizvoda, "ok");
                ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
                out.writeObject(odgovor);
            }

            if (poruka.equals("vratiSveIspite")) {
                SistemskaOperacija opsta = new SOIspit();
                List<DomenskiObjekat> lista = opsta.vratiSve((DomenskiObjekat) zahtev.getObjekat());

                List<Mesto> listaIspita = (List<Mesto>) ((List<?>) lista);
                TObjekat odgovor = new TObjekat(listaIspita, "ok");
                ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
                out.writeObject(odgovor);
            }

            if (poruka.equals("studentIzmeni")) {
                TObjekat odgovor;
                try {
                    Student s = (Student) zahtev.getObjekat();
                    int j = s.getJMBG();
                    System.out.println(j);
                    RadSaBazom rsb = new RadSaBazom();
                    inSoket = new ObjectInputStream(soket.getInputStream());
                    TObjekat zahtev2 = (TObjekat) inSoket.readObject();

                    Student s1 = (Student) zahtev2.getObjekat();

                    rsb.izmeni(s1, j);
                    odgovor = new TObjekat(null, "ok");
                } catch (Exception ex) {
                    odgovor = new TObjekat(null, "greska");
                }

                ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
                out.writeObject(odgovor);

            }

            if (poruka.equals("racun")) {
                SistemskaOperacija opsta = new SOSacuvajRacun();
                Racun r = (Racun) zahtev.getObjekat();
                opsta.izvrsi(r);
                System.out.println("racun snimljen");

                for (StavkaRacuna stavka : r.getListaStavki()) {
                    SistemskaOperacija opstaStavka = new SOSacuvajStavkuRacuna();
                    opstaStavka.izvrsi(stavka);
                }

                TObjekat odgovor = new TObjekat(null, "ok");
                ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
                out.writeObject(odgovor);
            }

//            if (poruka.equals("vratiMesta")) {
//
//                RadSaBazom rsb = new RadSaBazom();
//                List<Mesto> listaMesta = new ArrayList<>();
//                listaMesta = rsb.vratiSvaMesta();
//                TObjekat odgovor = new TObjekat(listaMesta, "ok");
//                ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
//                out.writeObject(odgovor);
//
//            }
            if (poruka.equals("sluzbenik")) {
                SistemskaOperacija opsta = new SOSluzbenik();
                List<DomenskiObjekat> lista = opsta.pronadji((DomenskiObjekat) zahtev.getObjekat());

                List<Sluzbenik> listaSluzbenika = (List<Sluzbenik>) ((List<?>) lista);
                TObjekat odgovor;
                if (listaSluzbenika.isEmpty()) {
                    odgovor = new TObjekat(listaSluzbenika, "greska");
                } else {
                    odgovor = new TObjekat(null, "ok");
                }
                ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
                out.writeObject(odgovor);
            }

            if (poruka.equals("nadji")) {
                SistemskaOperacija opsta = new SOSacuvajStudenta();
                List<DomenskiObjekat> lista = opsta.pronadji((DomenskiObjekat) zahtev.getObjekat());

                List<Student> listaStudenata = (List<Student>) ((List<?>) lista);

                TObjekat odgovor = new TObjekat(listaStudenata, "ok");
                ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
                out.writeObject(odgovor);
            }

//            if (poruka.equals("vratiSveStudente")) {
//                RadSaBazom rsb = new RadSaBazom();
//                List<Student> listaStudenata = rsb.vratiSveStudenta();
//                TObjekat odgovor = new TObjekat(listaStudenata, "ok");
//                ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
//                out.writeObject(odgovor);
//            }
            if (poruka.equals("obrisi")) {
                TObjekat odgovor;
                try {
                    SistemskaOperacija opsta = new SOSacuvajStudenta();
                    opsta.obrisi((DomenskiObjekat) zahtev.getObjekat());
                    odgovor = new TObjekat(null, "ok");
                } catch (Exception ex) {
                    odgovor = new TObjekat(null, "greska");
                }

                ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
                out.writeObject(odgovor);
            }

//                Student s = (Student) zahtev.getObjekat();
//                RadSaBazom rsb = new RadSaBazom();
//                if (rsb.obrisiStudenta(s)) {
//                    TObjekat odgovor = new TObjekat(null, "ok");
//                    ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
//                    out.writeObject(odgovor);
//                } else {
//                    TObjekat odgovor = new TObjekat(null, "greska");
//                    ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
//                    out.writeObject(odgovor);
//                }
//            }

            if (poruka.equals("brojRacuna")) {
                RadSaBazom rsb = new RadSaBazom();
                int i = rsb.vratiBrojRacuna();

                TObjekat odgovor = new TObjekat(i, "ok");
                ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
                out.writeObject(odgovor);

            }

//            if (poruka.equals("vratiProizvode")) {
//                RadSaBazom rsb = new RadSaBazom();
//                List<Proizvod> lista = rsb.vratiProizvode();
//                TObjekat odgovor = new TObjekat(lista, "ok");
//                for(Proizvod st: lista){
//                    System.out.println(st.getNaziv());
//                }
//                ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
//                out.writeObject(odgovor);
//            }
        } catch (Exception ex) {
            poruka = "greska";
            TObjekat odgovor = new TObjekat(null, "greska");
            ObjectOutputStream out = new ObjectOutputStream(soket.getOutputStream());
            out.writeObject(odgovor);
            ex.printStackTrace();
        }

    }
}
