package balatro.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

import static balatro.util.GeneralUtils.hasRelic;
import static com.megacrit.cardcrawl.helpers.RelicLibrary.getRelic;

public class ObtainPotionPatches {
    @SpirePatch2(
            clz = AbstractPlayer.class,
            method = "obtainPotion",
            paramtypez={AbstractPotion.class}
    )
    public static class normalObtain {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void obtainPotion() {
            if (hasRelic("Balatro:TarotTycoon")) {
                getRelic("Balatro:TarotTycoon").flash();
                AbstractDungeon.player.increaseMaxHp(2, true);
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
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPotion.class, "setAsObtained");
                // the `new ArrayList<Matcher>()` specifies the prerequisites before the line can be matched -
                // the LineFinder will search for all the prerequisites in order before it will match the finalMatcher -
                // since we didn't specify any prerequisites here, the LineFinder will simply find the first expression
                // that matches the finalMatcher.
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            }
        }
    }

    @SpirePatch2(
            clz = AbstractPlayer.class,
            method = "obtainPotion",
            paramtypez={int.class, AbstractPotion.class}
    )
    public static class setObtain {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void obtainPotion() {
            if (hasRelic("Balatro:TarotTycoon")) {
                getRelic("Balatro:TarotTycoon").flash();
                AbstractDungeon.player.increaseMaxHp(2, true);
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
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPotion.class, "setAsObtained");
                // the `new ArrayList<Matcher>()` specifies the prerequisites before the line can be matched -
                // the LineFinder will search for all the prerequisites in order before it will match the finalMatcher -
                // since we didn't specify any prerequisites here, the LineFinder will simply find the first expression
                // that matches the finalMatcher.
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            }
        }
    }
}
