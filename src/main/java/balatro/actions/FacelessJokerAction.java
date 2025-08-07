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

public class FacelessJokerAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");

    public static final String[] TEXT = uiStrings.TEXT;
    private final AbstractPlayer p = AbstractDungeon.player;
    private boolean upgraded;
    private final int amount;
    private final int gold;
    private boolean allPowers = true;

    public FacelessJokerAction(AbstractPlayer p, int amountCards, int goldGain) {
        this.actionType = ActionType.DISCARD;
        this.duration = 0.1F;
        this.amount = amountCards;
        this.gold = goldGain;
    }
    @Override
    public void update() {
        if (this.p.hand.size() <= this.amount) {
            for(int i = 0; i < this.p.hand.size(); ++i) {
                AbstractCard c = this.p.hand.getTopCard();
                if(c.type != AbstractCard.CardType.POWER) {
                    allPowers = false;}
                addToBot(new DiscardSpecificCardAction(c, this.p.hand));
            }
            if(allPowers && this.amount == this.p.hand.size()) {
                addToBot(new GainGoldAction(gold));
            }
            AbstractDungeon.player.hand.applyPowers();
        }

        if (this.p.hand.size() > this.amount) {
            addToTop(new SelectCardsInHandAction(2, TEXT[0],false,false, Objects::nonNull, list -> {
                for (AbstractCard card : list) {
                    if(card.type != AbstractCard.CardType.POWER) {
                        allPowers = false;}
                    addToBot(new DiscardSpecificCardAction(card, this.p.hand));
                }
                if(allPowers) {
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
