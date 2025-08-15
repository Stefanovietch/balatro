package balatro.relics;

import balatro.character.baseDeck;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static balatro.balatroMod.makeID;

public class Recyclomancy extends BaseRelic{
    private static final String NAME = "Recyclomancy"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public Recyclomancy() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
        this.counter = 0;
    }

    @Override
    public void onManualDiscard() {
        super.onManualDiscard();
        this.counter++;
        if (this.counter == 7) {
            this.counter = 0;
            flash();
            this.pulse = false;
            AbstractPlayer player = AbstractDungeon.player;
            //addToBot(new RelicAboveCreatureAction(player, this));
            addToBot(new ApplyPowerAction(player, player, new StrengthPower(player, 1),1));
        } else if (this.counter == 6) {
            beginPulse();
            this.pulse = true;
        }
    }

    public void atBattleStart() {
        if (this.counter == 6) {
            beginPulse();
            this.pulse = true;
        }
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
