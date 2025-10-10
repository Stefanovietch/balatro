package balatro.patches;

import balatro.balatroMod;
import balatro.cards.BaseCard;
import balatro.character.baseDeck;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.aiRng;

public class AddPerishableToRewardsPatches {
    @SpirePatch2(clz = AbstractDungeon.class, method = "getRewardCards")
    public static class RewardCardHook {
        @SpirePostfixPatch
        public static ArrayList<AbstractCard> setPerishable(ArrayList<AbstractCard> __result) {
            if(balatroMod.selectedStakeIndex >= 6 && AbstractDungeon.player instanceof baseDeck) {
                for (AbstractCard card : __result) {
                    if (card instanceof BaseCard && card.cost != -2 && aiRng.randomBoolean(0.2F)) {
                        ((BaseCard) card).setPerishable();
                    }
                }
            }
            return __result;
        }
    }
}
