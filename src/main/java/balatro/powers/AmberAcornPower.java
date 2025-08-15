package balatro.powers;

import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static balatro.balatroMod.makeID;

public class AmberAcornPower extends BlindPower{
    public static final String POWER_ID = makeID("AmberAcorn");

    public AmberAcornPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.AMBER_ACORN);
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        if (!AbstractDungeon.player.discardPile.isEmpty()) {
            addToBot(new EmptyDeckShuffleAction());
            addToBot(new ShuffleAction(AbstractDungeon.player.drawPile, false));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
