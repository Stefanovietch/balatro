package balatro.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static balatro.balatroMod.makeID;

public class TheFlintPower extends BlindPower{
    public static final String POWER_ID = makeID("TheFlint");
    int initialVulnerable;
    int initialWeak;

    public TheFlintPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.THE_FLINT);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        initialVulnerable = 0;
        initialWeak = 0;
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (Objects.equals(p.ID, "Vulnerable")) {
                initialVulnerable = p.amount;
                addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,owner,"Vulnerable"));
            }
            if (Objects.equals(p.ID, "Weak")) {
                initialWeak = p.amount;
                addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,owner,"Weakened"));
            }
        }
        AbstractPower vulnerablePower = new VulnerablePower(AbstractDungeon.player,-1,true);
        vulnerablePower.type = PowerType.BUFF;
        addToBot(new ApplyPowerAction(AbstractDungeon.player,owner, vulnerablePower, -1));
        AbstractPower weakPower = new WeakPower(AbstractDungeon.player,-1,true);
        weakPower.type = PowerType.BUFF;
        addToBot(new ApplyPowerAction(AbstractDungeon.player,owner,weakPower, -1));
    }


    public void onRemove() {
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,owner,"Vulnerable"));
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,owner,"Weakened"));

        if (initialVulnerable > 0) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player,owner,new VulnerablePower(AbstractDungeon.player,initialVulnerable,true), initialVulnerable));
        }
        if (initialWeak > 0) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player,owner,new WeakPower(AbstractDungeon.player,initialWeak,true), initialWeak));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
