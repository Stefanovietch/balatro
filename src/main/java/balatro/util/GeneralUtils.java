package balatro.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class GeneralUtils {
    public static String arrToString(Object[] arr) {
        if (arr == null)
            return null;
        if (arr.length == 0)
            return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length - 1; ++i) {
            sb.append(arr[i]).append(", ");
        }
        sb.append(arr[arr.length - 1]);
        return sb.toString();
    }

    public static String removePrefix(String ID) {
        return ID.substring(ID.indexOf(":") + 1);
    }

    public static int getProbabilityModifier() {
        int temp = 0;
        for (AbstractPower pow : AbstractDungeon.player.powers) {
            if (pow instanceof ProbabilityPower) {
                temp = ((ProbabilityPower) pow).modifyProbability(temp);
            }
        }
        return temp;
    }

    public static boolean hasRelic(String targetID) {
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r.relicId.equals(targetID))
                return true;
        }
        return false;
    }
}
