package balatro.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.HashMap;
import java.util.Map;

import static balatro.balatroMod.makeID;

public class TheSerpentPower extends BlindPower{
    public static final String POWER_ID = makeID("TheSerpent");
    public Map<AbstractPower,Integer> serpentPowers = new HashMap<>();

    public TheSerpentPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.THE_SERPENT);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        for (AbstractPower p : owner.powers) {
            if (p.type == PowerType.DEBUFF){
                serpentPowers.put(p,p.amount);
                addToBot(new RemoveSpecificPowerAction(owner,owner,p));
            }
        }
    }

    @Override
    public void onRemove() {
        super.onRemove();
        for (Map.Entry<AbstractPower, Integer> entry : serpentPowers.entrySet()) {
            addToBot(new ApplyPowerAction(owner,owner,entry.getKey(),entry.getValue()));
        }
        serpentPowers.clear();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.type != PowerType.DEBUFF) {
            super.onApplyPower(power, target, source);
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
