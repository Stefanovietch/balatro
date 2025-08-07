package balatro.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DrawAllCostAction extends AbstractGameAction {
    private final int cardCostToDraw;
    private final AbstractPlayer p;

    public DrawAllCostAction(int cost) {
        this.p = AbstractDungeon.player;
        this.cardCostToDraw = cost;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (!this.p.drawPile.isEmpty()) {
            for (AbstractCard card : this.p.drawPile.group) {
                if (card.cost == this.cardCostToDraw || (card.cost == 0 && card.freeToPlayOnce)) {
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            if (AbstractDungeon.player.hand.group.size() >= 10) {
                                AbstractDungeon.player.drawPile.moveToDiscardPile(card);
                                AbstractDungeon.player.createHandIsFullDialog();
                            } else {
                                AbstractDungeon.player.drawPile.moveToHand(card, AbstractDungeon.player.drawPile);
                            }
                            this.isDone = true;
                        }
                    });
                }
            }
        }
        tickDuration();
        this.isDone = true;
    }
}
