package balatro.cards;

import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class TheOrder extends BaseCard {
    public static final String ID = makeID(TheOrder.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int DAMAGE = 13;
    private static final int UPG_DAMAGE = 5;

    public TheOrder() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(DAMAGE,UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        calculateCardDamage(m);
        applyPowers();
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
    }

    public void applyPowers() {
        super.applyPowers();
        if (straightInHand()) {
            this.damage *= 3;
            isDamageModified = true;
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        if (straightInHand()) {
            this.damage *= 3;
            isDamageModified = true;
        }
    }

    public void triggerOnGlowCheck() {
        if (straightInHand()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    private boolean straightInHand() {
        boolean has0 = false, has1 = false, has2 = false, has3 = false;
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card.equals(this)) {continue;}
            if (card.costForTurn == 0 || (card.costForTurn == -1 && EnergyPanel.getCurrentEnergy() == 0)) {has0 = true;}
            if (card.costForTurn == 1 || (card.costForTurn == -1 && EnergyPanel.getCurrentEnergy() == 1)) {has1 = true;}
            if (card.costForTurn == 2 || (card.costForTurn == -1 && EnergyPanel.getCurrentEnergy() == 2)) {has2 = true;}
            if (card.costForTurn == 3 || (card.costForTurn == -1 && EnergyPanel.getCurrentEnergy() == 2)) {has3 = true;}
        }
        return has0 && has1 && has2 && has3;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new TheOrder();
    }
}
