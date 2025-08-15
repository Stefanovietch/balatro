package balatro.powers;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static balatro.balatroMod.makeID;

public class TheManaclePower extends BlindPower{
    public static final String POWER_ID = makeID("TheManacle");

    public TheManaclePower(AbstractCreature owner) {
        super(POWER_ID, owner, BlindType.THE_MANACLE);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        AbstractDungeon.player.gameHandSize--;
        addToBot(new DiscardAction(AbstractDungeon.player,this.owner,1,true));
    }

    public void onRemove() {
        AbstractDungeon.player.gameHandSize++;
        addToTop(new DrawCardAction(1));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
