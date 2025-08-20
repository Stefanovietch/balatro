package balatro.cards;

import balatro.actions.EnhanceAction;
import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Enhance extends BaseCard {
    public static final String ID = makeID(Enhance.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.SPECIAL, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    AbstractCard toEnhance;

    public Enhance() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDisplayRarity(CardRarity.RARE);
    }

    public Enhance(AbstractCard toEnhance) {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.toEnhance = toEnhance;
        setDisplayRarity(CardRarity.RARE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }

    public void onChoseThisOption() {
        addToBot(new EnhanceAction(this.toEnhance));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Enhance(this.toEnhance);
    }
}
