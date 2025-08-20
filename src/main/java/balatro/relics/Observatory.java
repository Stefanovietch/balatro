package balatro.relics;

import balatro.character.baseDeck;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;

import static balatro.balatroMod.makeID;

public class Observatory extends BaseRelic{
    private static final String NAME = "Observatory"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private boolean cardsSelected = true;

    public Observatory() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public void onEquip() {
        this.cardsSelected = false;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard card : (AbstractDungeon.player.masterDeck.getUpgradableCards()).group)
            tmp.addToTop(card);
        if (tmp.group.isEmpty()) {
            this.cardsSelected = true;
            return;
        }
        if (tmp.group.size() <= 4) {
            giveCards(tmp.group);
        } else if (!AbstractDungeon.isScreenUp) {
            AbstractDungeon.gridSelectScreen.open(tmp, 4, this.DESCRIPTIONS[1] + this.name + LocalizedStrings.PERIOD, false, false, false, false);
        } else {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
            AbstractDungeon.gridSelectScreen.open(tmp, 4, this.DESCRIPTIONS[1] + this.name + LocalizedStrings.PERIOD, false, false, false, false);
        }
    }

    public void update() {
        super.update();
        if (!this.cardsSelected &&
                AbstractDungeon.gridSelectScreen.selectedCards.size() == 4)
            giveCards(AbstractDungeon.gridSelectScreen.selectedCards);
    }

    public void giveCards(ArrayList<AbstractCard> group) {
        this.cardsSelected = true;
        float displayCount = 0.0F;
        for (AbstractCard card : group) {
            card.untip();
            card.unhover();
            card.upgrade();
            AbstractDungeon.player.bottledCardUpgradeCheck(card);
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy(), Settings.WIDTH / 4.0F + displayCount, Settings.HEIGHT / 2.0F));
            displayCount += Settings.WIDTH / 6.0F;
        }
        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
