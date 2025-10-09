package balatro.ui.stakes;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class WhiteStake extends ClickableStake {
    public static String stakeName = "whiteStake";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(stakeName)).TEXT;

    public WhiteStake() {
        super(stakeName, TEXT[0], TEXT[1]);
    }
}
