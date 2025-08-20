package balatro.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashMap;
import java.util.Map;

import static balatro.balatroMod.makeID;

public class TheArmPower extends BlindPower{
    public static final String POWER_ID = makeID("TheArm");
    public Map<AbstractCard,Integer> armCards = new HashMap<>();


    public TheArmPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.THE_ARM);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.upgraded) {
                armCards.put(c, c.cost);
                c.modifyCostForCombat(1);
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.upgraded) {
                armCards.put(c, c.cost);
                c.modifyCostForCombat(1);
            }
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.upgraded) {
                armCards.put(c, c.cost);
                c.modifyCostForCombat(1);
            }
        }
    }

    public void onRemove() {
        for (Map.Entry<AbstractCard, Integer> entry : armCards.entrySet()) {
            entry.getKey().modifyCostForCombat(-1);
            entry.getKey().isCostModified = false;
        }
        armCards.clear();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
