public class GameThread extends Thread {
    private String roomID;

    public GameThread(String roomID) {
        super();
        this.roomID = roomID;
    }

    @Override
    public void run() {
        try {
            ARHideAndSeekServer.server.broadcast(roomID + "PLAY:Ready");
            System.out.println(roomID + "PLAY:Ready");
            for (int i = 3; i >= 0; i--) {
                Thread.sleep(1000);
                ARHideAndSeekServer.server.broadcast(roomID + "PLAY:" + i);
                System.out.println(roomID + "PLAY:" + i);
            }
            Thread.sleep(1000);
            ARHideAndSeekServer.server.broadcast(roomID + "PLAY:Start");
            System.out.println(roomID + "PLAY:Start");
            ARHideAndSeekServer.room.get(roomID).status = 3;
            while (ARHideAndSeekServer.room.get(roomID).treasure >= 0) {
                Thread.sleep(1000);
                ARHideAndSeekServer.server.broadcast(roomID + "PLAY:" + ARHideAndSeekServer.room.get(roomID).time);
                System.out.println(roomID + "PLAY:" + ARHideAndSeekServer.room.get(roomID).time);
                if (ARHideAndSeekServer.room.get(roomID).time > 0) {
                    ARHideAndSeekServer.room.get(roomID).time--;
                } else {
                    break;
                }
            }
            Thread.sleep(1000);
            ARHideAndSeekServer.server.broadcast(roomID + "FINISH");
            System.out.println(roomID + "FINISH");
            ARHideAndSeekServer.room.get(roomID).status = 4;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
