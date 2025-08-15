package balatro.relics;

import balatro.character.baseDeck;
import balatro.util.Data;

import static balatro.balatroMod.makeID;

public class MoneyTree extends BaseRelic{
    private static final String NAME = "MoneyTree"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SHOP; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public MoneyTree() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onEquip() {
        super.onEquip();
        Data.setMaxGoldCombat(400);
    }

    @Override
    public void onUnequip() {
        super.onUnequip();
        Data.setMaxGoldCombat(200);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
