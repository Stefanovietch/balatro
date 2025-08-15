package balatro.powers;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static balatro.balatroMod.makeID;

public class TheHookPower extends BlindPower{
    public static final String POWER_ID = makeID("TheHook");

    public TheHookPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.THE_HOOK);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        addToBot(new DiscardAction(AbstractDungeon.player,owner,2,false));
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        addToBot(new DiscardAction(AbstractDungeon.player,owner,2,false));
    }

    public void onRemove() {
        addToTop(new DrawCardAction(2));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
