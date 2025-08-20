package balatro.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static balatro.balatroMod.makeID;

public class TheWaterPower extends BlindPower{
    public static final String POWER_ID = makeID("TheWater");

    public TheWaterPower(AbstractCreature owner) {
        super(POWER_ID, owner, BlindType.THE_WATER);
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        super.atEndOfTurnPreEndTurnCards(isPlayer);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
