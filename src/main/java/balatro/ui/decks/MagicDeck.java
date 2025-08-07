package balatro.ui.decks;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class MagicDeck extends ClickableDeck{
    public static String deckName = "magicDeck";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(deckName)).TEXT;

    public MagicDeck() {
        super(deckName, TEXT[0], TEXT[1]);
    }
}
