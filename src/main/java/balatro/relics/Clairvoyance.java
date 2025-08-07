package balatro.relics;

import balatro.character.baseDeck;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static balatro.balatroMod.makeID;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.getColorlessRewardCards;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.rollRarity;

public class Clairvoyance extends BaseRelic{
    private static final String NAME = makeID(Clairvoyance.class.getSimpleName()); //The name will be used for determining the image file as well as the ID.
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
        for (int numCards = 3, i = 0; i < numCards; i++) {
            AbstractCard card = AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.RARE).makeCopy();
            retVal.add(card);
        }
        return retVal;
    }
}
