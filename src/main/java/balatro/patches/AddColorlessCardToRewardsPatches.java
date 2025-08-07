package balatro.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;
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
            // This is the abstract method from SpireInsertLocator that will be used to find the line
            // numbers you want this patch inserted at
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                // finalMatcher is the line that we want to insert our patch before -
                // in this example we are using a `MethodCallMatcher` which is a type
                // of matcher that matches a method call based on the type of the calling
                // object and the name of the method being called. Here you can see that
                // we're expecting the `end` method to be called on a `SpireBatch`
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");
                // the `new ArrayList<Matcher>()` specifies the prerequisites before the line can be matched -
                // the LineFinder will search for all the prerequisites in order before it will match the finalMatcher -
                // since we didn't specify any prerequisites here, the LineFinder will simply find the first expression
                // that matches the finalMatcher.
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            }
        }

        private static boolean hasRelic(String targetID) {
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r.relicId.equals(targetID))
                    return true;
            }
            return false;
        }
    }
}
