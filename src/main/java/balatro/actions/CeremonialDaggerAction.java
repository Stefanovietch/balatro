package balatro.actions;

import balatro.cards.BaseCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Objects;
import java.util.UUID;

public class CeremonialDaggerAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");

    public static final String[] TEXT = uiStrings.TEXT;


    private final DamageInfo info;
    private final UUID uuid;
    private final AbstractPlayer p = AbstractDungeon.player;
    private boolean upgraded;
    private int cardValue;
    AbstractCard c;

    public CeremonialDaggerAction(AbstractCreature target, DamageInfo info, BaseCard card) {
        this.info = info;
        setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
        this.uuid = card.uuid;
        this.upgraded = card.upgraded;
    }
    @Override
    public void update() {
        cardValue = 0;
        if (!this.p.hand.isEmpty()) {
            if (this.p.hand.size() == 1) {
                c = this.p.hand.getBottomCard();
                if ((this.p.hand.getBottomCard()).costForTurn == -1) {
                    cardValue = EnergyPanel.getCurrentEnergy();
                } else if ((this.p.hand.getBottomCard()).costForTurn > 0) {
                    cardValue = this.p.hand.getBottomCard().costForTurn;
                }
                increaseDmg();
                this.p.hand.moveToExhaustPile(this.p.hand.getBottomCard());
            }
            if (this.p.hand.size() > 1) {
                if (this.upgraded) {
                    addToTop(new SelectCardsInHandAction(1,TEXT[0],false,false, Objects::nonNull, list -> {
                        AbstractCard c = list.get(0);
                        if (c.costForTurn == -1) {
                            cardValue = EnergyPanel.getCurrentEnergy();
                        } else if (c.costForTurn > 0) {
                            cardValue = c.costForTurn;
                        }
                        increaseDmg();
                        addToBot(new ExhaustSpecificCardAction(c, this.p.hand));
                    }));
                } else {
                    AbstractCard c = this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                    if (c.costForTurn == -1) {
                        cardValue = EnergyPanel.getCurrentEnergy();
                    } else if (c.costForTurn > 0) {
                        cardValue = c.costForTurn;
                    }
                    increaseDmg();
                    this.p.hand.moveToExhaustPile(c);
                }
            }
        }

        if (this.target != null) {
            this.target.damage(this.info);
        }
        if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
            AbstractDungeon.actionManager.clearPostCombatActions();
        this.isDone = true;
    }

    public boolean isUpgraded() {
        return upgraded;
    }

    public void increaseDmg(){
        for (AbstractCard c : p.masterDeck.group) {
            if (!c.uuid.equals(this.uuid))
                continue;
            c.misc += 2 * cardValue;
            c.applyPowers();
            c.baseDamage = c.misc;
            c.isDamageModified = false;
        }
        for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            c.misc += 2 * cardValue;
            c.applyPowers();
            c.baseDamage = c.misc;
        }
    }



    public void setUpgraded(boolean upgraded) {
        this.upgraded = upgraded;
    }
}
