package balatro.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.ThornsPower;

import static balatro.balatroMod.makeID;

public class TheWindowPower extends BlindPower{
    public static final String POWER_ID = makeID("TheWindow");


    public TheWindowPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.THE_WINDOW);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        addToBot(new ApplyPowerAction(owner,owner,new ThornsPower(owner,4),4));
    }

    @Override
    public void onRemove() {
        super.onRemove();
        addToBot(new RemoveSpecificPowerAction(owner,owner,"Thorns"));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
