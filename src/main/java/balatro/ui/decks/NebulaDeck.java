package balatro.ui.decks;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class NebulaDeck extends ClickableDeck{
    public static String deckName = "nebulaDeck";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(deckName)).TEXT;

    public NebulaDeck() {
        super(deckName, TEXT[0], TEXT[1]);
    }
}
