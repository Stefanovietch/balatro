package balatro.patches;

import balatro.cards.Castle;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.helpers.RelicLibrary.getRelic;

public class CastleDiscardPatches {
    @SpirePatch2(clz = CardGroup.class, method = "moveToDiscardPile")
    public static class TriggerDiscardHook {
        @SpireInsertPatch(
                rloc=0,
                localvars={"c"}
        )
        public static void triggerDiscard(@ByRef AbstractCard[] c) {
            ArrayList<AbstractCard> limbo = AbstractDungeon.player.limbo.group;
            for (AbstractCard card : limbo) {
                if (card instanceof Castle) {
                    ((Castle) card).triggerOnOtherCardDiscarded(c[0]);
                }
            }
            ArrayList<AbstractCard> hand = AbstractDungeon.player.hand.group;
            for (AbstractCard card : hand) {
                if (card instanceof Castle) {
                    ((Castle) card).triggerOnOtherCardDiscarded(c[0]);
                }
            }
        }
    }
}
