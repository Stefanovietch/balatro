package balatro.patches;

import balatro.cards.BaseCard;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

import static balatro.util.GeneralUtils.hasRelic;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.aiRng;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.getColorlessCardFromPool;
import static com.megacrit.cardcrawl.helpers.RelicLibrary.getRelic;

public class AddPerishableToRewardsPatches {
    @SpirePatch2(clz = AbstractDungeon.class, method = "getRewardCards")
    public static class RewardCardHook {
        @SpirePostfixPatch
        public static ArrayList<AbstractCard> setPerishable(ArrayList<AbstractCard> __result) {
            for (AbstractCard card : __result) {
                if (card instanceof BaseCard && card.cost != -2 && aiRng.randomBoolean(0.2F)) {
                    ((BaseCard) card).setPerishable();
                }
            }
            return __result;
        }
    }
}
