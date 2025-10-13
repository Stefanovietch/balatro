package balatro.patches;

import balatro.balatroMod;
import balatro.character.baseDeck;
import balatro.relics.*;
import balatro.util.Data;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.GamblingChip;
import com.megacrit.cardcrawl.relics.Lantern;
import com.megacrit.cardcrawl.relics.OldCoin;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.VictoryScreen;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static balatro.balatroMod.balatroConfig;

public class UnlockStakesPatches {
    @SpirePatch2(clz = DeathScreen.class,
            method = "submitVictoryMetrics"
    )
    public static class VictoryHook {
        @SpirePrefixPatch
        public static void nextStake() {
            if (AbstractDungeon.player instanceof baseDeck) {
                unlockNextStake();
            }
        }
    }

    @SpirePatch2(clz = VictoryScreen.class,
            method = "submitVictoryMetrics"
    )
    public static class Victory2Hook {
        @SpirePrefixPatch
        public static void nextStake() {
            if (AbstractDungeon.player instanceof baseDeck) {
                unlockNextStake();
            }
        }
    }

    public static void unlockNextStake() {
        String deckName = balatroMod.deckUI.selectedDeck;

        Supplier<Integer> getter = balatroMod.stakeGetters.get(deckName);
        Consumer<Integer> setter = balatroMod.stakeSetters.get(deckName);

        if (getter != null && setter != null) {
            int stakeUnlocked = getter.get();
            if (balatroMod.selectedStakeIndex >= stakeUnlocked) {
                setter.accept(balatroMod.selectedStakeIndex + 1);
                balatroConfig.setInt(deckName+"Stake",balatroMod.selectedStakeIndex + 1);
                try {
                    balatroConfig.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
