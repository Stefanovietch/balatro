package balatro.ui.stakes;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class GreenStake extends ClickableStake {
    public static String stakeName = "greenStake";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(stakeName)).TEXT;

    public GreenStake() {
        super(stakeName, TEXT[0], TEXT[1]);
    }
}
