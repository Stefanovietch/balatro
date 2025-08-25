package balatro.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TheIdolAction extends AbstractGameAction {
    private final AbstractPlayer p = AbstractDungeon.player;
    private boolean upgraded;
    private final int cardCost;
    private final AbstractCard.CardType cardType;

    public TheIdolAction(AbstractCard.CardType cardType, int cardCost) {
        this.actionType = ActionType.DRAW;
        this.duration = 0.1F;
        this.cardType = cardType;
        this.cardCost = cardCost;
    }

    @Override
    public void update() {
        if(!p.drawPile.isEmpty()) {
            CardGroup correctCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard card : p.drawPile.group) {
                if (card.type == cardType && card.costForTurn == cardCost) {
                    correctCards.addToRandomSpot(card);
                }
            }
            if (!correctCards.isEmpty()) {
                AbstractCard cardToDraw = correctCards.getBottomCard();
                cardToDraw.setCostForTurn(0);
                p.drawPile.moveToHand(cardToDraw);
            }
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
