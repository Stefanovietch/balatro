package balatro.relics;

import balatro.character.baseDeck;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static balatro.balatroMod.makeID;

public class Liquidation extends BaseRelic{
    private static final String NAME = "Liquidation"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SHOP; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public Liquidation() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onEquip() {
        super.onEquip();
        if (AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.ShopRoom) {
            flash();
            this.pulse = true;
            AbstractDungeon.shopScreen.applyDiscount(0.5F,true);
        }
    }

    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof com.megacrit.cardcrawl.rooms.ShopRoom) {
            flash();
            this.pulse = true;
        } else {
            this.pulse = false;
        }
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
