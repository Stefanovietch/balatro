package balatro.cards;

import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.aiRng;

public class Seance extends BaseCard {
    public static final String ID = makeID(Seance.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public Seance() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean justSkills = true;
        for (AbstractCard c : p.hand.group) {
            if (c.type != CardType.SKILL) {
                justSkills = false;
                break;
            }
        }
        if(justSkills) {
            ArrayList<AbstractCard> list = new ArrayList<>();

            for (AbstractCard c : AbstractDungeon.srcColorlessCardPool.group) {
                if (c.type == CardType.SKILL && !c.hasTag(CardTags.HEALING)) {
                    list.add(c);
                }
            }

            for (int i = 0; i < magicNumber; i++) {
                AbstractCard c = list.get(aiRng.random(list.size() - 1));
                //c.setCostForTurn(-99);
                addToBot(new MakeTempCardInHandAction(c, true));
            }
        }
    }

    public void triggerOnGlowCheck() {
        boolean justSkills = true;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type != CardType.SKILL) {
                justSkills = false;
                break;
            }
        }
        if (justSkills) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Seance();
    }
}
