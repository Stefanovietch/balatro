package balatro.relics;

import balatro.character.baseDeck;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static balatro.balatroMod.makeID;

public class OmenGlobe extends BaseRelic{
    private static final String NAME = "OmenGlobe"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public OmenGlobe() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onUsePotion() {
        super.onUsePotion();
        if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new MakeTempCardInHandAction(AbstractDungeon.returnTrulyRandomColorlessCardInCombat()));
        }
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
