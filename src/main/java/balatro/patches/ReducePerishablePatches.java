package balatro.patches;

import balatro.cards.BaseCard;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class ReducePerishablePatches {
    @SpirePatch2(clz = AbstractPlayer.class,
            method = "useCard"
    )
    public static class PlayCardHook {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars={"c"}
        )
        public static void reducePerishable(@ByRef AbstractCard[] c) {
            if(c[0] instanceof BaseCard) {
                ((BaseCard) c[0]).reducePerishable();
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "use");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            }
        }
    }
}
