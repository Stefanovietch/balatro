package balatro.cards;

import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;

import java.util.Arrays;
import java.util.List;

public class Castle extends BaseCard implements RandomType{
    public static final String ID = makeID(Castle.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private CardType cardtype;
    private final Random typeRandom = new Random();
    List<CardType> typeList =
            Arrays.asList(CardType.ATTACK, CardType.SKILL, CardType.POWER);

    private static final int BLOCK = 5;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 2;


    public Castle() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setBlock(BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSelfRetain(true);
        setRandomType();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
       addToBot(new GainBlockAction(p, block));
    }

    @Override
    public void upgrade() {
        super.upgrade();
        setRandomType();
    }

    public void triggerOnOtherCardDiscarded(AbstractCard c) {
        if (c.type == cardtype) {
            this.baseBlock += magicNumber;
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Castle();
    }

    @Override
    public void setRandomType() {
        if (CardCrawlGame.isInARun()) {
            cardtype = typeList.get(typeRandom.random(2));
            String typeString;
            if (cardtype == CardType.ATTACK) {
                typeString = "an Attack";
            } else if (cardtype == CardType.SKILL) {
                typeString = "a Skill";
            } else {
                typeString = "a Power";
            }
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + typeString + cardStrings.EXTENDED_DESCRIPTION[1];
        } else {
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        initializeDescription();
    }
}
