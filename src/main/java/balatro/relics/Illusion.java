package balatro.relics;

import balatro.character.baseDeck;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;

import java.util.ArrayList;

import static balatro.balatroMod.makeID;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRng;

public class Illusion extends BaseRelic{
    private static final String NAME = "Illusion"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SHOP; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public Illusion() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public void onEquip() {
        AbstractDungeon.getCurrRoom().addCardReward(getCardRewardWithRarity(AbstractCard.CardRarity.COMMON));
        AbstractDungeon.getCurrRoom().addCardReward(getCardRewardWithRarity(AbstractCard.CardRarity.UNCOMMON));
        AbstractDungeon.getCurrRoom().addCardReward(getCardRewardWithRarity(AbstractCard.CardRarity.RARE));

        AbstractDungeon.combatRewardScreen.open(this.DESCRIPTIONS[1]);
        (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.0F;

    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public RewardItem getCardRewardWithRarity(AbstractCard.CardRarity rarity) {
        RewardItem reward = new RewardItem();
        ArrayList<AbstractCard> cards = new ArrayList<>();
        while (cards.size() != 3) {
            boolean dupe = false;
            AbstractCard tmp = AbstractDungeon.getCard(rarity, cardRng);
            for (AbstractCard c : cards) {
                if (c.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }
            }
            if (!dupe)
                cards.add(tmp.makeCopy());
        }
        reward.cards = cards;
        return reward;
    }
}
