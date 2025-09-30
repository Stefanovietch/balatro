package balatro.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.shop.ShopScreen;

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
}
