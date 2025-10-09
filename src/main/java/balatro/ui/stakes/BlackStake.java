package balatro.ui.stakes;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class BlackStake extends ClickableStake {
    public static String stakeName = "blackStake";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(stakeName)).TEXT;

    public BlackStake() {
        super(stakeName, TEXT[0], TEXT[1]);
    }
}
