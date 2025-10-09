package balatro.patches;

import balatro.balatroMod;
import balatro.cards.CleverJoker;
import balatro.cards.JollyJoker;
import balatro.cards.MadJoker;
import balatro.cards.SlyJoker;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public class RemoveBasicCardPurgePatches {
    @SpirePatch2(clz = CardGroup.class,
            method = "getPurgeableCards"
    )
    public static class GetPurgeCards {
        @SpirePostfixPatch
        public static CardGroup removeBasicCards(CardGroup __result) {
            if (balatroMod.selectedStakeIndex >= 3) {
                __result.group.removeIf(card -> card instanceof SlyJoker || card instanceof JollyJoker || card instanceof MadJoker || card instanceof CleverJoker);
            }
            return __result;
        }
    }
}
