package balatro.util;

import balatro.balatroMod;
import balatro.cards.BaseCard;
import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import static com.megacrit.cardcrawl.helpers.FontHelper.SCP_cardEnergyFont;
import static com.megacrit.cardcrawl.helpers.FontHelper.prepFont;

public class PerishableVisualModifier extends AbstractCardModifier {
    public static final String ID = balatroMod.makeID("PerishableVisualModifier");
    private static final Texture PerishableBg = TextureLoader.getTexture(balatroMod.imagePath("PerishableBackground.png"));;
    private static final Color textColor = new Color(0.7F, 0.7F, 0.7F, 1.0F);

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        super.onRender(card, sb);
        FontHelper.cardEnergyFont_L.getData().setScale(card.drawScale*0.5F);
        if(((BaseCard) card).isPerishable()) {
            ExtraIcons.icon(PerishableBg)
                    .text(String.valueOf(((BaseCard) card).getPerishable()))
                    .textOffsetY(-5.0F)
                    .textOffsetX(5.0F)
                    .offsetY(52.0F)
                    .offsetX(40.0F)
                    .textColor(textColor.cpy())
                    .render(card);
        }
    }

    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        super.onSingleCardViewRender(card, sb);
        if(((BaseCard) card).isPerishable()) {
            float x = Settings.WIDTH / 2.0F - 500.0F;
            sb.draw(PerishableBg, x + 20.0F * Settings.scale, card.current_y + 1230.0F * card.drawScale * Settings.scale, 0.0F, 0.0F,
                    PerishableBg.getWidth(), PerishableBg.getHeight(), Settings.scale * 2.0F, Settings.scale * 2.0F, 0.0F, 0, 0,
                    PerishableBg.getWidth(), PerishableBg.getHeight(), false, false);
            FontHelper.renderFontCentered(sb, SCP_cardEnergyFont, String.valueOf(((BaseCard)card).getPerishable()), x + 90.0F * Settings.scale , card.current_y + 1240.0F * card.drawScale * Settings.scale, textColor.cpy());
        }

    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PerishableVisualModifier();
    }
}
