package balatro.patches;

import balatro.balatroMod;
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

public class AddColorlessCardToRewardsPatches {
    @SpirePatch2(clz = AbstractDungeon.class, method = "getRewardCards")
    public static class UpdateHook {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars={"card","rarity"}
        )
        public static void addColorlessReward(@ByRef AbstractCard[] card, AbstractCard.CardRarity rarity) {
            if (hasRelic("Balatro:Clairvoyance")) {
                getRelic("Balatro:Clairvoyance").flash();
                if (rarity != AbstractCard.CardRarity.COMMON && aiRng.randomBoolean()) {
                    AbstractCard colorlessRewardCard = getColorlessCardFromPool(rarity);
                    card[0] = colorlessRewardCard;
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            }
        }
    }
}
