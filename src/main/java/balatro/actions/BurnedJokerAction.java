package balatro.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Objects;

public class BurnedJokerAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");

    public static final String[] TEXT = uiStrings.TEXT;

    private final AbstractPlayer p = AbstractDungeon.player;
    private boolean upgraded;

    public BurnedJokerAction() {
        this.actionType = ActionType.DISCARD;
        this.duration = 0.1F;

    }
    @Override
    public void update() {
        if (!this.p.hand.isEmpty()) {
            addToTop(new SelectCardsInHandAction(99, TEXT[0],true,true, Objects::nonNull, list -> {
                if (!list.isEmpty()) {
                    for (AbstractCard card : list) {
                        card.upgrade();
                        addToBot(new DiscardSpecificCardAction(card, this.p.hand));
                    }
                }
            }));
        }
        this.isDone = true;
    }

    public boolean isUpgraded() {
        return upgraded;
    }


    public void setUpgraded(boolean upgraded) {
        this.upgraded = upgraded;
    }
}
