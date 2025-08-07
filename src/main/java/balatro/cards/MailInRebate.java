package balatro.cards;

import balatro.actions.MailInRebateAction;
import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;

import java.util.Arrays;
import java.util.List;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.miscRng;

public class MailInRebate extends BaseCard implements RandomType{
    public static final String ID = makeID(MailInRebate.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private AbstractCard.CardType cardtype;
    private final Random typeRandom = new Random();
    List<CardType> typeList =
            Arrays.asList(AbstractCard.CardType.ATTACK, AbstractCard.CardType.SKILL, AbstractCard.CardType.POWER);

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public MailInRebate() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC,UPG_MAGIC);
        setRandomType();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new MailInRebateAction(p,10, cardtype));
        addToBot(new DrawCardAction(magicNumber));
    }

    @Override
    public void upgrade() {
        super.upgrade();
        setRandomType();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new MailInRebate();
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
