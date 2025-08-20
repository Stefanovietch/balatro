package balatro.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static balatro.balatroMod.makeID;

public class TheClubPower extends BlindPower{
    public static final String POWER_ID = makeID("TheClub");


    public TheClubPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.THE_CLUB);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        addToBot(new MakeTempCardInDrawPileAction(new Wound(),1,true,true));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
