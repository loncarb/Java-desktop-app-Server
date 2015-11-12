/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Server1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Server1 server = new Server1();
        try {
            server.pokreni();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void pokreni() throws SQLException, Exception {
        try {
            ServerSocket ssoket = new ServerSocket(9000);
                
            while (true) {
                
                Socket soket = ssoket.accept();
                Server s = new Server();
                s.setSoket(soket);
                s.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
