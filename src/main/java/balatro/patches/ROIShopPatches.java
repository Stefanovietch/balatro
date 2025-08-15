package balatro.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StorePotion;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

import static balatro.util.GeneralUtils.hasRelic;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;
import static com.megacrit.cardcrawl.helpers.RelicLibrary.getRelic;

public class ROIShopPatches {
    @SpirePatch2(clz = ShopScreen.class, method = "initCards")
    public static class cardHook {
        @SpireInsertPatch(
                rloc = 0,
                localvars={"coloredCards","colorlessCards"}
        )
        public static void upgradeCards(@ByRef ArrayList<AbstractCard>[] coloredCards, @ByRef ArrayList<AbstractCard>[] colorlessCards) {
            boolean hasRare = false;
            if (hasRelic("Balatro:ROI")) {
                for (AbstractCard card : coloredCards[0]) {
                    if(card.rarity == AbstractCard.CardRarity.RARE) {hasRare = true;}
                    card.upgrade();
                }
                if (!hasRare) {
                    int cardToChange = aiRng.random(4);
                    AbstractCard.CardType cardType = coloredCards[0].get(cardToChange).type;
                    AbstractCard c = AbstractDungeon.getCardFromPool(AbstractCard.CardRarity.RARE, cardType, true).makeCopy();
                    c.upgrade();
                    c.price = (int) (AbstractCard.getPrice(c.rarity) * AbstractDungeon.merchantRng.random(0.9F, 1.1F));
                    coloredCards[0].set(cardToChange, c);
                }
                for (AbstractCard card : colorlessCards[0]) {
                    card.upgrade();
                }
            }
        }
    }

    @SpirePatch2(clz = ShopScreen.class, method = "init")
    public static class potionHook {
        @SpireInsertPatch(
                rloc = 9,
                localvars={"potions"}
        )
        public static void atleastUncommonPotion(@ByRef ArrayList<StorePotion>[] potions) {
            if (hasRelic("Balatro:ROI")) {
                for (int i = 0; i < potions[0].size(); i++) {
                    StorePotion potion = potions[0].get(i);
                    if (potion.potion.rarity == AbstractPotion.PotionRarity.COMMON) {
                        StorePotion p = new StorePotion(AbstractDungeon.returnRandomPotion(AbstractPotion.PotionRarity.UNCOMMON,false), i, shopScreen);
                        potions[0].set(i,p);
                    }
                }
            }
        }
    }
}
