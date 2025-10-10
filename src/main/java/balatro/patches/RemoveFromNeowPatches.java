package balatro.patches;

import balatro.balatroMod;
import balatro.cards.CleverJoker;
import balatro.cards.JollyJoker;
import balatro.cards.MadJoker;
import balatro.cards.SlyJoker;
import balatro.character.baseDeck;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.events.city.DrugDealer;
import com.megacrit.cardcrawl.events.exordium.LivingWall;
import com.megacrit.cardcrawl.events.shrines.Designer;
import com.megacrit.cardcrawl.events.shrines.Transmogrifier;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class RemoveFromNeowPatches {
    @SpirePatch2(clz = NeowEvent.class,
            method = "blessing"
    )
    public static class GetBlessing {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars={"rewards"}
        )
        public static void removeRemoveOptions(@ByRef ArrayList<NeowReward>[] rewards) {
            if (balatroMod.selectedStakeIndex >= 3 && AbstractDungeon.player instanceof baseDeck) {
                while(rewards[0].get(0).type == NeowReward.NeowRewardType.REMOVE_CARD) {
                    rewards[0].set(0, new NeowReward(0));
                }
                if(rewards[0].size() > 2) {
                    while (rewards[0].get(2).type == NeowReward.NeowRewardType.REMOVE_TWO) {
                        rewards[0].set(2, new NeowReward(2));
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(RoomEventDialog.class, "clearRemainingOptions");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            }
        }
    }
}
