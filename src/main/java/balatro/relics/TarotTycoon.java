package balatro.relics;

import balatro.character.baseDeck;

import static balatro.balatroMod.makeID;

public class TarotTycoon extends BaseRelic{
    private static final String NAME = "TarotTycoon"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.SOLID; //The sound played when the relic is clicked.

    public TarotTycoon() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
