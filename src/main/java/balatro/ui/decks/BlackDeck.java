package balatro.ui.decks;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class BlackDeck extends ClickableDeck{
    public static String deckName = "blackDeck";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(deckName)).TEXT;

    public BlackDeck() {
        super(deckName, TEXT[0], TEXT[1]);
    }
}
