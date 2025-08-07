package balatro.ui.decks;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class CheckeredDeck extends ClickableDeck{
    public static String deckName = "checkeredDeck";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(deckName)).TEXT;

    public CheckeredDeck() {
        super(deckName, TEXT[0], TEXT[1]);
    }
}
