package balatro.powers;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static balatro.balatroMod.makeID;

public class CrimsonHeartPower extends BlindPower{
    public static final String POWER_ID = makeID("CrimsonHeart");

    public CrimsonHeartPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.CRIMSON_HEART);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        addToBot(new DiscardAction(AbstractDungeon.player,owner,1,true));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
