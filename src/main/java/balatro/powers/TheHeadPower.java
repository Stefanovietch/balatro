package balatro.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.SlowPower;

import static balatro.balatroMod.makeID;

public class TheHeadPower extends BlindPower{
    public static final String POWER_ID = makeID("TheHead");

    public TheHeadPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.THE_HEAD);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, owner,new SlowPower(AbstractDungeon.player,0)));
    }

    public void onRemove() {
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,owner,"Slow"));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
