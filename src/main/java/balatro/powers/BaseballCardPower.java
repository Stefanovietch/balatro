package balatro.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static balatro.balatroMod.makeID;

public class BaseballCardPower extends BasePower{
    public static final String POWER_ID = makeID("BaseballCard");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public BaseballCardPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (card.rarity == AbstractCard.CardRarity.UNCOMMON) {
            return super.atDamageGive(damage * this.amount / 10F, type, card);
        }
        return super.atDamageGive(damage, type, card);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount / 10F + DESCRIPTIONS[1];
    }

}
