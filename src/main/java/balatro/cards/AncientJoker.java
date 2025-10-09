package balatro.cards;

import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;

import java.util.Arrays;
import java.util.List;

public class AncientJoker extends BaseCard implements RandomType{
    public static final String ID = makeID(AncientJoker.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private CardType cardtype;
    private final Random typeRandom = new Random();
    List<CardType> typeList =
            Arrays.asList(CardType.ATTACK, CardType.SKILL, CardType.POWER);

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;

    public AncientJoker() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(DAMAGE, UPG_DAMAGE);
        setRandomType();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //applyPowers();
        //calculateCardDamage(m);
        addToBot(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SMASH));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        float actualDamage = damage;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.type == cardtype)
                actualDamage = actualDamage * 1.5F;
        }
        damage = (int) actualDamage;
        isDamageModified = baseDamage != damage;
    }

    public void calculateCardDamage(AbstractMonster m) {
        float actualDamage = damage;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.type == cardtype)
                actualDamage = actualDamage * 1.5F;
        }
        damage = (int) actualDamage;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AncientJoker();
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
            initializeDescription();
        }
    }
}
