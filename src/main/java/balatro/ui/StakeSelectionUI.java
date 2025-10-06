package balatro.ui;

import balatro.balatroMod;
import balatro.ui.stakes.RedStake;
import balatro.ui.stakes.WhiteStake;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

public class StakeSelectionUI {
    private final StakePanel stakePanel;
    public String selectedStake;


    public StakeSelectionUI() {
        stakePanel = new StakePanel(200.0F * Settings.scale, Settings.HEIGHT/2f - 100.0F * Settings.scale);

        stakePanel.addStake(new WhiteStake());
        stakePanel.addStake(new RedStake());

        stakePanel.layoutDecks();

        stakePanel.stakes.get(balatroMod.selectedStakeIndex).selected = true;
        selectedStake= balatroMod.selectedDeck;
    }

    public void update() {
        stakePanel.update();
    }

    public void render(SpriteBatch sb, float xInfo) {
        stakePanel.render(sb);
    }
}
