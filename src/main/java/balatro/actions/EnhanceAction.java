package balatro.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.UUID;

public class EnhanceAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");

    public static final String[] TEXT = uiStrings.TEXT;

    private final UUID uuid;
    private final AbstractPlayer p = AbstractDungeon.player;
    private boolean upgraded;
    private final AbstractCard card;

    public EnhanceAction(AbstractCard cardToEnhance) {
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.1F;
        this.uuid = cardToEnhance.uuid;
        this.upgraded = cardToEnhance.upgraded;
        this.card = cardToEnhance;
    }
    @Override
    public void update() {
        for (AbstractCard c : p.masterDeck.group) {
            if (!c.uuid.equals(this.uuid))
                continue;
            c.misc++;
            c.applyPowers();
        }
        for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            c.misc++;
            c.applyPowers();
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
