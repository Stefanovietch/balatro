package balatro.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;

import java.util.HashMap;
import java.util.Map;

import static balatro.balatroMod.makeID;

public class TheWheelPower extends BlindPower{
    public static final String POWER_ID = makeID("TheWheel");
    private int prob;
    public Map<AbstractCard,Integer> wheelCards = new HashMap<>();


    public TheWheelPower(AbstractCreature owner) {
        super(POWER_ID, owner, BlindType.THE_WHEEL);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        this.prob = 1;
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p.ID.equals("Balatro:OopsAll6s")) {
                this.prob = (int) Math.pow(2,p.amount);
                break;
            }
        }
        updateDescription();
    }

    public void onDrawCard(AbstractCard card) {
        Random chanceRandom = new Random();
        int costUp;
        if (chanceRandom.randomBoolean(this.prob/7F)) {
            if (card.cost >= 0) {
                costUp = 3 - card.cost;
                wheelCards.put(card, costUp);
                card.modifyCostForCombat(costUp);
            }
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

    public void onRemove() {
        for (Map.Entry<AbstractCard, Integer> entry : wheelCards.entrySet()) {
            entry.getKey().modifyCostForCombat(-entry.getValue());
            entry.getKey().isCostModified = false;
        }
        wheelCards.clear();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.prob + DESCRIPTIONS[1] ;
    }
}
