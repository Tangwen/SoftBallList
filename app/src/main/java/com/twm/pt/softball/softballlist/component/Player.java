package com.twm.pt.softball.softballlist.component;

/**
 * Created by TangWen on 2015/4/22.
 */
public class Player {
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


    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    public enum Fielder {
        allfielder("內外野手"),
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
