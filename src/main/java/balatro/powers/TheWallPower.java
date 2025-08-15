package balatro.powers;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static balatro.balatroMod.makeID;

public class TheWallPower extends BlindPower{
    public static final String POWER_ID = makeID("TheWall");

    public TheWallPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.THE_WALL);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        int curHealth = owner.currentHealth;
        owner.increaseMaxHp(owner.maxHealth,true);
        owner.currentHealth = curHealth * 2;

    }

    public void onRemove() {
        owner.currentHealth = owner.currentHealth/2;
        owner.decreaseMaxHealth(owner.maxHealth/2);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
