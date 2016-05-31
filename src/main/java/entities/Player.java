/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import org.apache.commons.lang.SerializationUtils;
/**
 *
 * @author david
 */
public class Player implements Serializable {
    private static long seed = System.nanoTime();
    
    
    private String url;
    private int size;
    private int pId;
    private int oId;
    private Integer[][] taquin;     
    private Integer[][] taquinCopy;
    private String name;
    private int currentI;
    private int currentJ;

    
    public Player(String url, int size, int pId, int oId, String name, int currentI, int currentJ) {
        this.url = url;
        this.size = size;
        this.pId = pId;
        this.oId = oId;
        taquin = new Integer[ size ][ size ];
        taquinCopy = new Integer[ size ][ size ];
        taquin[ currentI ][ currentJ ] = null;
        this.name = name;
        this.currentI = currentI;
        this.currentJ = currentJ;
        for (int i = 0; i < taquin.length; i++) {
            taquinCopy[ i ] = taquin[ i ].clone();
        }
    }

    
    public List<Integer> getValidMoves(){
        ArrayList<Integer> valid_moves = new ArrayList<>();
        if( this.currentI > 0)
            valid_moves.add( 2 );
        if( this.currentI  < this.size - 1 )
            valid_moves.add( 3 );
        if (currentJ > 0)
            valid_moves.add( 1 );
        if( currentJ < this.size - 1)
            valid_moves.add( 0 );
        Collections.shuffle(valid_moves, new Random(seed));
        return valid_moves;
    }
    
    public static void printMatrix(Integer[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.printf("%4d", matrix[row][col]);
            }
            System.out.println();
        }
    }
    
    public void swap( int initialI, int initialJ, int endI, int endJ ){
        Integer copy = taquin[ initialI ][ initialJ ];
        taquin[ initialI ][ initialJ ] = taquin[ endI ][ endJ ];
        taquin[ endI ][ endJ ] = copy;
    }
    
    
    public void move(int direction, boolean fake) {
        if (direction == 0) {
            swap(currentI, currentJ, currentI, currentJ + 1);
            currentJ = currentJ + 1;
        } else if (direction == 1) {
            swap(currentI, currentJ, currentI, currentJ - 1);
            currentJ = currentJ - 1;
        } else if (direction == 2) {

            swap(currentI, currentJ, currentI - 1, currentJ);
            currentI = currentI - 1;

        } else if (direction == 3) {
            swap(currentI, currentJ, currentI + 1, currentJ);
            currentI = currentI + 1;
        }
    }
   
    
    public String solve() throws Exception{
        PriorityQueue<Movement> queue = new PriorityQueue<>();
        Movement previous = null;
        Movement best = null;
        ArrayList<Movement> estados = new ArrayList<>();
        HashMap<String, Movement> states = new HashMap<>();
        int totalMovimientos = 0;
        String camino = null;
        long iteraciones = 0;
        while( best == null || best.getExactTotalDistance() > 0 ){
            iteraciones++;
            if( best != null){
                previous = (Movement) SerializationUtils.clone( best );
            }
            List<Integer> moves = getValidMoves();
            totalMovimientos++;
            for (Integer movement : moves) {
                Player player_copy = (Player) SerializationUtils.clone( this );
                player_copy.move( movement , true);
                if( best != null ){
                    camino = best.getPath() + movement;
                    totalMovimientos = best.getMoveCount() + 1;
                }else{
                    camino = movement+"";
                    totalMovimientos = 1;
                }
                Movement move = new Movement( player_copy.taquin , camino , previous , player_copy.getCurrentI(), player_copy.getCurrentJ() , movement , totalMovimientos );
                
                int index = estados.indexOf( move );
                if( index == -1 ){
                    
                    if( states.containsKey( move.hashCode() +"")){
                        System.out.println("la RE CAGUE");
                         System.in.read();
                    }
                    queue.add(move);
                    estados.add(move);
                    states.put( move.hashCode()+"" , move);
                }else{
                    if(! states.containsKey( move.hashCode() +"" )){
                        System.out.println("la RE CAGUE METER");
                         System.in.read();
                    }
                    Movement real = estados.get( index );
                    move.setPath( real.getPath() );
                }
            }
            
            
            best = queue.poll();
            this.taquin = best.getTaquin();
            this.currentI = best.getI();
            this.currentJ = best.getJ();
            System.out.println( "Selecciona a una distancia de  " + best.getExactTotalDistance() );
            
            //System.in.read();
        }
        Player.printMatrix(taquin);
        System.out.println("Tamanio"+ best.getPath().length());
        System.out.println("iteraciones " + iteraciones);
        return best.getPath();
    }
    
    public Player() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public Integer[][] getTaquin() {
        return taquin;
    }

    public void setTaquin(Integer[][] taquin) {
        for (int i = 0; i < taquin.length; i++) {
            for (int j = 0; j < taquin.length; j++) {
                if( taquin[ i ][ j ]!= null){
                    taquinCopy[i][j] = (int)taquin[i][j];
                }
                
            }
        }
        this.taquin = taquin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentI() {
        return currentI;
    }

    public void setCurrentI(int currentI) {
        this.currentI = currentI;
    }

    public int getCurrentJ() {
        return currentJ;
    }

    public void setCurrentJ(int currentJ) {
        this.currentJ = currentJ;
    }

    private void cloneMatrix() {
       this.taquin = (Integer[][]) SerializationUtils.clone( this.taquin );
    }
    
    public static Player readFile( String fileName ) throws Exception{
         try (
            BufferedReader bf = new BufferedReader(new FileReader( fileName ));
         ) {
            String url = bf.readLine();
            Integer pid = Integer.parseInt( bf.readLine() );
            Integer oid = Integer.parseInt( bf.readLine() );
            String name = bf.readLine();
            int size = Integer.parseInt( bf.readLine() );
            String[] temp = bf.readLine().split(" ");
            int i = Integer.parseInt( temp[0] );
            int j = Integer.parseInt( temp[ 1 ] );
            Integer[][] taquin = new Integer[ size ][ size ];
             for (int k = 0; k < size; k++) {
                 String[] lineNumbers = bf.readLine().split(" ");
                 int cont = 0;
                 for (String lineNumber : lineNumbers) {
                     taquin[ k ][ cont ] = Integer.parseInt( lineNumber );
                     cont++;
                 } 
             }
             taquin[ i ][ j ] = null;
             printMatrix(taquin);
             Player p = new Player( url ,size , pid , oid , name , i , j );
             p.setTaquin( taquin );
             return p;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al leer el archivo ");
        }
    }
    
    public String[][] toStringMatrix(){
        String[][] matrix = new String[size][size];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[ i ][ j ] = this.taquin[ i ][ j ] + "";
            }
            
        }
        return matrix;
    }
    
}
