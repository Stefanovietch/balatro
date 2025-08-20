package balatro.cards;

import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Fibonacci extends BaseCard {
    public static final String ID = makeID(Fibonacci.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Fibonacci() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setExhaust(true,false);
        setCustomVar("fibonacci_damage",VariableType.DAMAGE,0,0);
        colorCustomVar("fibonacci_damage",Settings.CREAM_COLOR, Settings.CREAM_COLOR, Settings.CREAM_COLOR);
        setVarCalculation("fibonacci_damage", (m, base) -> {
            int turn = GameActionManager.turn;
            int amount1 = 1, amount2 = 0, temp;
            for (int i = 1; i < turn; i++) {
                temp = amount1;
                amount1 += amount2;
                amount2 = temp;
            }
            return amount1;
        });

        setCustomVar("turn",VariableType.MAGIC,1,0);
        colorCustomVar("turn",Settings.CREAM_COLOR, Settings.CREAM_COLOR, Settings.CREAM_COLOR);
        setVarCalculation("turn", (m, base) -> GameActionManager.turn);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, customVar("fibonacci_damage"), DamageInfo.DamageType.NORMAL)));

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Fibonacci();
    }
}
