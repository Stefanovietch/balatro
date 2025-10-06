package balatro.ui;

import balatro.balatroMod;
import balatro.ui.decks.ClickableDeck;
import balatro.ui.stakes.ClickableStake;
import balatro.util.Data;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class StakePanel {
    public ArrayList<ClickableStake> stakes = new ArrayList<>();
    public float x;
    public float y;
    public static final float PAD_X = (ClickableStake.SIZE + 10f) * Settings.scale;
    public static final float PAD_Y = (ClickableStake.SIZE + 10f) * Settings.scale;

    public StakePanel(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void addStake(ClickableStake stake) {
        stakes.add(stake);
    }

    public void selectStake(ClickableStake selected) {
        for (ClickableStake stake : stakes) {
            if (stake != selected) {
                stake.selected = false;
            } else {
                stake.selected = true;
                stake.onSelect();
            }
        }
    }

    public void layoutDecks() {
        int index = 0;
        for (ClickableStake stake : stakes) {
            stake.setPanel(this);
            stake.move(x + index * PAD_X, y - PAD_Y);
            index++;
        }
    }

    public void update(){
        for (ClickableStake stake : stakes) {
            stake.update();
        }
    }

    public void render(SpriteBatch sb){
        for (ClickableStake stake : stakes) {
            stake.render(sb);
        }
    }
}
