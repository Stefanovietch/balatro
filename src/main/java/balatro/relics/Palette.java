package balatro.relics;

import balatro.character.baseDeck;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static balatro.balatroMod.makeID;

public class Palette extends BaseRelic{
    private static final String NAME = "Palette"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public Palette() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public void onEquip() {
        AbstractDungeon.player.masterHandSize++;
    }

    public void onUnequip() {
        AbstractDungeon.player.masterHandSize--;
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
