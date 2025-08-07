package balatro.relics;

import balatro.character.baseDeck;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static balatro.balatroMod.makeID;

public class YouGetWhatYouGet extends BaseRelic{
    private static final String NAME = makeID(YouGetWhatYouGet.class.getSimpleName()); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private boolean triggered = false;

    public YouGetWhatYouGet() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        triggered = false;
    }

    public void onPlayerEndTurn() {
        if (!triggered) {
            triggered = true;
            flash();
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                addToTop(new GainGoldAction(2));
            }
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        if (!triggered) {
            flash();
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                addToTop(new GainGoldAction(2));
            }
        }
    }
}
