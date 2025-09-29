package balatro.cards;

import balatro.character.baseDeck;
import balatro.patches.LegendaryPatch;
import balatro.util.CardStats;
import balatro.util.Data;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Yorick extends BaseCard {
    public static final String ID = makeID(Yorick.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            LegendaryPatch.LEGENDARY, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = -1;
    private static final int DAMAGE = 11;

    public Yorick() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC, UPG_MAGIC);
        setDamage(DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //calculateCardDamage(m);
        //applyPowers();
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.damage *= (1 + Math.floorDiv(Data.getCardsDiscarded(), magicNumber));
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        this.damage *= (1 + Math.floorDiv(Data.getCardsDiscarded(), magicNumber));
        isDamageModified = baseDamage != damage;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Yorick();
    }
}
