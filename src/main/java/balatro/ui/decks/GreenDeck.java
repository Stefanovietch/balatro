package balatro.ui.decks;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class GreenDeck extends ClickableDeck{
    public static String deckName = "greenDeck";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(deckName)).TEXT;

    public GreenDeck() {
        super(deckName, TEXT[0], TEXT[1]);
    }
}
