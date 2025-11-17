package balatro.relics;

import balatro.character.baseDeck;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static balatro.balatroMod.makeID;

public class Flushed extends BaseRelic{
    private static final String NAME = "Flushed"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private boolean gainEnergyNext = false, firstTurn = false;

    public Flushed() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void atPreBattle() {
        flash();
        this.firstTurn = true;
        this.gainEnergyNext = true;
        if (!this.pulse) {
            beginPulse();
            this.pulse = true;
        }
    }

    public void atTurnStart() {
        beginPulse();
        this.pulse = true;
        if (this.gainEnergyNext && !this.firstTurn) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainEnergyAction(2));
        }
        this.firstTurn = false;
        this.gainEnergyNext = true;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER) {
            this.gainEnergyNext = false;
            this.pulse = false;
        }
    }

    public void onVictory() {
        this.pulse = false;
    }

}
