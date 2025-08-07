package balatro.actions;

import balatro.balatroMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Objects;

public class ShowmanAction extends AbstractGameAction {
    public final String[] TEXT = (CardCrawlGame.languagePack.getUIString("BetterToHandAction")).TEXT;
    private final AbstractPlayer p = AbstractDungeon.player;
    private boolean upgraded;

    public ShowmanAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = 0.1F;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (this.p.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (this.p.drawPile.size() == 1) {
                AbstractCard c = this.p.discardPile.group.get(0);
                if (this.p.hand.size() < 10) {
                    this.p.hand.addToHand(c);
                    this.p.drawPile.removeCard(c);
                }
                c.lighten(false);
                c.applyPowers();

                if (!this.p.discardPile.isEmpty()) {
                    for (AbstractCard card : this.p.discardPile.group) {
                        if (card.cardID.equals(c.cardID)) {
                            addToBot(new AbstractGameAction() {
                                @Override
                                public void update() {
                                    if (AbstractDungeon.player.hand.group.size() >= 10) {
                                        AbstractDungeon.player.createHandIsFullDialog();
                                    } else {
                                        AbstractDungeon.player.discardPile.moveToHand(card, AbstractDungeon.player.discardPile);
                                    }
                                    this.isDone = true;
                                }
                            });
                            card.lighten(false);
                            card.applyPowers();
                        }
                    }
                }

                this.isDone = true;
                return;
            }
            if (this.p.drawPile.size() > 1) {
                AbstractDungeon.gridSelectScreen.open(this.p.drawPile, 1, TEXT[0], false);
            }
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            if (this.p.hand.size() < 10) {
                this.p.drawPile.moveToHand(c, this.p.drawPile);
            }
            c.lighten(false);
            c.unhover();
            c.applyPowers();

            for (AbstractCard card : this.p.drawPile.group) {
                if (card.cardID.equals(c.cardID)) {
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            if (AbstractDungeon.player.hand.group.size() >= 10) {
                                AbstractDungeon.player.createHandIsFullDialog();
                            } else {
                                AbstractDungeon.player.drawPile.moveToHand(card, AbstractDungeon.player.drawPile);
                            }
                            this.isDone = true;
                        }
                    });
                    card.lighten(false);
                    card.applyPowers();
                }
            }
            if (!this.p.discardPile.isEmpty()) {
                for (AbstractCard card : this.p.discardPile.group) {
                    if (card.cardID.equals(c.cardID)) {
                        addToBot(new AbstractGameAction() {
                            @Override
                            public void update() {
                                if (AbstractDungeon.player.hand.group.size() >= 10) {
                                    AbstractDungeon.player.createHandIsFullDialog();
                                } else {
                                    AbstractDungeon.player.discardPile.moveToHand(card, AbstractDungeon.player.discardPile);
                                }
                                this.isDone = true;
                            }
                        });
                        card.lighten(false);
                        card.applyPowers();
                    }
                }
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        tickDuration();
        if (this.isDone) {
            for (AbstractCard c : this.p.hand.group) {
                c.applyPowers();
            }
        }
    }

    public boolean isUpgraded() {
        return upgraded;
    }


    public void setUpgraded(boolean upgraded) {
        this.upgraded = upgraded;
    }
}
