import java.util.ArrayList;

public class Game {
    public String host = null;
    public String roomID = null;
    public Integer time = null;
    public Boolean useCardBoard = null;
    public Integer treasure = null;
    public Integer status = 0;
    public String teamA = "";
    public String teamB = "";
    public Integer ready = 0;
    public ArrayList<String> seek = new ArrayList<String>();

    public Game(String host, String roomID, String time, String useCardBoard, String treasure) {
        this.host = host;
        this.roomID = roomID;
        this.time = Integer.parseInt(time);
        this.useCardBoard = Boolean.parseBoolean(useCardBoard);
        this.treasure = Integer.parseInt(treasure);
    }

    public String showInfo() {
        return roomID+"ROOM:Time:" + time + ",CardBoard:" + useCardBoard + ",Treasure:" + treasure + ",TeamA:" + teamA + ",TeamB:" + teamB + ",Status:" + status;
    }

    public void addPlayer(String player) {
        if (!(teamA.contains(player) || teamB.contains(player) || host.equals(player)) && status == 0) {
            if( teamA.split(";").length+(teamA.isEmpty()?0:1)<= teamA.split(";").length+(teamB.isEmpty()?0:1)) {
                teamA+=(teamA.isEmpty()?"":";")+player;
            } else {
                teamB+=(teamB.isEmpty()?"":";")+player;
            }
        }
    }

    public void removePlayer(String player) {
        if( teamA.contains(player) ) {
            teamA = teamA.replaceAll(";"+player,"").replaceAll(player,"");
        } else if( teamB.contains(player) ) {
            teamB = teamB.replaceAll(";"+player,"").replaceAll(player,"");
        }
    }

    public boolean checkReady() {
        ready++;
        int total = 1;
        if (!teamA.isEmpty()) {
            total += teamA.split(";").length;
        }
        if (!teamB.isEmpty()) {
            total += teamB.split(";").length;
        }
        return ready == total;
    }

    public boolean checkSeek(String s) {
        if (seek.contains(s)) {
            return false;
        } else {
            seek.add(s);
            return true;
        }
    }
}
