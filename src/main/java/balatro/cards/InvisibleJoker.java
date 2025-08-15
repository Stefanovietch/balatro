package balatro.cards;

import balatro.actions.DNAAction;
import balatro.actions.InvisibleJokerAction;
import balatro.balatroMod;
import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRng;

public class InvisibleJoker extends BaseCard {
    public static final String ID = makeID(InvisibleJoker.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            3 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public InvisibleJoker() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setExhaust(true);
        setCostUpgrade(2);
        this.misc = 0;
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new InvisibleJokerAction(this));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new InvisibleJoker();
    }
}