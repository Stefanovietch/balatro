package balatro.ui.decks;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class RedDeck extends ClickableDeck{
    public static String deckName = "redDeck";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(deckName)).TEXT;

    public RedDeck() {
        super(deckName, TEXT[0], TEXT[1]);
    }
}
