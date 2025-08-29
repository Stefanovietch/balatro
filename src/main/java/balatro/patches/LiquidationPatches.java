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
}
