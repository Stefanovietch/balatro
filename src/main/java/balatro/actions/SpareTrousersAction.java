package balatro.actions;

import balatro.cards.BaseCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.UUID;

public class SpareTrousersAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");

    public static final String[] TEXT = uiStrings.TEXT;

    private final DamageInfo info;
    private final UUID uuid;
    private final AbstractPlayer p = AbstractDungeon.player;
    private boolean upgraded;

    public SpareTrousersAction(DamageInfo info, BaseCard card) {
        this.info = info;
        setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
        this.uuid = card.uuid;
        this.upgraded = card.upgraded;
    }
    @Override
    public void update() {
        ArrayList<AbstractMonster> availableTargets = new ArrayList<>();
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!monster.isDead || !monster.isDying) {
                availableTargets.add(monster);
            }
        }
        int fatalHits = 0;
        if (availableTargets.size() <= 2) {
            for (AbstractMonster m : availableTargets) {
                m.damage(this.info);
                if ((m.isDying || m.currentHealth <= 0) && !m.halfDead &&
                        !m.hasPower("Minion")) {
                    fatalHits++;
                }
            }
        } else {
            AbstractMonster m1 = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            AbstractMonster m2 = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            while (m1 == m2) {
                m2 = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            }
            m1.damage(this.info);
            if ((m1.isDying || m1.currentHealth <= 0) && !m1.halfDead &&
                    !m1.hasPower("Minion")) {
                fatalHits++;
            }
            m2.damage(this.info);
            if ((m2.isDying || m2.currentHealth <= 0) && !m2.halfDead &&
                    !m2.hasPower("Minion")) {
                fatalHits++;
            }
        }

        if (fatalHits == 2) {
            for (AbstractCard c : p.masterDeck.group) {
                if (!c.uuid.equals(this.uuid))
                    continue;
                c.misc += c.magicNumber;
                c.applyPowers();
                c.baseDamage = c.misc;
                c.isDamageModified = false;
            }
            for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
                c.misc += c.magicNumber;
                c.applyPowers();
                c.baseDamage = c.misc;
            }
        }

        if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
            AbstractDungeon.actionManager.clearPostCombatActions();

        this.isDone = true;
    }

    public boolean isUpgraded() {
        return upgraded;
    }

    public void setUpgraded(boolean upgraded) {
        this.upgraded = upgraded;
    }
}
