package balatro.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static balatro.balatroMod.makeID;

public class TheGoadPower extends BlindPower{
    public static final String POWER_ID = makeID("TheGoad");


    public TheGoadPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.THE_GOAD);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        AbstractDungeon.player.damage(new DamageInfo(owner,2, DamageInfo.DamageType.HP_LOSS));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
