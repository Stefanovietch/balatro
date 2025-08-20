package balatro.relics;

import balatro.character.baseDeck;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.DuplicationPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.vfx.ObtainPotionEffect;

import static balatro.balatroMod.makeID;

public class CrystalBall extends BaseRelic{
    private static final String NAME = "CrystalBall"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public CrystalBall() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onEquip() {
        AbstractDungeon.player.potionSlots += 1;
        AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));
        AbstractDungeon.effectsQueue.add(new ObtainPotionEffect(new DuplicationPotion()));
        AbstractDungeon.effectsQueue.add(new ObtainPotionEffect(new DuplicationPotion()));
    }

}
