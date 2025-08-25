package balatro.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class GlassJokerAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");

    public static final String[] TEXT = uiStrings.TEXT;

    private final AbstractPlayer p = AbstractDungeon.player;
    private boolean upgraded;
    private AbstractCard sourceCard;
    private DamageInfo info;
    public int[] damage;

    public GlassJokerAction(int[] amount, DamageInfo info, AbstractCard source) {
        this.info = info;
        this.sourceCard = source;
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
        this.damage = amount;

    }
    @Override
    public void update() {
        int temp = (AbstractDungeon.getCurrRoom()).monsters.monsters.size();
        for (int i = 0; i < temp; i++) {
            AbstractMonster monster = (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i);
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(monster.hb.cX, monster.hb.cY, AbstractGameAction.AttackEffect.NONE));
            monster.damage(this.info);
            if ((monster.isDying || monster.currentHealth <= 0) && !monster.halfDead &&
                    !monster.hasPower("Minion")) {
                for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                    if (sourceCard.uuid.equals(card.uuid)) {
                        AbstractDungeon.player.masterDeck.removeCard(card);
                        break;
                    }
                }
                sourceCard.purgeOnUse = true;
            }
        }
        if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
            AbstractDungeon.actionManager.clearPostCombatActions();
        this.isDone = true;
        for (AbstractPower p : AbstractDungeon.player.powers)
            p.onDamageAllEnemies(this.damage);
    }

    public boolean isUpgraded() {
        return upgraded;
    }


    public void setUpgraded(boolean upgraded) {
        this.upgraded = upgraded;
    }
}
