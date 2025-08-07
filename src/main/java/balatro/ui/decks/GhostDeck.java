package balatro.ui.decks;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class GhostDeck extends ClickableDeck{
    public static String deckName = "ghostDeck";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(deckName)).TEXT;

    public GhostDeck() {
        super(deckName, TEXT[0], TEXT[1]);
    }
}
