package balatro.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashMap;
import java.util.Map;

import static balatro.balatroMod.makeID;

public class TheHousePower extends BlindPower{
    public static final String POWER_ID = makeID("TheHouse");
    public Map<AbstractCard,Integer> houseCards = new HashMap<>();
    public int drawSize = AbstractDungeon.player.masterHandSize;

    public TheHousePower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.THE_HOUSE);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        if (!AbstractDungeon.player.hand.isEmpty()) {
            int cost;
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.cost >= 0) {
                    cost = c.cost;
                    houseCards.put(c, cost);
                    c.costForTurn = 3;
                    c.isCostModified = true;
                }
            }
        }
    }

    @Override
    public void onDrawCard(AbstractCard card) {
        if(drawSize > 0) {
            drawSize--;
            if (card.cost >= 0) {
                int cost = card.cost;
                houseCards.put(card, cost);
                card.costForTurn = 3;
                card.isCostModified = true;
            }
        }
    }


    public void onRemove() {
        for (Map.Entry<AbstractCard, Integer> entry : houseCards.entrySet()) {
            entry.getKey().costForTurn = entry.getValue();
            entry.getKey().isCostModified = false;
        }
        houseCards.clear();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
