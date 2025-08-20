package balatro.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StoreRelic;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

import static balatro.util.GeneralUtils.hasRelic;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.shopScreen;

public class LiquidationPatches {
    @SpirePatch2(clz = ShopScreen.class, method = "init")
    public static class shopHook {
        @SpirePostfixPatch
        public static void applyDiscount() {
            if (hasRelic("Balatro:Liquidation")) {
                shopScreen.applyDiscount(0.5F,true);
            }
        }
    }

    @SpirePatch2(clz = StoreRelic.class, method = "purchaseRelic")
    public static class relicBuyHook {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void applyDiscount() {
            if (hasRelic("Balatro:Liquidation")) {
                shopScreen.applyDiscount(0.5F,true);
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
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractRelic.class, "flash");
                // the `new ArrayList<Matcher>()` specifies the prerequisites before the line can be matched -
                // the LineFinder will search for all the prerequisites in order before it will match the finalMatcher -
                // since we didn't specify any prerequisites here, the LineFinder will simply find the first expression
                // that matches the finalMatcher.
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            }
        }
    }
}
