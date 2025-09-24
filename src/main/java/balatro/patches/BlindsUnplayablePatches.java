package balatro.patches;

import balatro.balatroMod;
import balatro.powers.TheEyePower;
import balatro.powers.TheMouthPower;
import balatro.powers.ThePsychicPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

public class BlindsUnplayablePatches {
    @SpirePatch2(
            clz = AbstractCard.class,
            method = "hasEnoughEnergy"
    )
    public static class isPlayable {
        @SpirePostfixPatch
        public static boolean checkForPower(boolean __result, AbstractCard.CardType ___type) {
            if (!__result) {return false;}
            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) {
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    for (AbstractPower p : m.powers) {
                        if (p instanceof ThePsychicPower && p.amount >= 5) {
                            return false;
                        }
                        if (p instanceof TheEyePower) {
                            return ((TheEyePower) p).getPlayable(___type);
                        }
                        if (p instanceof TheMouthPower) {
                            return ((TheMouthPower) p).getPlayable(___type);
                        }
                    }
                }
            }
            return true;
        }
    }
}
