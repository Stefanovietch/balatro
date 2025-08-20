package balatro.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static balatro.balatroMod.makeID;

public class MrBonesPower extends BasePower{
    public static final String POWER_ID = makeID("MrBones");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public MrBonesPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (this.amount >= 1 && this.owner.currentHealth <= damageAmount) {
            damageAmount = 0;
            this.owner.currentHealth = 0;
            int healAmt = AbstractDungeon.player.maxHealth / 4;
            if (healAmt < 1)
                healAmt = 1;
            AbstractDungeon.player.heal(healAmt, true);

            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.cardID.equals("Balatro:MrBones")) {
                    AbstractDungeon.player.masterDeck.removeCard(c);
                    break;
                }
            }
            this.reducePower(1);
            if (this.amount <= 0) {
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            }
        }
        return super.onLoseHp(damageAmount);
    }
}
