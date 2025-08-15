package balatro.actions;

import balatro.cards.BaseCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.UUID;

import static basemod.BaseMod.logger;

public class DNAAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");

    public static final String[] TEXT = uiStrings.TEXT;
    private final int DNAindex;
    private final AbstractPlayer p = AbstractDungeon.player;
    private boolean upgraded;


    AbstractCard c;

    public DNAAction(int DNAindex) {
        this.DNAindex = DNAindex;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.1F;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> cards = AbstractDungeon.actionManager.cardsPlayedThisCombat;
        if (cards.size() >= 2) {
            AbstractCard card = cards.get(cards.size()-2).makeStatEquivalentCopy();
            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(card));
            p.masterDeck.group.set(this.DNAindex, card.makeCopy());
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
