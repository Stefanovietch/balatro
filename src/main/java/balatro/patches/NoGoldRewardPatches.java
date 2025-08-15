package balatro.patches;

import balatro.balatroMod;
import balatro.character.baseDeck;
import balatro.relics.YouGetWhatYouGet;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

import static balatro.util.GeneralUtils.hasRelic;
import static com.megacrit.cardcrawl.helpers.RelicLibrary.getRelic;

public class NoGoldRewardPatches {
    @SpirePatch2(clz = AbstractRoom.class, method = "addGoldToRewards")
    public static class GetGoldHook {
        @SpireInsertPatch(
                rloc=0,
                localvars={"gold"}
        )
        public static void removeGold(@ByRef(type="int") int[] gold) {
            if (hasRelic("Balatro:YouGetWhatYouGet")) {
                getRelic("Balatro:YouGetWhatYouGet").flash();
                gold[0] = 0;
            }
        }
    }
}
