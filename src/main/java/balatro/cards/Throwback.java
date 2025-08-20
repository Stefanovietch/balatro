package balatro.cards;

import balatro.character.baseDeck;
import balatro.util.CardStats;
import balatro.util.Data;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class Throwback extends BaseCard {
    public static final String ID = makeID(Throwback.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Throwback() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(0);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers();
        if (magicNumber > 0) {
            addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
            addToBot(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false), magicNumber));
        }
    }

    public void applyPowers() {
        super.applyPowers();
        magicNumber = Data.getEventRooms();
        if (magicNumber > 0) {
            isMagicNumberModified = true;
            if (upgraded) {
                magicNumber++;
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            } else {
                this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            }
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Throwback();
    }
}