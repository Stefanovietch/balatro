package balatro.powers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;

import static balatro.balatroMod.makeID;

public class TheOxPower extends  BlindPower{
    public static final String POWER_ID = makeID("TheOx");
    public AbstractCard targetCard;

    public TheOxPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.THE_OX);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        if (!AbstractDungeon.player.hand.isEmpty()) {
            targetCard = AbstractDungeon.player.hand.getRandomCard(true);
        } else {
            targetCard =  AbstractDungeon.player.drawPile.getTopCard();
        }
        targetCard.glowColor = Color.RED.cpy();
        targetCard.beginGlowing();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (card == targetCard) {
            AbstractDungeon.player.gold = 0;
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        targetCard.glowColor = new Color(0.2F, 0.9F, 1.0F, 0.25F);
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                this.isDone = true;
                targetCard = AbstractDungeon.player.hand.getRandomCard(true);
                targetCard.glowColor = Color.RED.cpy();
            }
        });
    }

    public void onRemove() {
        targetCard.glowColor = new Color(0.2F, 0.9F, 1.0F, 0.25F);
        targetCard = null;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
