package balatro.ui;

import balatro.character.baseDeck;
import balatro.ui.decks.ClickableDeck;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class DeckPanel {
    public ArrayList<ClickableDeck> decks = new ArrayList<>();
    public float x;
    public float y;
    public static final float PAD_X = (ClickableDeck.SIZE + 10f) * Settings.scale;
    public static final float PAD_Y = (ClickableDeck.SIZE + 10f) * Settings.scale;
    public static final float CORR_X = ClickableDeck.SIZE/2f * Settings.scale;
    public static final float CORR_Y = ClickableDeck.SIZE/2f * Settings.scale;
    public static final int DECKS_PER_ROW = 8;

    public DeckPanel(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void addDeck(ClickableDeck deck) {
        decks.add(deck);
    }

    public void selectDeck(ClickableDeck selected) {
        for (ClickableDeck deck : decks) {
            if (deck != selected) {
                deck.selected = false;
            } else {
                deck.selected = true;
                deck.onSelect();
            }
        }
    }

    public void layoutDecks() {
        int index = 0;
        int row = 0;
        for (ClickableDeck deck : decks) {
            deck.setPanel(this);
            deck.move(x + index * PAD_X, y - row * PAD_Y);
            index++;
            if (index == DECKS_PER_ROW) {
                index = 0;
                row++;
            }
        }
    }

    public void update(){
        for (ClickableDeck deck : decks) {
            deck.update();
        }
    }

    public void render(SpriteBatch sb){
        for (ClickableDeck deck : decks) {
            deck.render(sb);
        }
    }
}
