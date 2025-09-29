package balatro.relics;

import balatro.character.baseDeck;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static balatro.balatroMod.makeID;

public class Clairvoyance extends BaseRelic{
    private static final String NAME = "Clairvoyance"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public Clairvoyance() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onEquip() {
        super.onEquip();
        AbstractDungeon.cardRewardScreen.open(
                getColorlessRewardCards(), null,
                (CardCrawlGame.languagePack.getUIString("CardRewardScreen")).TEXT[1]);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public ArrayList<AbstractCard> getColorlessRewardCards() {
        ArrayList<AbstractCard> retVal = new ArrayList<>();

        AbstractCard card1 = AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.RARE).makeCopy();
        retVal.add(card1);

        AbstractCard card2 = AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.RARE).makeCopy();
        while (card1 == card2) {
            card2 = AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.RARE).makeCopy();
        }
        retVal.add(card2);

        AbstractCard card3 = AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.RARE).makeCopy();
        while (card1 == card3 || card2 == card3) {
            card3 = AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.RARE).makeCopy();
        }
        retVal.add(card3);

        return retVal;
    }
}
