package balatro.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static balatro.balatroMod.makeID;

public class TheEyePower extends BlindPower{
    public static final String POWER_ID = makeID("TheEye");
    public AbstractCard.CardType eyeType;

    public TheEyePower(AbstractCreature owner) {
        super(POWER_ID, owner, BlindType.THE_EYE);
        eyeType = null;
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        eyeType = card.type;
    }

    public boolean getPlayable(AbstractCard.CardType type) {
        return type != eyeType;
    }

    public void onRemove() {
        eyeType = null;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


}
