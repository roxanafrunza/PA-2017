import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MyBot {
    public static void main(String[] args) throws java.io.IOException {

        final InitPackage iPackage = Networking.getInit();
        final int myID = iPackage.myID;
        final GameMap gameMap = iPackage.map;
		MyMap myMap = new MyMap(myID,gameMap);
		Networking.sendInit("4PArtitions Bot");
        while(true) {
            List<Move> moves = new ArrayList<Move>();
            Networking.updateFrame(gameMap);
            myMap.myPieces = myMap.getMap();
            for ( int i = 0 ; i < myMap.myPieces.size(); i++){
            	Location currentLocation = myMap.myPieces.get(i);
            	Move mv = myMap.calculateMove(currentLocation);
            	moves.add(mv);
            	
            }
            Networking.sendFrame(moves);
        }
    }
}
