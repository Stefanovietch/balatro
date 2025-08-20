package balatro.relics;

import balatro.cards.*;
import balatro.character.baseDeck;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import static balatro.balatroMod.makeID;

public class GlowUp extends BaseRelic{
    private static final String NAME = "GlowUp"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public GlowUp() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onEquip() {
        super.onEquip();
        CardGroup legendaryCardPool = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        legendaryCardPool.addToRandomSpot(new Canio());
        legendaryCardPool.addToRandomSpot(new Triboulet());
        legendaryCardPool.addToRandomSpot(new Yorick());
        legendaryCardPool.addToRandomSpot(new Chicot());
        legendaryCardPool.addToRandomSpot(new Perkeo());
        AbstractCard card = legendaryCardPool.getRandomCard(true).makeCopy();
        AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(card.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
