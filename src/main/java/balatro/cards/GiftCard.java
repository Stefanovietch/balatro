package balatro.cards;

import balatro.balatroMod;
import balatro.character.baseDeck;
import balatro.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class GiftCard extends BaseCard {
    public static final String ID = makeID(GiftCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            baseDeck.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public GiftCard() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC, UPG_MAGIC);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new ArtifactPower(p, magicNumber),magicNumber));
        AbstractDungeon.getCurrRoom().uncommonCardChance += 10;
        AbstractDungeon.getCurrRoom().rareCardChance += 10;
        for (RewardItem ri : AbstractDungeon.getCurrRoom().rewards) {
            if (ri.type == RewardItem.RewardType.GOLD){ri.incrementGold(500);}
            /*
            if (ri.type == RewardItem.RewardType.CARD){
                boolean isColorless = true;
                for (AbstractCard c : ri.cards) {
                    balatroMod.logger.info(c);
                    if (c.color != CardColor.COLORLESS) {
                        isColorless = false;
                        //break;
                    }
                }
                if (isColorless) {
                    //ri.cards = AbstractDungeon.getColorlessRewardCards();
                } else {
                    //ri.cards = AbstractDungeon.getRewardCards();
                }
            }
        */
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new GiftCard();
    }
}
