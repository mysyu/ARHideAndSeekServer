import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class ARHideAndSeekServer extends WebSocketServer {

    public static ARHideAndSeekServer server = new ARHideAndSeekServer();
    public static Map<String,Game> room = new HashMap<String,Game>();

    public static void main(String[] argv) {
        server.start();
    }

    public ARHideAndSeekServer() {
        super(new InetSocketAddress("mysyu.ddns.net", 666));
    }
    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println(webSocket.getRemoteSocketAddress().getAddress().getHostAddress()+" Connecting...");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {

    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        try {
            System.out.println(s);
            if (s.length() >= 10) {
                String roomID = s.substring(0, 10);
                s = s.substring(10);
                if (s.startsWith("HOST:")) {
                    String[] info = s.split(",");
                    room.put(roomID, new Game(info[0].split(":")[1], roomID, info[1].split(":")[1], info[2].split(":")[1], info[3].split(":")[1]));
                    System.out.println(room.get(roomID).showInfo());
                    broadcast(room.get(roomID).showInfo());
                } else if (s.startsWith("PLAYER:")) {
                    s = s.replaceFirst("PLAYER:", "");
                    if (room.containsKey(roomID)) {
                        room.get(roomID).addPlayer(s);
                        System.out.println(room.get(roomID).showInfo());
                        broadcast(room.get(roomID).showInfo());
                    } else {
                        System.out.println(roomID + "NOTFIND");
                        broadcast(roomID + "NOTFIND");
                    }
                } else if (s.equals("START") && room.containsKey(roomID) && room.get(roomID).status == 0) {
                    room.get(roomID).status = 1;
                    System.out.println(roomID + s);
                    broadcast(roomID + s);
                } else if (s.startsWith("HIDE") && room.containsKey(roomID) && room.get(roomID).status == 1) {
                    System.out.println(roomID + s);
                    broadcast(roomID + s.replace("bench", "keyboard"));
                } else if (s.startsWith("SEEK") && room.containsKey(roomID) && room.get(roomID).status == 3) {
                    System.out.println(roomID + s);
                    broadcast(roomID + s);
                } else if (s.equals("PLAY") && room.containsKey(roomID) && room.get(roomID).checkReady()) {
                    room.get(roomID).status = 2;
                    System.out.println(roomID + s);
                    new GameThread(roomID).start();
                } else if (s.startsWith("LEAVE:") && room.containsKey(roomID) && room.get(roomID).status == 0) {
                    s = s.replaceFirst("LEAVE:", "");
                    room.get(roomID).removePlayer(s);
                    System.out.println(room.get(roomID).showInfo());
                    broadcast(room.get(roomID).showInfo());
                } else if (s.equals("EXIT") && room.containsKey(roomID) && room.get(roomID).status == 0) {
                    System.out.println(roomID + s);
                    broadcast(roomID + s);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Start Server...");
    }
}
