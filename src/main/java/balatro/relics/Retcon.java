package balatro.relics;

import balatro.balatroMod;
import balatro.character.baseDeck;
import balatro.powers.*;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static balatro.balatroMod.makeID;

public class Retcon extends BaseRelic implements ClickableRelic {
    private static final String NAME = "Retcon"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SHOP; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public Retcon() {
        super(ID, NAME, baseDeck.Enums.CARD_COLOR, RARITY, SOUND);
    }
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onRightClick() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.player.gold >= 50) {
            AbstractMonster blindTarget = null;
            AbstractPower blindPower = null;
            BlindPower.BlindType blindIndex = null;
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if(m.type == AbstractMonster.EnemyType.ELITE || m.type == AbstractMonster.EnemyType.BOSS) {
                    for (AbstractPower p : m.powers) {
                        if (p instanceof BlindPower) {
                            blindPower = p;
                            blindTarget = m;
                            blindIndex = ((BlindPower) p).getBlindType();
                            break;
                        }
                    }
                }
            }
            if (blindTarget != null && AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && GameActionManager.turn == 1) {
                blindTarget.addToTop(new RemoveSpecificPowerAction(blindTarget, blindTarget, blindPower.ID));

                if (blindTarget.type == AbstractMonster.EnemyType.ELITE) {
                    BlindPower.BlindType newBlindIndex = BlindPower.BlindType.randomType();
                    while (blindIndex == newBlindIndex) {
                        newBlindIndex = BlindPower.BlindType.randomType();
                    }

                    switch (newBlindIndex) {
                        case THE_HOOK:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new TheHookPower(blindTarget)));
                            break;
                        case THE_OX:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new TheOxPower(blindTarget)));
                            break;
                        case THE_HOUSE:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new TheHousePower(blindTarget)));
                            break;
                        case THE_WALL:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new TheWallPower(blindTarget)));
                            break;
                        case THE_WHEEL:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new TheWheelPower(blindTarget)));
                            break;
                        case THE_ARM:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new TheArmPower(blindTarget)));
                            break;
                        case THE_PSYCHIC:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new ThePsychicPower(blindTarget)));
                            break;
                        case THE_FISH:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new TheFishPower(blindTarget)));
                            break;
                        case THE_WATER:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new TheWaterPower(blindTarget)));
                            break;
                        case THE_MANACLE:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new TheManaclePower(blindTarget)));
                            break;
                        case THE_EYE:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new TheEyePower(blindTarget)));
                            break;
                        case THE_MOUTH:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new TheMouthPower(blindTarget)));
                            break;
                        case THE_PLANT:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new ThePlantPower(blindTarget)));
                            break;
                        case THE_NEEDLE:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new TheNeedlePower(blindTarget)));
                            break;
                        case THE_HEAD:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new TheHeadPower(blindTarget)));
                            break;
                        case THE_FLINT:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new TheFlintPower(blindTarget)));
                            break;
                        case THE_TOOTH:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new TheToothPower(blindTarget)));
                            break;
                        case THE_MARK:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new TheMarkPower(blindTarget)));
                            break;
                        default:
                            balatroMod.logger.info("blind doesnt exist: {}", newBlindIndex);
                            break;
                    }
                } else {
                    BlindPower.BlindType newBlindIndex = BlindPower.BlindType.randomBossType();
                    while (blindIndex == newBlindIndex) {
                        newBlindIndex = BlindPower.BlindType.randomBossType();
                    }
                    switch (newBlindIndex) {
                        case AMBER_ACORN:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new AmberAcornPower(blindTarget)));
                            break;
                        case VERDANT_LEAF:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new VerdantLeafPower(blindTarget)));
                            break;
                        case VIOLET_VESSEL:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new VioletVesselPower(blindTarget)));
                            break;
                        case CRIMSON_HEART:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new CrimsonHeartPower(blindTarget)));
                            break;
                        case CERULEAN_BELL:
                            blindTarget.addToBot(new ApplyPowerAction(blindTarget, blindTarget, new CeruleanBellPower(blindTarget)));
                            break;
                        default:
                            balatroMod.logger.info("boss blind doesnt exist: {}", newBlindIndex);
                            break;
                    }
                }

                AbstractDungeon.player.loseGold(50);
            }
        }

    }
}
