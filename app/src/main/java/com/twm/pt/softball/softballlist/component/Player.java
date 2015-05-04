package com.twm.pt.softball.softballlist.component;

import java.io.Serializable;

/**
 * Created by TangWen on 2015/4/22.
 */
public class Player implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;
    public static final String BundleKey = "Player";
    public static final String BundleKey_PositionCount = "PositionCount";

    /**人名*/
    public String Name;
    /**暱稱*/
    public String nickName;
    /**照片*/
    public String picture;
    /**背號*/
    public String number;
    /**投打*/
    public String habits;
    /**守備習慣*/
    public Fielder fielder;
    /**出席*/
    public boolean present = true;
    /**守備位置*/
    public Position position;
    /**打擊順序*/
    public int order_id = 99;


    public Player(String name, String nickName, String picture, String number, String habits, Fielder fielder) {
        Name = name;
        this.nickName = nickName;
        this.picture = picture;
        this.number = number;
        this.habits = habits;
        this.fielder = fielder;
    }

    public Player(String name, String nickName, String picture, String number, String habits, Fielder fielder, Position position) {
        Name = name;
        this.nickName = nickName;
        this.picture = picture;
        this.number = number;
        this.habits = habits;
        this.fielder = fielder;
        this.position = position;
    }

    @Override
    public String toString() {
        return "Player{" +
                "Name='" + Name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", picture='" + picture + '\'' +
                ", number='" + number + '\'' +
                ", habits='" + habits + '\'' +
                ", fielder=" + fielder +
                ", present=" + present +
                ", position=" + position +
                ", order_id=" + order_id +
                '}';
    }

    public boolean isPresent() {
        return present;
    }
    public void setPresent(boolean present) {
        this.present = present;
    }
    public int getOrder_id() {
        return order_id;
    }
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    @Override
    public boolean equals(Object obj) {
//        return super.equals(o);
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Player other = (Player) obj;
        if (number != other.number) return false;
        return true;
    }

    public enum Fielder {
        all_fielder("內外野手"),
        infielder("內野手"),
        outfielder("外野手"),
        pitcher("投手");

        String fielderName;
        Fielder(String fielderName) {
            this.fielderName = fielderName;
        }

        public String getFielderName() {
            return fielderName;
        }
    }
}
