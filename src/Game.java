public class Game {
    public String host = null;
    public String roomID = null;
    public Integer time = null;
    public Boolean useCardBoard = null;
    public Integer treasure = null;
    public Integer status = 0;
    public String teamA = "";
    public String teamB = "";

    public Game( String host , String roomID , String time , String useCardBoard , String treasure ) {
        this.host = host;
        this.roomID = roomID;
        this.time = Integer.parseInt(time);
        this.useCardBoard = Boolean.parseBoolean(useCardBoard);
        this.treasure = Integer.parseInt(treasure);
    }

    public String showInfo() {
        return roomID+"ROOM:Time:" + time + ",CardBoard:" + useCardBoard + ",Treasure:" + treasure + ",TeamA:" + teamA + ",TeamB:" + teamB + ",Status:" + status;
    }

    public void addPlayer( String player ) {
        if(!( teamA.contains(player) || teamB.contains(player) )) {
            if( teamA.split(";").length+(teamA.isEmpty()?0:1)<= teamA.split(";").length+(teamB.isEmpty()?0:1)) {
                teamA+=(teamA.isEmpty()?"":";")+player;
            } else {
                teamB+=(teamB.isEmpty()?"":";")+player;
            }
        }
    }
    public void removePlayer( String player ) {
        if( teamA.contains(player) ) {
            teamA = teamA.replaceAll(";"+player,"").replaceAll(player,"");
        } else if( teamB.contains(player) ) {
            teamB = teamB.replaceAll(";"+player,"").replaceAll(player,"");
        }
    }

}
