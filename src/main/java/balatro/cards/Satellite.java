package balatro.cards;

import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;

public class Satellite extends BaseCard {
    public static final String ID = makeID(Satellite.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;

    public Satellite() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setExhaust(true);
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amountUpgraded = 0;
        ArrayList<AbstractCard> possibleCards = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.canUpgrade())
                possibleCards.add(c);
            if (c.upgraded) {
                amountUpgraded++;
            }
        }
        if (!possibleCards.isEmpty()) {
            AbstractCard theCard = possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));
            theCard.upgrade();
            amountUpgraded++;
            AbstractDungeon.player.bottledCardUpgradeCheck(theCard);

            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(theCard.makeStatEquivalentCopy()));
        }
        addToBot(new GainGoldAction(amountUpgraded * magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Satellite();
    }
}
