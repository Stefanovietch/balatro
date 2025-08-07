package balatro.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import static balatro.balatroMod.makeID;

public class LoyaltyCardPower extends BasePower{
    public static final String POWER_ID = makeID("LoyaltyCard");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static int LoyaltyIdOffset;

    public LoyaltyCardPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.ID = POWER_ID + LoyaltyIdOffset;
        this.amount = 5;
        LoyaltyIdOffset++;
        updateDescription();
    }

    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else if (amount == 2) {
            this.description = DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
        }
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (amount == 1) {
            return super.atDamageGive(damage*4, type);
        }
        return super.atDamageGive(damage, type);
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        super.onAfterCardPlayed(usedCard);
        amount--;
        if (amount == 0) {
            amount = 5;
        }
        updateDescription();
    }
}
