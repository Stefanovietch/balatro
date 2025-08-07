package balatro.cards;

import balatro.actions.SpareTrousersAction;
import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SpareTrousers extends BaseCard {
    public static final String ID = makeID(SpareTrousers.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int DAMAGE = 8;

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 2;

    public SpareTrousers() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.misc = DAMAGE;
        this.baseDamage = this.misc;
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SpareTrousersAction(new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), this));
    }

    public void applyPowers() {
        this.baseDamage = this.misc;
        super.applyPowers();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SpareTrousers();
    }
}
