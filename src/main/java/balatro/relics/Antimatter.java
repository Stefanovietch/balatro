package balatro.relics;

import balatro.character.baseDeck;

import static balatro.balatroMod.makeID;

public class Antimatter extends BaseRelic{
    private static final String NAME = "Antimatter"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public Antimatter() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public int changeNumberOfCardsInReward(int numberOfCards) {
        return numberOfCards + 1;
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
