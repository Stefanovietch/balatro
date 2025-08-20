package balatro.cards;

import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReservedParking extends BaseCard {
    public static final String ID = makeID(ReservedParking.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public ReservedParking() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC, UPG_MAGIC);
        setChance(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawPileToHandAction(this.magicNumber, CardType.POWER));
        int totalGold = 0;
        for (AbstractCard c : p.hand.group) {
            if (c.type == CardType.POWER && getChance()) {totalGold += 20;}
        }
        if (totalGold != 0) {
            addToBot(new GainGoldAction(totalGold));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ReservedParking();
    }
}