package com.twm.pt.softball.softballlist.Manager;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.twm.pt.softball.softballlist.component.Player;
import com.twm.pt.softball.softballlist.component.Position;
import com.twm.pt.softball.softballlist.utility.L;
import com.twm.pt.softball.softballlist.utility.PreferenceUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by TangWen on 2015/4/23.
 */
public class PlayerDataManager {
    public static String picPath = "/Pictures/SoftBall/";
    public static final Type arrayListPlayerType = new TypeToken<ArrayList<Player>>() {}.getType();
    public static final String BP_number = "444";

    private String sharedPreferencesKey = "PlayerData";
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<onPlayerChangeListener> mAllPlayerListener = new ArrayList<>();
    private ArrayList<onPlayerChangeListener> mOrderPlayerListener = new ArrayList<>();
    private Context mContext;

    private static PlayerDataManager mPlayerDataManager;
    public static PlayerDataManager getInstance(Context mContext) {
        if(mPlayerDataManager ==null) {
            mPlayerDataManager = new PlayerDataManager(mContext);
        }
        return mPlayerDataManager;
    }

    public PlayerDataManager(Context mContext) {
        this.mContext = mContext;
        initPlayerData();
    }

    public void LoadPlayers() {
        players = PreferenceUtils.JsonToObject(PreferenceUtils.getValue(mContext, sharedPreferencesKey, ""), arrayListPlayerType);
    }
    public void SavePlayer() {
        PreferenceUtils.setValue(mContext, sharedPreferencesKey, PreferenceUtils.ObjectToJson(players));
    }

    private void initPlayerData() {
        LoadPlayers();
        if(players==null) {
            players = new ArrayList<Player>();
        }

        if(players.size()==0) {
            L.d("players.size()==0");
            players = new ArrayList<Player>();
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

            player = new Player("預備球員(BP)", "預備BAR", "", BP_number, "A/A", Player.Fielder.all_fielder, Position.BenchPlayer);
            player.setPresent(true);
            players.add(player);

            SavePlayer();
        }
    }


    public ArrayList<Player> getAllPlayers() {
        ArrayList<Player> allPlayers = new ArrayList<Player>();
        for(Player mPlayer : players) {
            if(!mPlayer.number.equals(BP_number)) { //去除預備球員bar
                allPlayers.add(mPlayer);
            }
        }
        allPlayers = sortByNumber(allPlayers);
        return allPlayers;
    }
    public void setAllPlayers(ArrayList<Player> allPlayers) {
        for(Player mPlayer : players) {
            if(mPlayer.number.equals(BP_number)) {
                allPlayers.add(mPlayer);
            }
        }
        players = allPlayers;
        players = sortByNumber(players);
        SavePlayer();
        notifyAllPlayersOnChangeListener();
    }
    public void add_AllPlayers(Player mPlayer) {
        players.add(mPlayer);
        players = sortByNumber(players);
        SavePlayer();
        notifyAllPlayersOnChangeListener();
    }
    public void modify_AllPlayers(Player mPlayer) {
        int index = players.indexOf(mPlayer);
        L.d("index=" + index);
        if(index>=0) {
            players.set(index, mPlayer);
            SavePlayer();
            notifyAllPlayersOnChangeListener();
        }
    }
    public void remove_AllPlayers(Player mPlayer) {
        players.remove(mPlayer);
        SavePlayer();
        notifyAllPlayersOnChangeListener();
    }
    public void setAllPlayersOnChangeListener(onPlayerChangeListener mListener) {
        mAllPlayerListener.add(mListener);
    }
    public void removeAllPlayersOnChangeListener(onPlayerChangeListener mListener) {
        mAllPlayerListener.remove(mListener);
    }
    private void notifyAllPlayersOnChangeListener() {
        for(onPlayerChangeListener mListener : mAllPlayerListener) {
            mListener.onChange(players);
        }
    }


    public ArrayList<Player> getOrderPlayers() {
        ArrayList<Player> orderPlayers = new ArrayList<Player>();
        for(Player mPlayer : players) {
            if(mPlayer.isPresent()) {
                orderPlayers.add(mPlayer);
            }
        }
        orderPlayers = sortByOrder(orderPlayers);
        return orderPlayers;
    }
    public void setOrderPlayers(ArrayList<Player> orderPlayers) {
        for(Player mPlayer : orderPlayers) {
            players.set(players.indexOf(mPlayer), mPlayer);
        }
        SavePlayer();
        notifyOrderPlayersOnChangeListener();
    }
    public void setOrderPlayersOnChangeListener(onPlayerChangeListener mListener) {
        mOrderPlayerListener.add(mListener);
    }
    public void removeOrderPlayersOnChangeListener(onPlayerChangeListener mListener) {
        mOrderPlayerListener.remove(mListener);
    }
    private void notifyOrderPlayersOnChangeListener() {
        ArrayList<Player> newOrderPlayers = getOrderPlayers();
        for(onPlayerChangeListener mListener : mOrderPlayerListener) {
            mListener.onChange(newOrderPlayers);
        }
    }


    private ArrayList<Player> sortByNumber(ArrayList<Player> sortPlayer) {
        //依 number 排序
        Collections.sort(sortPlayer, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                try {
                    return Integer.parseInt(o1.number) - Integer.parseInt(o2.number);
                } catch (Exception e) {

                }
                return o1.number.compareTo(o2.number);
            }
        });
        return sortPlayer;
    }

    private ArrayList<Player> sortByOrder(ArrayList<Player> sortPlayer) {
        //依 number 排序
        Collections.sort(sortPlayer, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return o1.order_id - o2.order_id;
            }
        });
        return sortPlayer;
    }


    public interface onPlayerChangeListener {
        void onChange(ArrayList<Player> players);
    }
}
