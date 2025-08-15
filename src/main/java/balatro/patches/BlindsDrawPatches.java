package balatro.patches;

import balatro.powers.BlindPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class BlindsDrawPatches {
    @SpirePatch2(
            clz = DrawCardAction.class,
            method = "update"
    )
    public static class drawHook {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void triggerBlindDraws() {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                for (AbstractPower p : m.powers) {
                    if (p instanceof BlindPower) {
                        ((BlindPower) p).onDrawCard(AbstractDungeon.player.drawPile.getTopCard());
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            }
        }
    }

    @SpirePatch2(
            clz = GameActionManager.class,
            method = "getNextAction"
    )
    public static class afterDrawHook {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void triggerAfterDrawBlinds() {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                for (AbstractPower p : m.powers) {
                    if (p instanceof BlindPower) {
                        p.atStartOfTurnPostDraw();
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "applyStartOfTurnPostDrawPowers");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            }
        }
    }
}
