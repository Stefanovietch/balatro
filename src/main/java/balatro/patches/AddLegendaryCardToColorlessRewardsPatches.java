package balatro.patches;

import balatro.cards.*;
import balatro.character.baseDeck;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.aiRng;

public class AddLegendaryCardToColorlessRewardsPatches {
    @SpirePatch2(clz = AbstractDungeon.class, method = "getColorlessCardFromPool")
    public static class ColorlessHook {
        @SpirePostfixPatch
        public static AbstractCard replaceRareWithLegendary(AbstractCard __result) {
            if (aiRng.randomBoolean(0.05F) && __result.rarity == AbstractCard.CardRarity.RARE && AbstractDungeon.player instanceof baseDeck) {
                CardGroup legendaryCardPool = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                legendaryCardPool.addToRandomSpot(new Canio());
                legendaryCardPool.addToRandomSpot(new Triboulet());
                legendaryCardPool.addToRandomSpot(new Yorick());
                legendaryCardPool.addToRandomSpot(new Chicot());
                legendaryCardPool.addToRandomSpot(new Perkeo());
                return legendaryCardPool.getRandomCard(true).makeCopy();
            }
            return __result;
        }
    }
}
