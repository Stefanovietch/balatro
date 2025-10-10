package balatro.patches;

import balatro.balatroMod;
import balatro.character.baseDeck;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LoseGoldOnPlayCardPatchs {
    @SpirePatch2(clz = AbstractPlayer.class,
            method = "playCard"
    )
    public static class PlayCardHook {
        @SpirePostfixPatch
        public static void loseGold() {
            if(balatroMod.selectedStakeIndex >= 7 && AbstractDungeon.player instanceof baseDeck) {
                AbstractDungeon.player.loseGold(2);
            }
        }
    }
}
