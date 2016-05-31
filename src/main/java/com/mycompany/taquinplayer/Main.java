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
            Player p1 = Player.readFile( /*sc.nextLine()*/ "3.txt" );
            p1.shuffle();
            System.out.println( p1.toString() );
            ServerIntegration integration = new ServerIntegration( p1 );
            integration.challenge();
            integration.getTaquin();
            integration.Solve();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
