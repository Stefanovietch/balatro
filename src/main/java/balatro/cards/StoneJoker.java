package balatro.cards;

import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Safety;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Objects;

public class StoneJoker extends BaseCard {
    public static final String ID = makeID(StoneJoker.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 1;

    private int safetyCount = 0;

    public StoneJoker() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers();
        for (int i = 0; i < safetyCount; i++) {
            addToBot(new GainBlockAction(p, p, block));
        }
    }

    public void applyPowers() {
        super.applyPowers();
        safetyCount = 0;
        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.cardID.equals(Safety.ID)) {safetyCount++;}
        }
        for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.cardID.equals(Safety.ID)) {safetyCount++;}
        }
        for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.cardID.equals(Safety.ID)) {safetyCount++;}
        }
        for(AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c.cardID.equals(Safety.ID)) {safetyCount++;}
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new StoneJoker();
    }
}