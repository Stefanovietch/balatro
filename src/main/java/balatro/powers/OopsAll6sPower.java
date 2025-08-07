package balatro.powers;

import balatro.balatroMod;
import balatro.cards.BaseCard;
import balatro.util.GeneralUtils;
import balatro.util.ProbabilityPower;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static balatro.balatroMod.makeID;

public class OopsAll6sPower extends BasePower implements ProbabilityPower {
    public static final String POWER_ID = makeID("OopsAll6s");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public OopsAll6sPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(AbstractCard c, UseCardAction action) {
        if (c instanceof BaseCard) {
            BaseCard card = (BaseCard) c;
            balatroMod.logger.info("card prob: {}", card.customVar("probability"));
            balatroMod.logger.info("prob modify: {}", GeneralUtils.getProbabilityModifier());
        }

    }

    public int modifyProbability(int prob) {
        return prob + this.amount;
        //return (int) (prob * Math.pow(2,this.amount));
    }
}
