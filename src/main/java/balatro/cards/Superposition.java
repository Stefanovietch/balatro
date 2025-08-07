package balatro.cards;

import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.Objects;

public class Superposition extends BaseCard {
    public static final String ID = makeID(Superposition.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Superposition() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setExhaust(true);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> cards = p.hand.group;
        if (straightInHand(cards)) {
            addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
        }
    }

    public void triggerOnGlowCheck() {
        ArrayList<AbstractCard> cards = AbstractDungeon.player.hand.group;
        if (straightInHand(cards)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Superposition();
    }

    private boolean straightInHand(ArrayList<AbstractCard> cards) {
        boolean has0 = false, has1 = false, has2 = false, has3 = false;
        for (AbstractCard card : cards) {
            if (Objects.equals(card, this)) {continue;}
            if (card.costForTurn == 0 || (card.costForTurn == -1 && EnergyPanel.getCurrentEnergy() == 0)) {has0 = true;}
            if (card.costForTurn == 1 || (card.costForTurn == -1 && EnergyPanel.getCurrentEnergy() == 1)) {has1 = true;}
            if (card.costForTurn == 2 || (card.costForTurn == -1 && EnergyPanel.getCurrentEnergy() == 2)) {has2 = true;}
            if (card.costForTurn == 3 || (card.costForTurn == -1 && EnergyPanel.getCurrentEnergy() == 2)) {has3 = true;}
        }
        return has0 && has1 && has2 && has3;
    }
}
