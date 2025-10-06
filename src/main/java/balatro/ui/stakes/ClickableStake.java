package balatro.ui.stakes;

import balatro.balatroMod;
import balatro.ui.DeckPanel;
import balatro.ui.StakePanel;
import balatro.util.TextureLoader;
import basemod.ClickableUIElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;

import java.io.IOException;

import static balatro.balatroMod.imagePath;

public abstract class ClickableStake extends ClickableUIElement {
    public String stakeName;
    public boolean selected;
    public boolean unlocked = false;
    private final TextureAtlas.AtlasRegion selectedTexture = ImageMaster.TINY_STAR; //Glow spark
    private final float texScale = SIZE / selectedTexture.packedWidth;
    private final String header;
    private final String body;
    public static float SIZE = 80f;
    private StakePanel containingPanel;

    public ClickableStake(String stakeName, String header, String body) {
        super(TextureLoader.getTexture(imagePath("stakes/" + stakeName + ".png")));
        this.header = header;
        this.body = body;
        this.stakeName = stakeName;
    }

    public void move(float x, float y) {
        setX(x);
        setY(y);
    }

    public void setPanel(StakePanel panel) {
        this.containingPanel = panel;
    }

    @Override
    public void update() {
        super.update();
        if (selected && !unlocked) {
            int newIndex = containingPanel.stakes.indexOf(this)-1;
            if (newIndex >= 0) {
                containingPanel.selectStake(containingPanel.stakes.get(newIndex));
            } else {
                balatroMod.logger.info("negative index or is white stake locked???");
            }
        }
    }

    @Override
    public void render(SpriteBatch sb, Color color) {
        if (!unlocked) {
            color = new Color(0.2F,0.2F,0.2F,1F);
        }
        super.render(sb, color);
        if (selected) {
            float halfWidth = selectedTexture.packedWidth / 2.0F;
            float halfHeight = selectedTexture.packedHeight / 2.0F;
            sb.draw(selectedTexture, x - halfWidth + halfWidth * Settings.scale * texScale, y - halfHeight + halfHeight * Settings.scale * texScale, halfWidth, halfHeight, selectedTexture.packedWidth, selectedTexture.packedHeight, Settings.scale * texScale * 0.5f, Settings.scale * texScale * 0.5f, 0);
        }

    }

    @Override
    protected void onHover() {
        TipHelper.renderGenericTip(this.x + 100F * Settings.scale, this.y + 100F * Settings.scale, header, body);
    }

    @Override
    protected void onUnhover() {

    }

    @Override
    protected void onClick() {
        if (!selected && unlocked) {
            containingPanel.selectStake(this);
        }
    }

    public void onSelect() {
        try {
            balatroMod.balatroConfig.setInt(balatroMod.SELECTED_STAKE_INDEX, containingPanel.stakes.indexOf(this));
            balatroMod.balatroConfig.setString(balatroMod.SELECTED_STAKE, this.stakeName);
            balatroMod.balatroConfig.save();

            balatroMod.selectedStake = this.stakeName;
            balatroMod.stakeUI.selectedStake = this.stakeName;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
