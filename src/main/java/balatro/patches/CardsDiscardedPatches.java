package balatro.patches;

import balatro.util.Data;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.GameActionManager;

public class CardsDiscardedPatches {
    @SpirePatch2(
            clz = GameActionManager.class,
            method = "incrementDiscard")
    public static class DiscardHook {
        @SpireInsertPatch(
                rloc=0,
                localvars={"endOfTurn"}
        )
        public static void cardDiscarded(@ByRef boolean[] endOfTurn) {
            if (!endOfTurn[0]) {
                Data.changeCardsDiscarded(1);
            }
        }
    }
}
