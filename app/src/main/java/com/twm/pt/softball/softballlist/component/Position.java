package com.twm.pt.softball.softballlist.component;

/**
 * Created by TangWen on 2015/4/23.
 */
public enum Position {

    Pitcher("1", "投手", "Pitcher", "P"),
    Catcher("2", "捕手", "Catcher", "C"),
    FirstBaseMan("3", "一壘手", "First-Base Man", "1B"),
    SecondBaseMan("4", "二壘手", "Second-Base Man","2B"),
    ThirdBaseMan("5", "三壘手", "Third-Base Man","3B"),
    ShortShop("6", "游擊手", "Short Shop","SS"),
    LeftFielder("7", "左外野", "Left-Fielder","LF"),
    CenterFielder("8", "中外野", "Center-Fielder","CF"),
    RightFielder("9", "右外野", "Right-Fielder","RF"),
    ShortFielder("10", "自由手", "Short Fielder","SF"),
    ExtraPlayer("11", "增額球員", "Extra Player","EP"),
    BenchPlayer("0", "候補球員", "Bench Player","BP")
    ;

    String NO;
    String cName;
    String enName;
    String shortName;

    Position(String NO, String cName, String enName, String shortName) {
        this.NO = NO;
        this.cName = cName;
        this.enName = enName;
        this.shortName = shortName;
    }

    public static Position lookup(final String valueString) {
        Position obj = null;
        if (valueString != null) {
            for (Position obj1 : Position.values()) {
                if (obj.getNO().equalsIgnoreCase(valueString)) {
                    obj = obj1;
                    break;
                } else if (obj.getCName().equalsIgnoreCase(valueString)) {
                    obj = obj1;
                    break;
                } else if (obj.getEnName().equalsIgnoreCase(valueString)) {
                    obj = obj1;
                    break;
                } else if (obj.getShortName().equalsIgnoreCase(valueString)) {
                    obj = obj1;
                    break;
                }
            }
        }
        return obj;
    }


    public String getNO() {
        return NO;
    }
    public String getCName() {
        return cName;
    }
    public String getEnName() {
        return enName;
    }
    public String getShortName() {
        return shortName;
    }


}
