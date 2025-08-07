package balatro.patches;

import balatro.balatroMod;
import balatro.util.Data;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.scenes.AbstractScene;

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
