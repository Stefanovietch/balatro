package balatro.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static balatro.balatroMod.makeID;

public class VioletVesselPower extends BlindPower{
    public static final String POWER_ID = makeID("VioletVessel");

    public VioletVesselPower(AbstractCreature owner) {
        super(POWER_ID, owner,BlindType.VIOLET_VESSEL);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        int curHealth = owner.currentHealth;
        owner.increaseMaxHp(owner.maxHealth*2,true);
        owner.currentHealth = curHealth * 3;

    }

    public void onRemove() {
        owner.currentHealth = owner.currentHealth/3;
        owner.decreaseMaxHealth(owner.maxHealth * 2/3);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
