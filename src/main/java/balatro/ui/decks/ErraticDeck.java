package balatro.ui.decks;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class ErraticDeck extends ClickableDeck{
    public static String deckName = "erraticDeck";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(deckName)).TEXT;

    public ErraticDeck() {
        super(deckName, TEXT[0], TEXT[1]);
    }
}
