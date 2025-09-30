package balatro.relics;

import balatro.character.baseDeck;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static balatro.balatroMod.makeID;

public class NestEgg extends BaseRelic{
    private static final String NAME = "NestEgg"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private boolean giveMoney = false;
    public NestEgg() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onEquip() {
        giveMoney = true;
    }


    public void update() {
        super.update();
        if (giveMoney) {
            try {
                CardCrawlGame.sound.play("GOLD_GAIN");
                AbstractDungeon.player.gainGold(300);
                giveMoney = false;
            } catch (Exception e) {

            }
        }
    }
}
