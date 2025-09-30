package balatro.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static balatro.balatroMod.makeID;

public class TribouletPower extends BasePower{
    public static final String POWER_ID = makeID("Triboulet");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public TribouletPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (card.costForTurn == 2) {
            return super.atDamageGive(damage * 2, type);
        }
        return super.atDamageGive(damage, type);
    }

    @Override
    public float modifyBlock(float blockAmount, AbstractCard card) {
        if (card.costForTurn == 2) {
            return super.modifyBlock(blockAmount * 2, card);
        }
        return super.modifyBlock(blockAmount, card);
    }
}
