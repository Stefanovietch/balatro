package balatro.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class LegendaryPatch {
    @SpireEnum
    public static AbstractCard.CardRarity LEGENDARY;

    @SpirePatch2(clz = AbstractCard.class, method = "getPrice")
    public static class priceHook {
        @SpirePostfixPatch
        public static int changePrice(int __result, AbstractCard.CardRarity rarity) {
            if (rarity == LEGENDARY) {
                return 250;
            }
            return __result;
        }
    }
}




