/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.taquinplayer;

import entities.Player;
import integration.ServerIntegration;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        try {
            System.out.println("Ingresa el nombre del archivo a para jugar");
            Scanner sc = new Scanner(System.in);
            Player p = Player.readFile( /*sc.nextLine()*/ "4.txt" );
            p.solve();
            //ServerIntegration integration = new ServerIntegration( p );
            //integration.challenge();
            //integration.getTaquin();
            //integration.Solve();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
