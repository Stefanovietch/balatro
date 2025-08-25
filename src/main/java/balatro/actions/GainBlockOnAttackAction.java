package balatro.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class GainBlockOnAttackAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");

    public static final String[] TEXT = uiStrings.TEXT;
    private final AbstractPlayer p = AbstractDungeon.player;

    public GainBlockOnAttackAction(AbstractCreature target, DamageInfo info) {
        setValues(target, info);

        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
    }

    @Override
    public void update() {
        if (this.target != null) {
            int dmg = (int) (target.lastDamageTaken/2f);
            addToTop(new GainBlockAction(AbstractDungeon.player,dmg));
            this.isDone = true;
        }
    }
}
