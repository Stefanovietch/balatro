package balatro.ui.stakes;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class OrangeStake extends ClickableStake {
    public static String stakeName = "orangeStake";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(stakeName)).TEXT;

    public OrangeStake() {
        super(stakeName, TEXT[0], TEXT[1]);
    }
}
