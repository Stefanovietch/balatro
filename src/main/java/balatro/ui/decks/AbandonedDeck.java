package balatro.ui.decks;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class AbandonedDeck extends ClickableDeck{
    public static String deckName = "abandonedDeck";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(deckName)).TEXT;

    public AbandonedDeck() {
        super(deckName, TEXT[0], TEXT[1]);
    }
}
