package balatro.patches;

import balatro.balatroMod;
import balatro.util.Data;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class GoldPerCombatPatches {
    @SpirePatch2(clz = AbstractPlayer.class, method = "gainGold")
    public static class GetGoldHook {
        @SpireInsertPatch(
                rloc=0,
                localvars={"amount"}
        )
        public static void checkGold(@ByRef(type="int") int[] amount) {
            if (AbstractDungeon.floorNum > 1) {
                if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && balatroMod.combatGoldLimit) {
                    if (Data.getGoldCombat() < Data.getMaxGoldCombat()) {
                        if (Data.getGoldCombat() + amount[0] <= Data.getMaxGoldCombat()) {
                            Data.changeGoldCombat(amount[0]);
                        } else {
                            amount[0] = Data.getMaxGoldCombat() - Data.getGoldCombat();
                            Data.changeGoldCombat(amount[0]);
                        }
                    } else {
                        amount[0] = 0;
                    }
                }
            }
        }
    }
}
