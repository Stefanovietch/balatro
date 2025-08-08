package balatro.relics;

import balatro.character.baseDeck;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static balatro.balatroMod.makeID;

public class Petroglyph extends BaseRelic{
    private static final String NAME = "Petroglyph"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    private final float MODIFIER_AMT = 0.9F;

    public Petroglyph() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public void atBattleStart() {
        if ((AbstractDungeon.getCurrRoom()).eliteTrigger) {
            flash();
            for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                m.maxHealth = (int) (m.maxHealth * MODIFIER_AMT);
                m.healthBarUpdatedEvent();
            }
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
