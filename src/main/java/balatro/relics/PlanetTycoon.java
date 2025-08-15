package balatro.relics;

import balatro.character.baseDeck;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;

import static balatro.balatroMod.makeID;

public class PlanetTycoon extends BaseRelic{
    private static final String NAME = "PlanetTycoon"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.SOLID; //The sound played when the relic is clicked.

    public PlanetTycoon() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public void onEquip() {
        for (RewardItem reward : AbstractDungeon.combatRewardScreen.rewards) {
            if (reward.cards != null)
                for (AbstractCard c : reward.cards)
                    onPreviewObtainCard(c);
        }
    }

    public void onPreviewObtainCard(AbstractCard c) {
        onObtainCard(c);
    }

    public void onObtainCard(AbstractCard c) {
        if (c.rarity == AbstractCard.CardRarity.COMMON && c.canUpgrade() && !c.upgraded)
            c.upgrade();
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
