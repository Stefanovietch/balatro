package balatro.powers;

import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static balatro.balatroMod.makeID;

public class VerdantLeafPower extends BlindPower{
    public static final String POWER_ID = makeID("VerdantLeaf");

    public VerdantLeafPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.VERDANT_LEAF);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        addToBot(new ExhaustAction(1,false,false,false));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
