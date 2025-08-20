package balatro.relics;

import balatro.character.baseDeck;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.ShopRoom;

import static balatro.balatroMod.makeID;

public class OverstockPlus extends BaseRelic{
    private static final String NAME = "OverstockPlus"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public OverstockPlus() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onEquip() {
        super.onEquip();
        if (AbstractDungeon.getCurrRoom() instanceof ShopRoom) {
            AbstractDungeon.getCurrRoom().update();
        }
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
