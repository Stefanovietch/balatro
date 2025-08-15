package balatro.powers;

import balatro.actions.LoseGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.HashMap;
import java.util.Map;

import static balatro.balatroMod.makeID;

public class TheToothPower extends BlindPower{
    public static final String POWER_ID = makeID("TheTooth");


    public TheToothPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.THE_TOOTH);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        addToBot(new LoseGoldAction(10));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
