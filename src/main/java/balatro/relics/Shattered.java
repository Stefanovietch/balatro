package balatro.relics;

import balatro.character.baseDeck;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static balatro.balatroMod.makeID;

public class Shattered extends BaseRelic{
    private static final String NAME = "Shattered"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public Shattered() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public void onEquip() {
        AbstractDungeon.player.masterDeck.group.clear();
        for (int j = 0; j < 20; j++) {
            AbstractCard card = AbstractDungeon.returnTrulyRandomCard().makeCopy();
            AbstractDungeon.player.masterDeck.addToBottom(card);
        }
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
