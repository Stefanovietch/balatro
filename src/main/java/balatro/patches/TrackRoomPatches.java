package balatro.patches;

import balatro.balatroMod;
import balatro.util.Data;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.scenes.AbstractScene;

public class TrackRoomPatches {
    @SpirePatch2(
            clz = AbstractScene.class,
            method = "nextRoom")
    public static class RoomHook {
        @SpireInsertPatch(
                rloc=0,
                localvars={"nextRoom"}
        )
        public static void room(@ByRef AbstractRoom[] room) {
            if (room[0] instanceof RestRoom) {
                Data.changeRestSites(1);
            }
        }
    }

    @SpirePatch2(
            clz = EventHelper.class,
            method = "roll",
            paramtypez={Random.class}
    )
    public static class RoomRollHook {
        @SpirePostfixPatch
        public static void QuestionRoom() {
            Data.changeEventRooms(1);
        }
    }
}
