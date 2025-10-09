package balatro.ui.stakes;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class GoldStake extends ClickableStake {
    public static String stakeName = "goldStake";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(stakeName)).TEXT;

    public GoldStake() {
        super(stakeName, TEXT[0], TEXT[1]);
    }
}
