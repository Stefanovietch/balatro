package balatro.patches;

import balatro.balatroMod;
import balatro.cards.CleverJoker;
import balatro.cards.JollyJoker;
import balatro.cards.MadJoker;
import balatro.cards.SlyJoker;
import balatro.character.baseDeck;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.DrugDealer;
import com.megacrit.cardcrawl.events.exordium.LivingWall;
import com.megacrit.cardcrawl.events.shrines.Designer;
import com.megacrit.cardcrawl.events.shrines.Transmogrifier;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;

import java.util.Objects;
import java.util.Optional;

public class RemoveBasicCardPurgePatches {
    @SpirePatch2(clz = CardGroup.class,
            method = "getPurgeableCards"
    )



    @SpirePatch2(clz = CardGroup.class,
            method = "getPurgeableCards"
    )
    public static class GetPurgeCards {
        @SpirePostfixPatch
        public static CardGroup removeBasicCards(CardGroup __result) {
            if (balatroMod.selectedStakeIndex >= 3 && AbstractDungeon.player instanceof baseDeck) {
                AbstractRoom room = AbstractDungeon.getCurrRoom();

                if (room instanceof TreasureRoomBoss) {
                    if (AbstractDungeon.player.hasRelic("Astrolabe")) {
                        return __result;
                    }
                }

                if (room instanceof EventRoom) {
                    if (room.event instanceof Transmogrifier) {
                        return __result;
                    }
                    if (room.event instanceof DrugDealer) {
                        return __result;
                    }
                    if (room.event instanceof LivingWall) {
                        Object x = Optional.ofNullable(ReflectionHacks.getPrivate(room.event, LivingWall.class, "choice")).get();
                        if (Objects.equals(x.toString(), "CHANGE")) {
                            return __result;
                        }
                    }
                    if (room.event instanceof Designer) {
                        Object x = Optional.ofNullable(ReflectionHacks.getPrivate(room.event, Designer.class, "option")).get();
                        if (Objects.equals(x.toString(), "TRANSFORM")) {
                            return __result;
                        }
                    }
                }

                __result.group.removeIf(card -> card instanceof SlyJoker || card instanceof JollyJoker || card instanceof MadJoker || card instanceof CleverJoker);
            }
            return __result;
        }

        private enum Choice {
            CHANGE
        }
    }
}
