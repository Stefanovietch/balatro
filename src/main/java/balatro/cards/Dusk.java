package balatro.cards;

import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Dusk extends BaseCard {
    public static final String ID = makeID(Dusk.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            3 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Dusk() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setExhaust(true);
        setCostUpgrade(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hand.isEmpty() || (p.hand.size() == 1 && p.hand.getBottomCard() instanceof Dusk)) {
            ArrayList<AbstractCard> cards = AbstractDungeon.actionManager.cardsPlayedThisTurn;
            for (AbstractCard card : cards) {
                if (card instanceof Dusk) {
                    return;
                } else {
                    AbstractCard c = card.makeSameInstanceOf();
                    AbstractDungeon.player.limbo.group.add(c);
                    c.current_y = -200.0F * Settings.scale;
                    c.purgeOnUse = true;
                    c.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                    c.target_y = Settings.HEIGHT / 2.0F;
                    c.targetAngle = 0.0F;
                    c.lighten(false);
                    c.drawScale = 0.12F;
                    c.targetDrawScale = 0.75F;
                    c.applyPowers();
                    addToBot(new NewQueueCardAction(c, m, false, true));
                    addToTop(new UnlimboAction(c));
                }
            }
        }
    }

//    public void triggerOnGlowCheck() {
//        if (AbstractDungeon.player.hand.group.size() <= 1) {
//            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
//        } else {
//            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
//        }
//    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Dusk();
    }
}
