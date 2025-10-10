package balatro.patches;

import balatro.balatroMod;
import balatro.character.baseDeck;
import balatro.powers.BlindPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class ReduceFirstDrawPatches {
    @SpirePatch2(
            clz = AbstractRoom.class,
            method = "update"
    )
    public static class beforeDrawHook {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void reduceDraw() {
            if(balatroMod.selectedStakeIndex >= 4 && AbstractDungeon.player instanceof baseDeck) {
                AbstractDungeon.player.gameHandSize--;
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "applyStartOfCombatPreDrawLogic");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            }
        }
    }

    @SpirePatch2(
            clz = AbstractRoom.class,
            method = "update"
    )
    public static class afterDrawHook {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void giveBackDraw() {
            if(balatroMod.selectedStakeIndex >= 4) {
                AbstractDungeon.player.gameHandSize++;
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "applyStartOfCombatLogic");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            }
        }
    }
}
