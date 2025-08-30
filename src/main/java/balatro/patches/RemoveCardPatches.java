package balatro.patches;

import balatro.balatroMod;
import balatro.util.Data;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class RemoveCardPatches {
    @SpirePatch2(clz = AbstractCard.class,
            method = "onRemoveFromMasterDeck"
    )
    public static class getOnRemoveFromMasterDeck {
        @SpirePostfixPatch
        public static void addToCardsRemoved() {
            Data.changeRemovedCards(1);
        }
    }
}
