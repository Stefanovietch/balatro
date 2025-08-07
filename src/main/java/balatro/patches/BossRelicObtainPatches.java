package balatro.patches;

import balatro.balatroMod;
import balatro.util.Data;
import basemod.devcommands.relic.Relic;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CallingBell;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.helpers.RelicLibrary.getRelic;

public class BossRelicObtainPatches {
    @SpirePatch2(
            clz = ProceedButton.class,
            method = "update")
    public static class ObtainHook {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void relicObtain() {
            if (hasRelic("Balatro:HighStakes")) {
                getRelic("Balatro:HighStakes").flash();
                AbstractRelic r = AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS);
                r.bossObtainLogic();
            }
        }

        private static class Locator extends SpireInsertLocator {
            // This is the abstract method from SpireInsertLocator that will be used to find the line
            // numbers you want this patch inserted at
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                // finalMatcher is the line that we want to insert our patch before -
                // in this example we are using a `MethodCallMatcher` which is a type
                // of matcher that matches a method call based on the type of the calling
                // object and the name of the method being called. Here you can see that
                // we're expecting the `end` method to be called on a `SpireBatch`
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(Settings.class, "isDemo");
                // the `new ArrayList<Matcher>()` specifies the prerequisites before the line can be matched -
                // the LineFinder will search for all the prerequisites in order before it will match the finalMatcher -
                // since we didn't specify any prerequisites here, the LineFinder will simply find the first expression
                // that matches the finalMatcher.
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            }
        }

        private static boolean hasRelic(String targetID) {
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r.relicId.equals(targetID))
                    return true;
            }
            return false;
        }
    }
}
