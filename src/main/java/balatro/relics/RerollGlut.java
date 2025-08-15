package balatro.relics;

import balatro.character.baseDeck;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.shop.Merchant;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.shop.StoreRelic;

import java.util.ArrayList;
import java.util.Objects;

import static balatro.balatroMod.makeID;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.shopScreen;

public class RerollGlut extends BaseRelic implements ClickableRelic {
    private static final String NAME = "RerollGlut"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SHOP; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public RerollGlut() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onRightClick() {
        if (AbstractDungeon.getCurrRoom() instanceof ShopRoom && AbstractDungeon.player.gold >= 50) {

            ArrayList<AbstractCard> newColorCards = new ArrayList<>();
            AbstractCard c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.ATTACK, true).makeCopy();
            while (c.color == AbstractCard.CardColor.COLORLESS)
                c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.ATTACK, true).makeCopy();
            newColorCards.add(c);
            c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.ATTACK, true).makeCopy();
            while (Objects.equals(c.cardID, (newColorCards.get(newColorCards.size() - 1)).cardID) || c.color == AbstractCard.CardColor.COLORLESS)
                c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.ATTACK, true).makeCopy();
            newColorCards.add(c);
            c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.SKILL, true).makeCopy();
            while (c.color == AbstractCard.CardColor.COLORLESS)
                c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.SKILL, true).makeCopy();
            newColorCards.add(c);
            c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.SKILL, true).makeCopy();
            while (Objects.equals(c.cardID, (newColorCards.get(newColorCards.size() - 1)).cardID) || c.color == AbstractCard.CardColor.COLORLESS)
                c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.SKILL, true).makeCopy();
            newColorCards.add(c);
            c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.POWER, true).makeCopy();
            while (c.color == AbstractCard.CardColor.COLORLESS)
                c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), AbstractCard.CardType.POWER, true).makeCopy();
            newColorCards.add(c);

            ArrayList<AbstractCard> newColorlessCards = new ArrayList<>();
            newColorlessCards.add(AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.UNCOMMON).makeCopy());
            newColorlessCards.add(AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.RARE).makeCopy());

            ReflectionHacks.setPrivate(shopScreen, ShopScreen.class, "coloredCards", newColorCards);
            ReflectionHacks.setPrivate(shopScreen, ShopScreen.class, "colorlessCards", newColorlessCards);

            ReflectionHacks.privateMethod(ShopScreen.class,"initCards").invoke(shopScreen);

            AbstractDungeon.player.loseGold(50);
        }
    }
}
