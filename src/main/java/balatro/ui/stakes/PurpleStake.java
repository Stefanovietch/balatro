package balatro.ui.stakes;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class PurpleStake extends ClickableStake {
    public static String stakeName = "purpleStake";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(stakeName)).TEXT;

    public PurpleStake() {
        super(stakeName, TEXT[0], TEXT[1]);
    }
}
