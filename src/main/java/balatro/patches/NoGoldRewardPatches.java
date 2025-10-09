package balatro.patches;

import balatro.balatroMod;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

import static balatro.util.GeneralUtils.hasRelic;
import static com.megacrit.cardcrawl.helpers.RelicLibrary.getRelic;

public class NoGoldRewardPatches {
    @SpirePatch2(clz = AbstractRoom.class, method = "addGoldToRewards")
    public static class GetGoldHook {
        @SpireInsertPatch(
                rloc=0,
                localvars={"gold"}
        )
        public static void removeGold(@ByRef(type="int") int[] gold) {
            AbstractRoom curRoom = AbstractDungeon.getCurrRoom();
            if (balatroMod.selectedDeckIndex >= 1) {
                if (curRoom instanceof MonsterRoom && !(curRoom instanceof MonsterRoomElite || curRoom instanceof MonsterRoomBoss)) {
                    gold[0] = gold[0]/2;
                }
            }
            if (hasRelic("Balatro:YouGetWhatYouGet")) {
                getRelic("Balatro:YouGetWhatYouGet").flash();
                gold[0] = 0;
            }
        }
    }
}
