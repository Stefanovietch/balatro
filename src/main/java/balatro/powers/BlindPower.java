package balatro.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.megacrit.cardcrawl.core.Settings.seed;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.aiRng;

public abstract class BlindPower extends BasePower{
    BlindType blindType;

    public BlindPower(String id, AbstractCreature owner, BlindType type) {
        super(id,  PowerType.BUFF, false, owner, -1);
        this.blindType = type;
    }

    public BlindType getBlindType() {
        return this.blindType;
    }

    public void onDrawCard(AbstractCard card) {

    }

    @Override
    public void onDeath() {
        super.onDeath();
        onRemove();
    }

    public enum BlindType {
        THE_ARM,
        THE_FISH,
        THE_HOOK,
        THE_HOUSE,
        THE_OX,
        THE_PSYCHIC,
        THE_WALL,
        THE_WHEEL,
        THE_WATER,
        THE_MANACLE,
        THE_EYE,
        THE_MOUTH,
        THE_PLANT,
        THE_NEEDLE,
        THE_HEAD,
        THE_TOOTH,
        THE_FLINT,
        THE_MARK,
        THE_CLUB,
        THE_GOAD,
        THE_SERPENT,
        THE_WINDOW,

        AMBER_ACORN,
        VERDANT_LEAF,
        VIOLET_VESSEL,
        CRIMSON_HEART,
        CERULEAN_BELL;

        BlindType() {
        }

        private static final List<BlindType> TYPES = new ArrayList<>(Arrays.asList(
                THE_ARM, THE_FISH, THE_HOOK, THE_HOUSE, THE_OX, THE_PSYCHIC, THE_WALL, THE_WHEEL,
                THE_WATER, THE_MANACLE, THE_EYE, THE_MOUTH, THE_PLANT, THE_NEEDLE, THE_HEAD, THE_TOOTH,
                THE_FLINT, THE_MARK, THE_CLUB, THE_GOAD, THE_SERPENT, THE_WINDOW));

        private static final List<BlindType> BOSS_TYPES = new ArrayList<>(Arrays.asList(
                AMBER_ACORN,VERDANT_LEAF,VIOLET_VESSEL,CRIMSON_HEART,CERULEAN_BELL));

        public static BlindType randomType() {
            return TYPES.get(aiRng.random(TYPES.size()-1));
        }

        public static BlindType randomBossType() {
            return BOSS_TYPES.get(aiRng.random(BOSS_TYPES.size()-1));
        }
    }
}
