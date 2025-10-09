package balatro.ui.stakes;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class BlueStake extends ClickableStake {
    public static String stakeName = "blueStake";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(stakeName)).TEXT;

    public BlueStake() {
        super(stakeName, TEXT[0], TEXT[1]);
    }
}
