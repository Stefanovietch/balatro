package balatro.patches;

import balatro.cards.*;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.aiRng;

public class AstronomerPatches {
    @SpirePatch2(clz = AbstractCard.class, method = "freeToPlay")
    public static class FreeToPlayHook {
        @SpirePostfixPatch
        public static boolean setUpgradedFree(boolean __result, boolean ___upgraded) {
            if (AbstractDungeon.player != null && AbstractDungeon.currMapNode != null &&
                    (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                    AbstractDungeon.player.hasPower("Balatro:Astronomer") && ___upgraded) {
                return true;
            } else {
                return __result;
            }
        }
    }
}
