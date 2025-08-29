package balatro.powers;

import balatro.balatroMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.HashMap;
import java.util.Map;

import static balatro.balatroMod.makeID;

public class TheFishPower extends BlindPower{
    public static final String POWER_ID = makeID("TheFish");
    public Map<AbstractCard,Integer> fishCards = new HashMap<>();
    public boolean endOfTurnDraw = true;
    public int drawSize = AbstractDungeon.player.masterHandSize;

    public TheFishPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.THE_FISH);
    }

    @Override
    public void onDrawCard(AbstractCard card) {
        if(drawSize >= 0) {
            drawSize--;
            if (drawSize == -1) {
                endOfTurnDraw = false;
            }
        }

        if (!this.endOfTurnDraw) {
            fishCards.put(card, card.cost);
            card.costForTurn = 3;
            card.isCostModified = true;
        }

    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        super.atEndOfTurnPreEndTurnCards(isPlayer);
        this.endOfTurnDraw = true;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        endOfTurnDraw = false;
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                this.isDone = true;
                for (Map.Entry<AbstractCard, Integer> entry : fishCards.entrySet()) {
                    entry.getKey().costForTurn = entry.getValue();
                    entry.getKey().isCostModified = false;
                }
                fishCards.clear();
                endOfTurnDraw = false;
            }
        });
    }

    public void onRemove() {
        for (Map.Entry<AbstractCard, Integer> entry : fishCards.entrySet()) {
            entry.getKey().costForTurn = entry.getValue();
            entry.getKey().isCostModified = false;
        }
        fishCards.clear();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
