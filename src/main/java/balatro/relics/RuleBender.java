package balatro.relics;

import balatro.actions.GainBlockOnAttackAction;
import balatro.character.baseDeck;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static balatro.balatroMod.makeID;

public class RuleBender extends BaseRelic{
    private static final String NAME = "RuleBender"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public RuleBender() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        if (info.type == DamageInfo.DamageType.NORMAL) {
            addToTop(new GainBlockAction(AbstractDungeon.player, info.base/2));
            //addToBot(new GainBlockOnAttackAction(target, info));
        }
    }

    @Override
    public void atBattleStartPreDraw() {
        super.atBattleStartPreDraw();
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            m.maxHealth *= 2;
            m.currentHealth *=2;
        }
    }
}
