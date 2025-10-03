package balatro.util;

import balatro.balatroMod;
import balatro.powers.BlindPower;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
    public static Map<String, Integer> savedData = new HashMap<String,Integer>();
    public static Map<String, Integer> savedDataInBattle = new HashMap<String,Integer>();
    public static List<BlindPower.BlindType> bossBlinds = new ArrayList<BlindPower.BlindType>();

    static {
        savedData.put("PotionsUsed", 0);
        savedData.put("EventRooms", 0);
        savedData.put("RestSites", 0);
        savedData.put("MaxGoldCombat", 200);
        savedData.put("RemovedCards", 0);
        savedDataInBattle.put("PotionsUsed", 0);
        savedDataInBattle.put("CardsDiscarded", 0);
        savedDataInBattle.put("GoldCombat", 0);
        savedDataInBattle.put("RemovedCards", 0);
    }

    public static void saveBattleData() {
        savedData.put("PotionsUsed", savedData.get("PotionsUsed") + savedDataInBattle.get("PotionsUsed"));
        savedData.put("RemovedCards", savedData.get("RemovedCards") + savedDataInBattle.get("RemovedCards"));
        balatroMod.logger.info("save normal: {}, battle: {}",savedData.get("RemovedCards"),savedDataInBattle.get("RemovedCards"));

    }

    public static void resetBattleData() {
        savedDataInBattle.put("PotionsUsed", 0);
        savedDataInBattle.put("CardsDiscarded", 0);
        savedDataInBattle.put("GoldCombat", 0);
        savedDataInBattle.put("RemovedCards", 0);

    }

    public static void resetData() {
        savedData.put("PotionsUsed", 0);
        savedData.put("EventRooms", 0);
        savedData.put("RestSites", 0);
        savedData.put("MaxGoldCombat", 200);
        savedData.put("RemovedCards", 0);
    }
    public static void setData(Map<String, Integer> map) {
        savedData.put("PotionsUsed", map.get("PotionsUsed"));
        savedData.put("EventRooms", map.get("EventRooms"));
        savedData.put("RestSites", map.get("RestSites"));
        savedData.put("MaxGoldCombat", map.get("MaxGoldCombat"));
        savedData.put("RemovedCards", map.get("RemovedCards"));
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

    public static int getRemovedCards() {
        balatroMod.logger.info("normal: {}, battle: {}",savedData.get("RemovedCards"),savedDataInBattle.get("RemovedCards"));
        return savedData.get("RemovedCards") + savedDataInBattle.get("RemovedCards");
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

    public static void changeRemovedCards(int amount) {
        savedDataInBattle.put("RemovedCards", savedDataInBattle.get("RemovedCards") + amount);
        balatroMod.logger.info("new battle: {}",savedDataInBattle.get("RemovedCards"));

    }

    public static List<BlindPower.BlindType> getBossBlinds() {
        return bossBlinds;
    }
    public static void setBossBlinds(List<BlindPower.BlindType> bb) {
        bossBlinds = bb;
    }
    public static void useBossBlind(BlindPower.BlindType blind) {
        bossBlinds.add(blind);
    }
    public static void resetBossBlind() {
        bossBlinds.clear();
    }



    public static void setMaxGoldCombat(int amount) {
        savedData.put("MaxGoldCombat", amount);
    }

    public static void resetRestSites() {
        savedData.put("RestSites", 0);
    }

}
