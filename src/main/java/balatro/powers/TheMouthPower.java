package balatro.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static balatro.balatroMod.makeID;

public class TheMouthPower extends BlindPower{
    public static final String POWER_ID = makeID("TheMouth");
    public AbstractCard.CardType mouthType1;
    public AbstractCard.CardType mouthType2;

    public TheMouthPower(AbstractCreature owner) {
        super(POWER_ID, owner, BlindType.THE_MOUTH);
        mouthType1 = null;
        mouthType2 = null;
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (mouthType1 == null) {
            mouthType1 = card.type;
        } else if(mouthType2 == null && card.type != mouthType1) {
            mouthType2 = card.type;
        }
    }

    public boolean getPlayable(AbstractCard.CardType type) {
        return mouthType1 == null || mouthType2 == null || mouthType1 == type || mouthType2 == type;
    }

    public void onRemove() {
        mouthType1 = null;
        mouthType2 = null;
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        mouthType1 = null;
        mouthType2 = null;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


}
