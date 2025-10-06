package balatro.ui.stakes;

import balatro.balatroMod;
import balatro.util.Data;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class RedStake extends ClickableStake {
    public static String stakeName = "redStake";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(stakeName)).TEXT;

    public RedStake() {
        super(stakeName, TEXT[0], TEXT[1]);
        update();
    }

    @Override
    public void update() {
        super.update();
        if (Data.getStakeForDeck(balatroMod.selectedDeck) >= 1) {
            this.unlocked = true;
        } else {
            this.unlocked = false;
        }
    }
}
