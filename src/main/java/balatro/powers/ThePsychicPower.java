package balatro.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.HashMap;
import java.util.Map;

import static balatro.balatroMod.makeID;

public class ThePsychicPower extends BlindPower{
    public static final String POWER_ID = makeID("ThePsychic");
    public Map<AbstractCard,Integer> fishCards = new HashMap<>();


    public ThePsychicPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.THE_PSYCHIC);
        this.amount = 0;
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();

    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        this.amount++;
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        amount = 0;
    }

    public void onRemove() {

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
