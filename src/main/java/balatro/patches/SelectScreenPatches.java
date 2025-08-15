package balatro.patches;

import balatro.character.baseDeck;
import balatro.balatroMod;
import balatro.relics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

import java.util.Objects;

public class SelectScreenPatches {
    @SpirePatch2(clz = CharacterOption.class, method = "update")
    public static class UpdateHook {
        @SpirePostfixPatch
        public static void updateCharOption(CharacterOption __instance) {
            if (__instance.c instanceof baseDeck && __instance.selected) {
                balatroMod.deckUI.update();
            }
        }
    }

    @SpirePatch2(clz = CharacterOption.class, method = "renderInfo")
    public static class RenderHook {
        @SpirePostfixPatch()
        public static void renderCharOption(CharacterOption __instance, SpriteBatch sb, float ___infoX) {
            if (__instance.c instanceof baseDeck && __instance.selected) {
                balatroMod.deckUI.render(sb, ___infoX);
            }
        }
    }

    @SpirePatch2(clz = CharacterOption.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {String.class, AbstractPlayer.class, Texture.class, Texture.class})
    public static class RaiseText {
        @SpirePostfixPatch
        public static void lift(AbstractPlayer c, @ByRef float[] ___infoY) {
            if (c instanceof baseDeck) {
                ___infoY[0] = Settings.HEIGHT*3/4f;
            }
        }
    }

    @SpirePatch2(clz = CharacterOption.class, method = "renderRelics")
    public static class RenderRelicHook {
        @SpirePostfixPatch()
        public static void renderRelicOption(CharacterOption __instance, SpriteBatch sb, float ___infoX) {
            if (__instance.c instanceof baseDeck && __instance.selected) {
                String relicID = "";
                String deckName = balatroMod.deckUI.selectedDeck;
                if (Objects.equals(deckName, "redDeck")) {
                    relicID = LowStakes.ID;
                } else if (Objects.equals(deckName, "blueDeck")) {
                    relicID = HeadsUp.ID;
                } else if (Objects.equals(deckName, "yellowDeck")) {
                    relicID = NestEgg.ID;
                } else if (Objects.equals(deckName, "greenDeck")) {
                    relicID = YouGetWhatYouGet.ID;
                } else if (Objects.equals(deckName, "blackDeck")) {
                    relicID = Royale.ID;
                } else if (Objects.equals(deckName, "magicDeck")) {
                    relicID = CrystalBall.ID;
                } else if (Objects.equals(deckName, "nebulaDeck")) {
                    relicID = Astronomy.ID;
                } else if (Objects.equals(deckName, "ghostDeck")) {
                    relicID = Clairvoyance.ID;
                } else if (Objects.equals(deckName, "abandonedDeck")) {
                    relicID = Flushed.ID;
                } else if (Objects.equals(deckName, "checkeredDeck")) {
                    relicID = Retrograde.ID;
                } else if (Objects.equals(deckName, "zodiacDeck")) {
                    relicID = ROI.ID;
                } else if (Objects.equals(deckName, "paintedDeck")) {
                    relicID = BigHands.ID;
                } else if (Objects.equals(deckName, "anaglyphDeck")) {
                    relicID = HighStakes.ID;
                } else if (Objects.equals(deckName, "plasmaDeck")) {
                    relicID = RuleBender.ID;
                } else if (Objects.equals(deckName, "erraticDeck")) {
                    relicID = Shattered.ID;
                }

                float infoX = ___infoX;
                float infoY = Settings.HEIGHT*3/4f;
                float var10002;
                String relicString;
                float var10003;
                float var10004;
                sb.setColor(Settings.QUARTER_TRANSPARENT_BLACK_COLOR);
                var10002 = infoX - 64.0F;
                sb.draw(RelicLibrary.getRelic(relicID).outlineImg, var10002, infoY - 60.0F * Settings.scale - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
                sb.setColor(Color.WHITE);
                var10002 = infoX - 64.0F;
                sb.draw(RelicLibrary.getRelic(relicID).img, var10002, infoY - 60.0F * Settings.scale - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
                var10003 = infoX + 44.0F * Settings.scale;
                var10004 = infoY - 40.0F * Settings.scale;
                FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, RelicLibrary.getRelic(relicID).name, var10003, var10004, 10000.0F, 10000.0F, Settings.GOLD_COLOR);
                relicString = RelicLibrary.getRelic(relicID).description;
                FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, relicString, infoX + 44.0F * Settings.scale, infoY - 66.0F * Settings.scale, 10000.0F, 10000.0F, Settings.CREAM_COLOR);
            }
        }
    }
}
