package balatro.relics;

import balatro.cards.TheIdol;
import balatro.character.baseDeck;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.ColorlessPotion;
import com.megacrit.cardcrawl.potions.DuplicationPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.rewards.RewardItem;

import java.util.ArrayList;
import java.util.Objects;

import static balatro.balatroMod.makeID;

public class Astronomy extends BaseRelic{
    private static final String NAME = makeID(Astronomy.class.getSimpleName()); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public Astronomy() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onEquip() {
        AbstractDungeon.player.potionSlots -= 1;
        ArrayList<AbstractPotion> oldPotions = AbstractDungeon.player.potions;
        AbstractDungeon.player.potions.clear();
        for (int i = 0; i < AbstractDungeon.player.potionSlots; i++) {
            AbstractDungeon.player.potions.add(new PotionSlot(i));
        }
    }

}
