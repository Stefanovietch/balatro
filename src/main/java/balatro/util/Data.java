package balatro.util;

import java.util.HashMap;
import java.util.Map;

public class Data {
    public static Map<String, Integer> savedData = new HashMap<String,Integer>();
    public static Map<String, Integer> savedDataInBattle = new HashMap<String,Integer>();

    static {
        savedData.put("PotionsUsed", 0);
        savedData.put("EventRooms", 0);
        savedData.put("RestSites", 0);
        savedData.put("MaxGoldCombat", 200);
        savedDataInBattle.put("PotionsUsed", 0);
        savedDataInBattle.put("CardsDiscarded", 0);
        savedDataInBattle.put("GoldCombat", 0);
    }

    public static void saveBattleData() {
        savedData.put("PotionsUsed", savedData.get("PotionsUsed") + savedDataInBattle.get("PotionsUsed"));
    }

    public static void resetBattleData() {
        savedDataInBattle.put("PotionsUsed", 0);
        savedDataInBattle.put("CardsDiscarded", 0);
        savedDataInBattle.put("GoldCombat", 0);
    }

    public static void resetData() {
        savedData.put("PotionsUsed", 0);
        savedData.put("EventRooms", 0);
        savedData.put("RestSites", 0);
        savedData.put("MaxGoldCombat", 200);
    }
    public static void setData(Map<String, Integer> map) {
        savedData.put("PotionsUsed", map.get("PotionsUsed"));
        savedData.put("EventRooms", map.get("EventRooms"));
        savedData.put("RestSites", map.get("RestSites"));
        savedData.put("MaxGoldCombat", map.get("MaxGoldCombat"));
    }

    public static Map<String, Integer> getData() {
        return savedData;
    }

    public static int getPotionsUsed() {
        return savedData.get("PotionsUsed") + savedDataInBattle.get("PotionsUsed");
    }

    public static int getEventRooms() {
        return savedData.get("EventRooms");
    }

    public static int getRestSites() {
        return savedData.get("RestSites");
    }

    public static int getCardsDiscarded() {
        return savedDataInBattle.get("CardsDiscarded");
    }

    public static int getGoldCombat() {
        return savedDataInBattle.get("GoldCombat");
    }

    public static int getMaxGoldCombat() {
        return savedData.get("MaxGoldCombat");
    }

    public static void changePotionsUsed(int amount) {
        savedDataInBattle.put("PotionsUsed", savedDataInBattle.get("PotionsUsed") + amount);
    }

    public static void changeEventRooms(int amount) {
        savedData.put("EventRooms", savedData.get("EventRooms") + amount);
    }

    public static void changeRestSites(int amount) {
        savedData.put("RestSites", savedData.get("RestSites") + amount);
    }

    public static void changeCardsDiscarded(int amount) {
        savedDataInBattle.put("CardsDiscarded", savedDataInBattle.get("CardsDiscarded") + amount);
    }

    public static void changeGoldCombat(int amount) {
        savedDataInBattle.put("GoldCombat", savedDataInBattle.get("GoldCombat") + amount);
    }


    public static void setMaxGoldCombat(int amount) {
        savedData.put("MaxGoldCombat", amount);
    }


    public static void resetRestSites() {
        savedData.put("RestSites", 0);
    }

}
