package balatro.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StoreRelic;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

import static balatro.util.GeneralUtils.hasRelic;
import static com.megacrit.cardcrawl.shop.ShopScreen.rollRelicTier;

public class OverstockPlusPatches {
    @SpirePatch2(clz = ShopScreen.class, method = "initRelics")
    public static class shopHook {
        @SpireInsertPatch(
                rloc = 2,
                localvars={"relics"}
        )
        public static void addRelics(@ByRef ArrayList<StoreRelic>[] relics) {
            if (hasRelic("Balatro:OverstockPlus")) {
                for(int i = 3; i < 6; ++i) {
                    AbstractRelic tempRelic = null;
                    if (i != 5) {
                        tempRelic = AbstractDungeon.returnRandomRelicEnd(rollRelicTier());
                    } else {
                        tempRelic = AbstractDungeon.returnRandomRelicEnd(AbstractRelic.RelicTier.SHOP);
                    }

                    StoreRelic relic = new StoreRelic(tempRelic, i, AbstractDungeon.shopScreen);
                    if (!Settings.isDailyRun) {
                        relic.price = MathUtils.round((float)relic.price * AbstractDungeon.merchantRng.random(0.95F, 1.05F));
                    }

                    relics[0].add(relic);
                }
            }
        }
    }

    @SpirePatch2(clz = StoreRelic.class, method = "update")
    public static class storeRelicHook {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars={"relic","slot"}
        )
        public static void moveRelic(@ByRef AbstractRelic[] relic, int slot) {
            if (hasRelic("Balatro:OverstockPlus")) {
                if (slot >= 3) {
                    relic[0].currentX -= 150.0F * 3 * Settings.xScale;
                    relic[0].currentY += 100.0F * Settings.yScale;
                } else {
                    relic[0].currentY -= 50.0F * Settings.yScale;
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
                Matcher finalMatcher = new Matcher.MethodCallMatcher(Hitbox.class, "move");
                // the `new ArrayList<Matcher>()` specifies the prerequisites before the line can be matched -
                // the LineFinder will search for all the prerequisites in order before it will match the finalMatcher -
                // since we didn't specify any prerequisites here, the LineFinder will simply find the first expression
                // that matches the finalMatcher.
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            }
        }
    }
}
