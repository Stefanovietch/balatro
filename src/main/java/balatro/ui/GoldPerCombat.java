package balatro.ui;

import balatro.character.baseDeck;
import balatro.util.Data;
import basemod.TopPanelItem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import static balatro.balatroMod.makeID;

public class GoldPerCombat extends TopPanelItem {
    public static final String ID = makeID("GoldPerCombat");
    private static final float tipYpos = Settings.HEIGHT - (120.0f * Settings.scale);
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    public GoldPerCombat() {
        super(ImageMaster.TP_GOLD, ID);
    }

    @Override
    public boolean isClickable() {
        return false;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (AbstractDungeon.player instanceof baseDeck) {
            render(sb, Settings.GOLD_COLOR);
            FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelAmountFont,
                    Data.getGoldCombat() + " / " + Data.getMaxGoldCombat(),
                    this.x, this.y, Settings.GOLD_COLOR);
            if (getHitbox().hovered) {
                TipHelper.renderGenericTip(getHitbox().x, tipYpos, uiStrings.TEXT[0], uiStrings.TEXT[1]);
            }
        }
    }

    @Override
    protected void onClick() {

    }
}