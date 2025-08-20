package balatro.cards;

import balatro.actions.IncreaseBlockAction;
import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SquareJoker extends BaseCard {
    public static final String ID = makeID(SquareJoker.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int BLOCK = 4;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public SquareJoker() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC,UPG_MAGIC);
        this.misc = BLOCK;
        this.baseBlock = this.misc;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() == 5) {
            addToBot(new IncreaseBlockAction(this, magicNumber));
        }
        applyPowers();
        addToBot(new GainBlockAction(p, p, block));
    }

    public void triggerOnGlowCheck() {
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() == 4) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SquareJoker();
    }

}
