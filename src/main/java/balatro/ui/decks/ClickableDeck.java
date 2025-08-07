package balatro.ui.decks;

import balatro.character.baseDeck;
import balatro.ui.DeckPanel;
import balatro.util.TextureLoader;
import basemod.ClickableUIElement;
import balatro.balatroMod;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;

import java.io.IOException;

import static balatro.balatroMod.imagePath;

public abstract class ClickableDeck extends ClickableUIElement {
    public String deckName;
    public boolean selected;
    private final TextureAtlas.AtlasRegion selectedTexture = ImageMaster.TINY_STAR; //Glow spark
    private final float texScale = SIZE / selectedTexture.packedWidth;
    private final String header;
    private final String body;
    public static float SIZE = 80f;
    private DeckPanel containingPanel;

    public ClickableDeck(String deckName, String header, String body) {
        super(TextureLoader.getTexture(imagePath("decks/small/" + deckName + ".png")));
        this.header = header;
        this.body = body;
        this.deckName = deckName;
    }

    public void move(float x, float y) {
        setX(x);
        setY(y);
    }

    public void setPanel(DeckPanel panel) {
        this.containingPanel = panel;
    }

    @Override
    public void render(SpriteBatch sb, Color color) {
        super.render(sb, color);
        //sb.setColor(Color.WHITE);
        if (selected) {
            float halfWidth = selectedTexture.packedWidth / 2.0F;
            float halfHeight = selectedTexture.packedHeight / 2.0F;
            sb.draw(selectedTexture, x - halfWidth + halfWidth * Settings.scale * texScale, y - halfHeight + halfHeight * Settings.scale * texScale, halfWidth, halfHeight, selectedTexture.packedWidth, selectedTexture.packedHeight, Settings.scale * texScale * 0.5f, Settings.scale * texScale * 0.5f, 0);
        }
        /*if (hitbox.hovered && card != null) {
            float scale = FontHelper.cardTitleFont.getData().scaleX;
            card.render(sb);
            FontHelper.cardTitleFont.getData().setScale(scale);
        }*/
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
        if (!selected) {
            containingPanel.selectDeck(this);
        }
    }

    public void onSelect() {
        try {
            balatroMod.balatroConfig.setInt(balatroMod.SELECTED_DECK_INDEX, containingPanel.decks.indexOf(this));
            balatroMod.balatroConfig.setString(balatroMod.SELECTED_DECK, this.deckName);
            balatroMod.balatroConfig.save();

            balatroMod.selectedDeck = this.deckName;
            balatroMod.deckUI.selectedDeck = this.deckName;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
