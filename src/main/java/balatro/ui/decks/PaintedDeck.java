package balatro.ui.decks;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class PaintedDeck extends ClickableDeck{
    public static String deckName = "paintedDeck";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(deckName)).TEXT;

    public PaintedDeck() {
        super(deckName, TEXT[0], TEXT[1]);
    }
}
