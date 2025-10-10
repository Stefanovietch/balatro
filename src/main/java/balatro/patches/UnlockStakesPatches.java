package balatro.patches;

import balatro.balatroMod;
import balatro.character.baseDeck;
import balatro.util.Data;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.VictoryScreen;

public class UnlockStakesPatches {
    @SpirePatch2(clz = DeathScreen.class,
            method = "submitVictoryMetrics"
    )
    public static class VictoryHook {
        @SpirePrefixPatch
        public static void unlockNextStake() {
            if (AbstractDungeon.player instanceof baseDeck) {
                Data.unlockNextStakeForDeck(balatroMod.selectedDeck);
            }
        }
    }

    @SpirePatch2(clz = VictoryScreen.class,
            method = "submitVictoryMetrics"
    )
    public static class Victory2Hook {
        @SpirePrefixPatch
        public static void unlockNextStake() {
            if (AbstractDungeon.player instanceof baseDeck) {
            Data.unlockNextStakeForDeck(balatroMod.selectedDeck);
            }
        }
    }
}
