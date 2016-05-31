/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import entities.Player;
import java.io.IOException;

/**
 *
 * @author david
 */
public class ServerIntegration {
    /**
     * las siguientes constantes represtan token para cambia en las url
     */
    private final static String REPLACE_SERVER_LOCATION = ":server:";
    private final static String REPLACE_PID = ":pid:";
    private final static String REPLACE_PLAYER_NAME = ":name:";
    
    /**
     * las siguientes constantes represtan las urls de los servidores
     */
    private final static String BASE_PATH = REPLACE_SERVER_LOCATION + "/api/" ;
    private final static String GET_BOARD_PATH = BASE_PATH + "board/" + REPLACE_PID + "/";
    private final static String CHALLENGE_PATH = BASE_PATH + "player/" + REPLACE_PID + "/challenge/";
    private final static String CREATE_BOARD_PATH =  BASE_PATH +"board/new/";
    private final static String CREATE_PLAYER_PATH = BASE_PATH + "player/" + REPLACE_PID + "/new/" +REPLACE_PLAYER_NAME;
    
    private final static String MOVE_RIGHT_PATH = BASE_PATH + "player/" + REPLACE_PID +"/board/move/right";
    private final static String MOVE_LEFT_PATH = BASE_PATH + "player/" + REPLACE_PID +"/board/move/left";
    private final static String MOVE_UP_PATH = BASE_PATH + "player/" + REPLACE_PID +"/board/move/up";
    private final static String MOVE_DOWN_PATH = BASE_PATH + "player/" + REPLACE_PID +"/board/move/down";
    /**
     * las siguientes constantes representan los movimientos
     */

     private final static String DOWN = "3";
     private final static String UP = "2";
     private final static String LEFT = "1";
     private final static String RIGHT = "0";
     
     
    private Player player;
    private Client client;
    public ServerIntegration( Player player ){
        this.player  = player;
        client = Client.create();
        createBoard();
        createPlayer();
    }
    
    public final void createPlayer(){
        String url = CREATE_PLAYER_PATH
                .replace( REPLACE_SERVER_LOCATION , player.getUrl() )
                .replace( REPLACE_PID , player.getpId()+"" )
                .replace( REPLACE_PLAYER_NAME , player.getName() );
        WebResource webResource = client.resource(url);
        webResource.type("application/json").post(  );
                
    }
    public final void createBoard(){
        String url = CREATE_BOARD_PATH.replace(REPLACE_SERVER_LOCATION, player.getUrl() );
        WebResource webResource = client.resource(url);
        Board challenge = new Board();
        challenge.setCurrentState( player.toStringMatrix() );
        challenge.setMovements( 0 );
        challenge.setBlank( new Piece( player.getCurrentI(), player.getCurrentJ() ));
        
        challenge.setCurrentState( player.toStringMatrix() );
        Gson gson = new Gson();
        String json =  gson.toJson( challenge ) ;
        webResource.type("application/json").post( json );
    }
    public void challenge(){
        String url = CHALLENGE_PATH.replace(REPLACE_SERVER_LOCATION, player.getUrl() ).replace( REPLACE_PID, player.getoId()+"" );
        WebResource webResource = client.resource( url );
        Board challenge = new Board();
        challenge.setCurrentState( player.toStringMatrix() );
        challenge.setBlank( new Piece( player.getCurrentI(), player.getCurrentJ() ));
        challenge.setCurrentState( player.toStringMatrix() );
        Gson gson = new Gson();
        String json =  gson.toJson( challenge ) ;
        webResource.type("application/json").post( json );
    }
    
    public Board getTaquin(  ) throws IOException, Exception{
        System.out.println("¿El reto esta listo?");
        System.in.read();
        String url = GET_BOARD_PATH.replace( REPLACE_SERVER_LOCATION , player.getUrl() ).replace( REPLACE_PID , player.getpId()+"" );
        WebResource webResource = client.resource( url );
        ClientResponse response = webResource.accept("application/json")
                                   .get(ClientResponse.class);
        String taquinString = response.getEntity(String.class);
        Gson gson = new Gson();
        Board taquin = gson.fromJson( taquinString , Board.class );
        player.setCurrentI( taquin.getBlank().getRow() );
        player.setCurrentJ( taquin.getBlank().getColumn() );
        player.setTaquin( taquin.toIntegerMatrix() );
        Player.printMatrix( player.getTaquin() );
        return taquin;    
    }
    
    public void moveUp(){
        String url = MOVE_UP_PATH
                .replace( REPLACE_SERVER_LOCATION , player.getUrl() )
                .replace( REPLACE_PID , player.getpId()+"" );
        WebResource webResource = client.resource( url );
        webResource.type("application/json").post(  );
    }
    public void moveDown(){
        String url = MOVE_DOWN_PATH
                .replace( REPLACE_SERVER_LOCATION , player.getUrl() )
                .replace( REPLACE_PID , player.getpId()+"" );
        WebResource webResource = client.resource( url );
        webResource.type("application/json").post(  );
    }
    public void moveLeft(){
        String url = MOVE_LEFT_PATH
                .replace( REPLACE_SERVER_LOCATION , player.getUrl() )
                .replace( REPLACE_PID , player.getpId()+"" );
        WebResource webResource = client.resource( url );
        webResource.type("application/json").post(  );
    }
    public void moveRight(){
        String url = MOVE_RIGHT_PATH
                .replace( REPLACE_SERVER_LOCATION , player.getUrl() )
                .replace( REPLACE_PID , player.getpId()+"" );
        WebResource webResource = client.resource( url );
        webResource.type("application/json").post(  );
    }
    
    public void Solve() throws Exception{
        System.out.println("mis posiciones  " + player.getCurrentI() + player.getCurrentJ());
        String moves = player.solve();
        System.out.println( moves );
        System.in.read();
        for (int i = 0; i < moves.length(); i++) {
            String move = moves.charAt( i )+"";
            switch(move){
                case DOWN:
                    this.moveDown();
                    break;
                case UP:
                    this.moveUp();
                    break;
                case LEFT:
                    this.moveLeft();
                    break;
                case RIGHT:
                    this.moveRight();
                    break;
            }
        }
    }
}
