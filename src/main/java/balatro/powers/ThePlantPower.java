package balatro.powers;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashMap;
import java.util.Map;

import static balatro.balatroMod.makeID;

public class ThePlantPower extends BlindPower{
    public static final String POWER_ID = makeID("ThePlant");
    public Map<AbstractCard,Integer> armCards = new HashMap<>();


    public ThePlantPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.THE_PLANT);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();

    }

    public void onRemove() {

    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        super.atEndOfTurnPreEndTurnCards(isPlayer);
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.POWER) {
                c.isEthereal = true;
                addToBot(new ExhaustSpecificCardAction(c,AbstractDungeon.player.hand));
            }
        }

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
