package balatro.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

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
            if (hasRelic("Balatro:YouGetWhatYouGet")) {
                getRelic("Balatro:YouGetWhatYouGet").flash();
                gold[0] = 0;
            }
        }
    }
}
