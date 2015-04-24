package com.twm.pt.softball.softballlist.Data;

import com.twm.pt.softball.softballlist.component.Player;
import com.twm.pt.softball.softballlist.component.Position;

import java.util.ArrayList;

/**
 * Created by TangWen on 2015/4/23.
 */
public class PlayerData {
    ArrayList<Player> players = new ArrayList<Player>();
    public String picPath = "/Pictures/SoftBall/";

    private static PlayerData mPlayerData;
    public static PlayerData getInstance() {
        if(mPlayerData==null) {
            mPlayerData = new PlayerData();
        }
        return mPlayerData;
    }

    public PlayerData() {
        initPlayerData();
    }

    private void initPlayerData() {
        //TODO load Players form json

        if(players.size()==0) {
            players.add(new Player("陽岱鋼", "YOH桑", "images1.jpg", "1", "R/R", Player.Fielder.outfielder, Position.LeftFielder));
            players.add(new Player("林益全", "第一神全", "images9.jpg", "9", "R/L", Player.Fielder.infielder, Position.ThirdBaseMan));
            players.add(new Player("彭政閔", "恰恰", "images23.jpg", "23", "R/R", Player.Fielder.infielder, Position.FirstBaseMan));
            players.add(new Player("陳金鋒", "鋒哥", "images52.jpg", "52", "R/R", Player.Fielder.outfielder, Position.ExtraPlayer));
            players.add(new Player("林智勝", "乃耀．阿給", "images31.jpg", "31", "R/R", Player.Fielder.infielder, Position.ShortShop));
            players.add(new Player("林泓育", "小胖", "images11.jpg", "11", "R/R", Player.Fielder.infielder, Position.Catcher));
            players.add(new Player("林哲瑄", "釣蝦瑄", "images24.jpg", "24", "R/R", Player.Fielder.outfielder, Position.CenterFielder));
            players.add(new Player("周思齊", "周董", "images16.jpg", "16", "L/L", Player.Fielder.outfielder, Position.ShortFielder));
            players.add(new Player("張建銘", "火哥", "images66.jpg", "66", "L/L", Player.Fielder.outfielder, Position.RightFielder));
            players.add(new Player("郭嚴文", "超級喜歡", "images21.jpg", "21", "R/L", Player.Fielder.infielder, Position.SecondBaseMan));
            players.add(new Player("潘威倫", "嘟嘟", "images18.jpg", "18", "R/R", Player.Fielder.pitcher, Position.Pitcher));

            Player player = null;
            player = new Player("張正偉", "花花", "images59.jpg", "59", "L/L", Player.Fielder.outfielder, Position.BenchPlayer);
            player.setPresent(false);
            players.add(player);
            player = new Player("陳江和", "紅龜", "images8.jpg", "8", "R/R", Player.Fielder.infielder, Position.BenchPlayer);
            player.setPresent(false);
            players.add(player);
            player = new Player("郭泓志", "小小郭", "images51.jpg", "51", "L/L", Player.Fielder.pitcher, Position.BenchPlayer);
            player.setPresent(false);
            players.add(player);


            //TODO save Players into json
        }
    }


    public ArrayList<Player> getAllPlayers() {
        return players;
    }
    public void setAllPlayers(ArrayList<Player> players) {
        this.players = players;
    }

}
