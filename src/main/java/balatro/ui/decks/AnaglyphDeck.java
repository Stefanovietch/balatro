package balatro.ui.decks;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class AnaglyphDeck extends ClickableDeck{
    public static String deckName = "anaglyphDeck";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(deckName)).TEXT;

    public AnaglyphDeck() {
        super(deckName, TEXT[0], TEXT[1]);
    }
}
