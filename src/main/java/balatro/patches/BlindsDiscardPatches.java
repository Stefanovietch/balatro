package balatro.patches;

import balatro.powers.BlindPower;
import balatro.powers.TheWaterPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class BlindsDiscardPatches {
    @SpirePatch2(
            clz = DiscardAction.class,
            method = "update"
    )
    public static class discardHook {
        @SpireInsertPatch(
                rloc = 0,
                localvars = {"duration","isDone","endTurn"}
        )
        public static void preventDiscard(@ByRef float[] duration, @ByRef boolean[] isDone, boolean endTurn) {
            if (!endTurn) {
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    for (AbstractPower p : m.powers) {
                        if (p instanceof TheWaterPower) {
                            duration[0] = 0;
                            isDone[0] = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    @SpirePatch2(
            clz = DiscardSpecificCardAction.class,
            method = "update"
    )
    public static class discardSpecificHook {
        @SpireInsertPatch(
                rloc = 0,
                localvars = {"duration","isDone"}
        )
        public static void preventDiscard(@ByRef float[] duration, @ByRef boolean[] isDone) {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                for (AbstractPower p : m.powers) {
                    if (p instanceof TheWaterPower) {
                        duration[0] = 0;
                        isDone[0] = true;
                        break;
                    }
                }
            }
        }
    }

    @SpirePatch2(
            clz = GameActionManager.class,
            method = "callEndOfTurnActions"
    )
    public static class beforeEndTurnDiscardHook {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void triggerEndTurnBlinds() {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                for (AbstractPower p : m.powers) {
                    if (p instanceof BlindPower) {
                        p.atEndOfTurnPreEndTurnCards(false);
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractRoom.class, "applyEndOfTurnPreCardPowers");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            }
        }
    }
}
