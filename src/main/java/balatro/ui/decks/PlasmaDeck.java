package balatro.ui.decks;

import balatro.balatroMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class PlasmaDeck extends ClickableDeck{
    public static String deckName = "plasmaDeck";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(balatroMod.makeID(deckName)).TEXT;

    public PlasmaDeck() {
        super(deckName, TEXT[0], TEXT[1]);
    }
}
