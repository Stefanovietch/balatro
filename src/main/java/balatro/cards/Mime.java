package balatro.cards;

import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Mime extends BaseCard {
    public static final String ID = makeID(Mime.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            3 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Mime() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setCostUpgrade(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardGroup cardsToPlay = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.costForTurn != -2) {
                cardsToPlay.addToRandomSpot(c);
            }
        }

        for (AbstractCard card : cardsToPlay.group) {
            if (AbstractDungeon.player.hand.group.contains(card)) {
                m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                (AbstractDungeon.getCurrRoom()).souls.remove(card);
                card.exhaustOnUseOnce = true;
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = -200.0F * Settings.scale;
                card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                card.target_y = Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                card.applyPowers();
                addToBot(new NewQueueCardAction(card, m, false, true));
                addToTop(new UnlimboAction(card));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Mime();
    }
}
