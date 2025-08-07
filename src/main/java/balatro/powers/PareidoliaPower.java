package balatro.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static balatro.balatroMod.makeID;

public class PareidoliaPower extends BasePower{
    public static final String POWER_ID = makeID("Pareidolia");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public PareidoliaPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard cardInput, UseCardAction action) {
        cardInput.type = AbstractCard.CardType.POWER;
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        card.type = AbstractCard.CardType.POWER;
        super.onPlayCard(card, m);
    }


    @Override
    public void duringTurn() {
        super.duringTurn();
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            card.type = AbstractCard.CardType.POWER;
        }
        for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
            card.type = AbstractCard.CardType.POWER;
        }
        for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
            card.type = AbstractCard.CardType.POWER;
        }
        for (AbstractCard card : AbstractDungeon.player.exhaustPile.group) {
            card.type = AbstractCard.CardType.POWER;
        }
    }
}
