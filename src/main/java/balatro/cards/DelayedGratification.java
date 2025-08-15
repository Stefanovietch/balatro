package balatro.cards;

import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DelayedGratification extends BaseCard {
    public static final String ID = makeID(DelayedGratification.class.getSimpleName());

    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int MONEY = 15;
    private static final int UPG_MONEY = 10;
    private boolean discardedCard;

    public DelayedGratification() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MONEY,UPG_MONEY);
        discardedCard = GameActionManager.totalDiscardedThisTurn > 0;

    }

    public void didDiscard() {
        discardedCard = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public void triggerOnGlowCheck() {
        if (!discardedCard) {
            this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = null;
        }
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        discardedCard = false;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        if(!discardedCard) {
            addToBot(new GainGoldAction(magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DelayedGratification();
    }


}
