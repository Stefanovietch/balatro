package balatro.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashMap;
import java.util.Map;

import static balatro.balatroMod.makeID;

public class TheMarkPower extends BlindPower{
    public static final String POWER_ID = makeID("TheMark");
    public Map<AbstractCard,Integer> markCards = new HashMap<>();


    public TheMarkPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.THE_MARK);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        int costUp;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.type == AbstractCard.CardType.POWER) {
                costUp = 3 - c.cost;
                markCards.put(c, costUp);
                c.modifyCostForCombat(costUp);
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.type == AbstractCard.CardType.POWER) {
                costUp = 3 - c.cost;
                markCards.put(c, costUp);
                c.modifyCostForCombat(costUp);
            }
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.POWER) {
                costUp = 3 - c.cost;
                markCards.put(c, costUp);
                c.modifyCostForCombat(costUp);
            }
        }
    }

    public void onRemove() {
        for (Map.Entry<AbstractCard, Integer> entry : markCards.entrySet()) {
            entry.getKey().modifyCostForCombat(-entry.getValue());
            entry.getKey().isCostModified = false;
        }
        markCards.clear();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
