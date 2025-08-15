package balatro.powers;

import balatro.balatroMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.AutoplayCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static balatro.balatroMod.makeID;

public class CeruleanBellPower extends BlindPower{
    public static final String POWER_ID = makeID("CeruleanBell");
    AbstractCard cardToPlay;

    public CeruleanBellPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.CERULEAN_BELL);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        addToBot(new AbstractGameAction() {
            public void update() {
                isDone = true;
                cardToPlay = AbstractDungeon.player.hand.getRandomCard(true);
                addToBot(new NewQueueCardAction(cardToPlay, AbstractDungeon.getRandomMonster()));
                addToTop(new UnlimboAction(cardToPlay));
            }
        });


    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
