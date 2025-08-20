package balatro.cards;

import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Obelisk extends BaseCard {
    public static final String ID = makeID(Obelisk.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );


    public Obelisk() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.misc = 5;
        this.baseBlock = this.misc * 2;
        this.baseDamage = this.misc;
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> choices = new ArrayList<>();
        choices.add(new Blockade(this.baseBlock));
        choices.add(new Assault(this.baseDamage));
        choices.add(new Enhance(this));
        if (this.upgraded)
            for (AbstractCard c : choices)
                c.upgrade();

        addToBot(new ChooseOneAction(choices));

        ArrayList<AbstractCard> choices2 = new ArrayList<>();
        choices2.add(new Blockade(this.baseBlock));
        choices2.add(new Assault(this.baseDamage));
        choices2.add(new Enhance(this));
        if (this.upgraded)
            for (AbstractCard c : choices2)
                c.upgrade();

        addToBot(new ChooseOneAction(choices2));
    }

    public void applyPowers() {
        this.baseBlock = this.misc * 2;
        this.baseDamage = this.misc;
        super.applyPowers();
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.misc += 2;
        this.baseBlock = this.misc * 2;
        this.baseDamage = this.misc;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Obelisk();
    }
}
