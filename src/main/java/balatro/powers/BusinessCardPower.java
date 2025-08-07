package balatro.powers;

import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Objects;

import static balatro.balatroMod.makeID;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.aiRng;

public class BusinessCardPower extends BasePower{
    public static final String POWER_ID = makeID("BusinessCard");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private int prob;

    public BusinessCardPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.prob = 1;
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p.ID.equals("Balatro:OopsAll6s")) {
                this.prob = (int) Math.pow(2,p.amount);
                break;
            }
        }
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.prob + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (card.cardID.equals("Balatro:OopsAll6s")) {
            this.prob = this.prob * 2;
            updateDescription();
        }
        if (card.type == AbstractCard.CardType.POWER && aiRng.randomBoolean(this.prob/4F)) {
            addToBot(new GainGoldAction(this.amount));
        }
    }
}
