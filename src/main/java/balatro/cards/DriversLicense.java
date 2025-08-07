package balatro.cards;

import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class DriversLicense extends BaseCard {
    public static final String ID = makeID(DriversLicense.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public DriversLicense() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (hasRareCards()) {
            addToBot(new ApplyPowerAction(p,p,new DexterityPower(p, this.magicNumber),this.magicNumber));
            addToBot(new ApplyPowerAction(p,p,new StrengthPower(p, this.magicNumber),this.magicNumber));
        }
    }

    public void triggerOnGlowCheck() {
        if (hasRareCards()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    public boolean hasRareCards() {
        int amountRareCards = 0;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.rarity == CardRarity.RARE) {amountRareCards++;}
        }
        return amountRareCards >= 4;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DriversLicense();
    }
}
