package balatro.cards;

import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;

import static java.lang.Integer.MAX_VALUE;

public class RaisedFist extends BaseCard {
    public static final String ID = makeID(RaisedFist.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );


    public RaisedFist() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setSelfRetain(false,true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int lowestCost = MAX_VALUE;
        ArrayList<AbstractCard> cards = p.hand.group;
        cards.remove(this);
        if (!cards.isEmpty()) {
            for (AbstractCard card : cards) {
                int cardCost = card.costForTurn;
                if (cardCost == -1) {cardCost = EnergyPanel.getCurrentEnergy();}
                if (cardCost == -2) {continue;}
                if (cardCost < lowestCost) {lowestCost = cardCost;}
            }
        } else {
            lowestCost = 0;
        }

        if (lowestCost > 0) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 2 * lowestCost), 2 * lowestCost));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 2 * lowestCost), 2 * lowestCost));
        }

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RaisedFist();
    }
}