package balatro.ui;

import balatro.balatroMod;
import balatro.ui.decks.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

public class DeckSelectionUI {
    private final DeckPanel deckPanel;
    public String selectedDeck;


    public DeckSelectionUI() {
        deckPanel = new DeckPanel(200.0F * Settings.scale, Settings.HEIGHT/2f);

        deckPanel.addDeck(new RedDeck());
        deckPanel.addDeck(new BlueDeck());
        deckPanel.addDeck(new YellowDeck());
        deckPanel.addDeck(new GreenDeck());
        deckPanel.addDeck(new BlackDeck());
        deckPanel.addDeck(new MagicDeck());
        deckPanel.addDeck(new NebulaDeck());
        deckPanel.addDeck(new GhostDeck());
        deckPanel.addDeck(new AbandonedDeck());
        deckPanel.addDeck(new CheckeredDeck());
        deckPanel.addDeck(new ZodiacDeck());
        deckPanel.addDeck(new PaintedDeck());
        deckPanel.addDeck(new AnaglyphDeck());
        deckPanel.addDeck(new PlasmaDeck());
        deckPanel.addDeck(new ErraticDeck());

        deckPanel.layoutDecks();

        deckPanel.decks.get(balatroMod.selectedDeckIndex).selected = true;
        selectedDeck = balatroMod.selectedDeck;
    }

    public void update() {
        deckPanel.update();
    }

    public void render(SpriteBatch sb, float xInfo) {
        deckPanel.render(sb);
    }
}
