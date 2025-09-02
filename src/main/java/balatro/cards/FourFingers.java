package balatro.cards;

import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FourFingers extends BaseCard {
    public static final String ID = makeID(FourFingers.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int DAMAGE = 1;

    public FourFingers() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setCustomVar("damage1",VariableType.DAMAGE,1);
        setCustomVar("damage2",VariableType.DAMAGE,2);
        setCustomVar("damage3",VariableType.DAMAGE,3);
        setCustomVar("damage4",VariableType.DAMAGE,4);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            addToBot(new DamageAction(m, new DamageInfo(p, customVar("damage1"), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            addToBot(new DamageAction(m, new DamageInfo(p, customVar("damage2"), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            addToBot(new DamageAction(m, new DamageInfo(p, customVar("damage3"), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            addToBot(new DamageAction(m, new DamageInfo(p, customVar("damage4"), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        } else {
            addToBot(new DamageRandomEnemyAction(new DamageInfo(p, customVar("damage1"), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            addToBot(new DamageRandomEnemyAction(new DamageInfo(p, customVar("damage2"), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            addToBot(new DamageRandomEnemyAction(new DamageInfo(p, customVar("damage3"), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            addToBot(new DamageRandomEnemyAction(new DamageInfo(p, customVar("damage4"), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.target = CardTarget.ENEMY;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new FourFingers();
    }
}
