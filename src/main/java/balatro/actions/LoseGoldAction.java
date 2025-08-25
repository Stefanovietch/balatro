package balatro.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class LoseGoldAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");

    public static final String[] TEXT = uiStrings.TEXT;
    private boolean upgraded;

    public LoseGoldAction(int amount) {
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.1F;
        this.amount = amount;

    }
    @Override
    public void update() {
        AbstractDungeon.player.loseGold(this.amount);
        this.isDone = true;
    }

    public boolean isUpgraded() {
        return upgraded;
    }


    public void setUpgraded(boolean upgraded) {
        this.upgraded = upgraded;
    }
}
