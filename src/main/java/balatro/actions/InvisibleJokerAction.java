package balatro.actions;

import balatro.balatroMod;
import balatro.cards.InvisibleJoker;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRng;

public class InvisibleJokerAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");

    public static final String[] TEXT = uiStrings.TEXT;
    private final AbstractCard invisCard;
    private final AbstractPlayer p = AbstractDungeon.player;
    private boolean upgraded;


    AbstractCard c;

    public InvisibleJokerAction(AbstractCard invisCard) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.1F;
        this.invisCard = invisCard;
    }

    @Override
    public void update() {
        for (AbstractCard c : p.masterDeck.group) {
            if (c.uuid.equals(this.invisCard.uuid) && c instanceof InvisibleJoker) {
                c.misc += 1;
            }
        }
        for (AbstractCard c : GetAllInBattleInstances.get(this.invisCard.uuid)) {
            c.misc += 1;
        }
        if (this.invisCard.misc >= 2) {
            ArrayList<AbstractCard> possibleCardsToCopy = new ArrayList<>();
            int thisIndex = -1;
            for (AbstractCard c : p.masterDeck.group) {
                if (c.type != AbstractCard.CardType.CURSE && !c.uuid.equals(this.invisCard.uuid)) {
                    possibleCardsToCopy.add(c);
                }
                if (c.uuid.equals(this.invisCard.uuid)) {
                    thisIndex = p.masterDeck.group.indexOf(c);
                }
            }
            if (thisIndex >= 0) {
                AbstractCard newCard = possibleCardsToCopy.get(cardRng.random(possibleCardsToCopy.size()-1)).makeCopy();
                if (newCard.cost > 0) {
                    newCard.updateCost(-newCard.cost);
                    newCard.isCostModified = true;
                }
                p.masterDeck.group.set(thisIndex, newCard);
            } else {
                balatroMod.logger.info("No Invisible Joker in master deck.");
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
