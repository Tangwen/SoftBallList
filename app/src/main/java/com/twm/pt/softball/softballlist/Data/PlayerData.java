package com.twm.pt.softball.softballlist.Data;

import com.twm.pt.softball.softballlist.component.Player;
import com.twm.pt.softball.softballlist.component.Position;

import java.util.ArrayList;

/**
 * Created by TangWen on 2015/4/23.
 */
public class PlayerData {
    ArrayList<Player> players = new ArrayList<Player>();

    private static PlayerData mPlayerData;
    public static PlayerData getInstance() {
        if(mPlayerData==null) {
            mPlayerData = new PlayerData();
        }
        return mPlayerData;
    }

    public ArrayList<Player> getPlayers() {
        players = new ArrayList<Player>();
        players.add(new Player("陽岱鋼", "YOH桑", "pic", "1", "R/R", Player.Fielder.outfielder, Position.LeftFielder));
        players.add(new Player("林益全", "第一神全", "pic", "9", "R/L", Player.Fielder.infielder, Position.ThirdBaseMan));
        players.add(new Player("彭政閔", "恰恰", "pic", "23", "R/R", Player.Fielder.infielder, Position.FirstBaseMan));
        players.add(new Player("陳金鋒", "鋒哥", "pic", "52", "R/R", Player.Fielder.outfielder, Position.ExtraPlayer));
        players.add(new Player("林智勝", "乃耀．阿給", "pic", "31", "R/R", Player.Fielder.infielder, Position.ShortShop));
        players.add(new Player("林泓育", "小胖", "pic", "11", "R/R", Player.Fielder.infielder, Position.Catcher));
        players.add(new Player("林哲瑄", "釣蝦瑄", "pic", "24", "R/R", Player.Fielder.outfielder, Position.Catcher));
        players.add(new Player("周思齊", "周董", "pic", "16", "L/L", Player.Fielder.outfielder, Position.ShortFielder));
        players.add(new Player("張建銘", "火哥", "pic", "66", "L/L", Player.Fielder.outfielder, Position.RightFielder));
        players.add(new Player("郭嚴文", "超級喜歡", "pic", "21", "R/L", Player.Fielder.infielder, Position.SecondBaseMan));
        players.add(new Player("潘威倫", "嘟嘟", "pic", "18", "R/R", Player.Fielder.pitcher, Position.Pitcher));
        return players;
    }

}
