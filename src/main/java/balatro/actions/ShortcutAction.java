package balatro.actions;

import balatro.balatroMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ShortcutAction extends AbstractGameAction {
    public final String[] TEXT = (CardCrawlGame.languagePack.getUIString("BetterToHandAction")).TEXT;
    private final AbstractPlayer p = AbstractDungeon.player;
    private boolean upgraded;
    private final int costReduction;

    public ShortcutAction(int costReduction) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = 0.1F;
        this.costReduction = costReduction;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (this.p.discardPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (this.p.discardPile.size() == 1) {
                AbstractCard c = this.p.discardPile.group.get(0);
                if (this.p.hand.size() < 10) {
                    this.p.hand.addToHand(c);
                    c.setCostForTurn(Math.max(0, c.costForTurn - costReduction));
                    this.p.discardPile.removeCard(c);
                }
                c.lighten(false);
                c.applyPowers();

                this.isDone = true;
                return;
            }
            if (this.p.discardPile.size() > 1) {
                AbstractDungeon.gridSelectScreen.open(this.p.discardPile, 1, TEXT[0], false);
            }
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            if (this.p.hand.size() < 10) {
                this.p.hand.addToHand(card);
                card.setCostForTurn(Math.max(0, card.costForTurn - costReduction));
                this.p.discardPile.removeCard(card);
            }
            card.lighten(false);
            card.unhover();
            card.applyPowers();

            for (AbstractCard c : this.p.discardPile.group) {
                c.unhover();
                c.target_x = CardGroup.DISCARD_PILE_X;
                c.target_y = 0.0F;
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        tickDuration();
        if (this.isDone)
            for (AbstractCard c : this.p.hand.group)
                c.applyPowers();

    }

    public boolean isUpgraded() {
        return upgraded;
    }


    public void setUpgraded(boolean upgraded) {
        this.upgraded = upgraded;
    }
}
