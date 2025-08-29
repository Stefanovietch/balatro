package balatro.cards;

import balatro.actions.TheIdolAction;
import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;

public class TheIdol extends BaseCard implements RandomType{
    public static final String ID = makeID(TheIdol.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private int cardCost;
    private CardType cardtype;
    private final Random typeRandom = new Random();

    public TheIdol() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setSelfRetain(false,true);
        setRandomType();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TheIdolAction(cardtype, cardCost));
    }

    @Override
    public void upgrade() {
        super.upgrade();
        setRandomType();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new TheIdol();
    }

    @Override
    public void setRandomType() {
        if (upgraded) {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        } else {
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        if (CardCrawlGame.isInARun()) {
            if (!AbstractDungeon.player.drawPile.isEmpty()) {
                ArrayList<CardType> availableCardTypes = new ArrayList<>();
                ArrayList<Integer> availableCardCosts = new ArrayList<>();
                for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                    if ((card.costForTurn == 1 || card.costForTurn == 2 || card.costForTurn == 3) && (card.type == CardType.ATTACK || card.type == CardType.SKILL || card.type == CardType.POWER)) {
                        availableCardTypes.add(card.type);
                        availableCardCosts.add(card.costForTurn);
                    }
                }
                if (!availableCardTypes.isEmpty() && !availableCardCosts.isEmpty()) {
                    cardCost = availableCardCosts.get(typeRandom.random(availableCardCosts.size() - 1));
                    cardtype = availableCardTypes.get(typeRandom.random(availableCardTypes.size() - 1));

                    String typeString;
                    if (cardtype == CardType.ATTACK) {
                        typeString = " Attack ";
                    } else if (cardtype == CardType.SKILL) {
                        typeString = " Skill ";
                    } else {
                        typeString = " Power ";
                    }

                    if (upgraded) {
                        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[2] + cardStrings.EXTENDED_DESCRIPTION[0] + cardCost + typeString + cardStrings.EXTENDED_DESCRIPTION[1];
                    } else {
                        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + cardCost + typeString + cardStrings.EXTENDED_DESCRIPTION[1];
                    }
                }
            }
        }
        initializeDescription();
    }
}
