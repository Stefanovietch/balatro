package balatro.relics;

import balatro.character.baseDeck;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static balatro.balatroMod.makeID;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.aiRng;

public class Retrograde extends BaseRelic{
    private static final String NAME = "Retrograde"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public Retrograde() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
        super.atBattleStartPreDraw();
        int amountToUpgrade = Math.floorDiv(AbstractDungeon.player.drawPile.size(), 2);
        CardGroup cardsToUpgrade = AbstractDungeon.player.drawPile.getUpgradableCards();
        while (cardsToUpgrade.size() > amountToUpgrade) {
            cardsToUpgrade.removeCard(cardsToUpgrade.getRandomCard(true));
        }
        for (AbstractCard card : cardsToUpgrade.group) {
            card.upgrade();
        }
    }
}
