package balatro.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static balatro.balatroMod.makeID;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.aiRng;

public class LuckyCatPower extends BasePower{
    public static final String POWER_ID = makeID("LuckyCat");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private int prob;

    public LuckyCatPower(AbstractCreature owner, int amount) {
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
        this.description = DESCRIPTIONS[0] + this.prob + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]
                + this.prob + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4];
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        if (aiRng.randomBoolean(this.prob/4F)) {
            addToBot(new GainBlockAction(this.owner, this.amount));
        }
        if (aiRng.randomBoolean(this.prob/4F)) {
            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, this.amount, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (card.cardID.equals("Balatro:OopsAll6s")) {
            this.prob = this.prob * 2;
            updateDescription();
        }
    }
}
