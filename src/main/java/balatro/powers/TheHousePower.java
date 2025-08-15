package balatro.powers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static balatro.balatroMod.makeID;

public class TheHousePower extends BlindPower{
    public static final String POWER_ID = makeID("TheHouse");
    public Map<AbstractCard,Integer> houseCards = new HashMap<>();

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


    public void onRemove() {
        for (Map.Entry<AbstractCard, Integer> entry : houseCards.entrySet()) {
            entry.getKey().costForTurn = entry.getValue();
            entry.getKey().isCostModified = false;
        }
        houseCards.clear();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (houseCards.isEmpty()) {
                    int cost;
                    for (AbstractCard c : AbstractDungeon.player.hand.group) {
                        if (c.cost >= 0) {
                            cost = c.cost;
                            houseCards.put(c, cost);
                            c.costForTurn = 3;
                        }
                    }
                }
            }
        });
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
