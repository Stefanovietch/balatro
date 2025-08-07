package balatro.ui.decks;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class ZodiacDeck extends ClickableDeck{
    public static String deckName = "zodiacDeck";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(deckName)).TEXT;

    public ZodiacDeck() {
        super(deckName, TEXT[0], TEXT[1]);
    }
}
