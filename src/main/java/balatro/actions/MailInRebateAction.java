package balatro.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Objects;

public class MailInRebateAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");

    public static final String[] TEXT = uiStrings.TEXT;


    private final AbstractPlayer p = AbstractDungeon.player;
    private boolean upgraded;
    private final int gold;
    private final AbstractCard.CardType type;

    public MailInRebateAction(AbstractPlayer p, int gold, AbstractCard.CardType type) {
        this.actionType = ActionType.DISCARD;
        this.duration = 0.1F;
        this.gold = gold;
        this.type = type;
    }
    @Override
    public void update() {
        if (this.p.hand.size() == 1) {
            AbstractCard card = this.p.hand.getTopCard();
            addToBot(new DiscardSpecificCardAction(card, this.p.hand));
            if (card.type == type) {
                addToBot(new GainGoldAction(gold));
            }
            AbstractDungeon.player.hand.applyPowers();
            }

        if (this.p.hand.size() > 1) {
            addToTop(new SelectCardsInHandAction(1, TEXT[0],false,false, Objects::nonNull, list -> {
                AbstractCard card = list.get(0);
                addToBot(new DiscardSpecificCardAction(card, this.p.hand));
                if (card.type == type) {
                    addToBot(new GainGoldAction(gold));
                }
            }));

            AbstractDungeon.player.hand.applyPowers();
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
