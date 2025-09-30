package balatro.powers;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static balatro.balatroMod.makeID;

public class TheNeedlePower extends BlindPower{
    public static final String POWER_ID = makeID("TheNeedle");

    public TheNeedlePower(AbstractCreature owner) {
        super(POWER_ID, owner, BlindType.THE_NEEDLE);
        amount = GameActionManager.turn;
    }

    public void onRemove() {
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        amount = GameActionManager.turn;
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        if (amount >= 10) {
            addToBot(new LoseHPAction(this.owner, this.owner, 99999));
        }
    }

    public void updateDescription() {
        int turnsLeft = 11 - GameActionManager.turn;
        if (turnsLeft > 1) {
            this.description = DESCRIPTIONS[0] + turnsLeft + DESCRIPTIONS[1];
        } else if (turnsLeft == 1) {
            this.description = DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[3];
        }
    }


}
