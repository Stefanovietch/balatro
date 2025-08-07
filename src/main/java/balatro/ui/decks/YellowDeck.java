package balatro.ui.decks;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class YellowDeck extends ClickableDeck{
    public static String deckName = "yellowDeck";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(deckName)).TEXT;

    public YellowDeck() {
        super(deckName, TEXT[0], TEXT[1]);
    }
}
