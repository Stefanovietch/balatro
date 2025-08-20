package balatro.cards;

import balatro.character.baseDeck;
import balatro.patches.LegendaryPatch;
import balatro.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Chicot extends BaseCard {
    public static final String ID = makeID(Chicot.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            LegendaryPatch.LEGENDARY, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Chicot() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setExhaust(true);
        setEthereal(true,false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (monster.type == AbstractMonster.EnemyType.BOSS && !monster.isDying && !monster.isDead) {
                addToBot(new StunMonsterAction(monster, p));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Chicot();
    }
}
