package balatro.ui.decks;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class BlueDeck extends ClickableDeck{
    public static String deckName = "blueDeck";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(deckName)).TEXT;

    public BlueDeck() {
        super(deckName, TEXT[0], TEXT[1]);
    }
}
