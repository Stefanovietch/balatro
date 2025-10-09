package balatro;

import balatro.cards.BaseCard;
import balatro.cards.RandomType;
import balatro.character.baseDeck;
import balatro.events.JackInTheBoxEvent;
import balatro.events.SpectralMerchantEvent;
import balatro.events.WeirdJokerEvent;
import balatro.powers.*;
import balatro.relics.*;
import balatro.ui.DeckSelectionUI;
import balatro.ui.GoldPerCombat;
import balatro.ui.StakeSelectionUI;
import balatro.util.Data;
import balatro.util.GeneralUtils;
import balatro.util.KeywordInfo;
import balatro.util.TextureLoader;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.abstracts.CustomSavable;
import basemod.eventUtil.AddEventParams;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.RagePower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static balatro.character.baseDeck.Enums.BASEDECK;

@SpireInitializer
public class balatroMod implements
        OnStartBattleSubscriber,
        PostBattleSubscriber,
        EditRelicsSubscriber,
        EditCharactersSubscriber,
        EditCardsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        StartGameSubscriber,
        OnPlayerTurnStartSubscriber,
        PostPotionUseSubscriber,
        StartActSubscriber,
        PostInitializeSubscriber
{

    public static String makeID(String id) {
        return modID + ":" + id;
    }

    public static ModInfo info;
    public static String modID; //Edit your pom.xml to change this
    static { loadModInfo(); }
    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.
    private static final String resourcesFolder = "balatro";

    //Red Deck
    private static final String BASEDECK_BG_ATTACK = characterPath("cardback/bg_attack.png");
    private static final String BASEDECK_BG_ATTACK_P = characterPath("cardback/bg_attack_p.png");
    private static final String BASEDECK_BG_SKILL = characterPath("cardback/bg_skill.png");
    private static final String BASEDECK_BG_SKILL_P = characterPath("cardback/bg_skill_p.png");
    private static final String BASEDECK_BG_POWER = characterPath("cardback/bg_power.png");
    private static final String BASEDECK_BG_POWER_P = characterPath("cardback/bg_power_p.png");
    private static final String BASEDECK_ENERGY_ORB = characterPath("cardback/energy_orb.png");
    private static final String BASEDECK_ENERGY_ORB_P = characterPath("cardback/energy_orb_p.png");
    private static final String BASEDECK_SMALL_ORB = characterPath("cardback/small_orb.png");
    private static final String BASEDECK_CHAR_SELECT_BUTTON = characterPath("select/button.png");
    private static final String BASEDECK_CHAR_SELECT_PORTRAIT = characterPath("select/portrait.png");

    public static SpireConfig balatroConfig;

    public static final String SELECTED_DECK_INDEX = "selectedDeckIndex";
    public static int selectedDeckIndex = 0;
    public static final String SELECTED_DECK = "selectedDeck";
    public static String selectedDeck = "redDeck";

    public static final String SELECTED_STAKE_INDEX = "selectedStakeIndex";
    public static int selectedStakeIndex = 0;
    public static final String SELECTED_STAKE = "selectedStake";
    public static String selectedStake = "whiteStake";

    public static final String COMBAT_GOLD_LIMIT = "combatGoldLimit";
    public static boolean combatGoldLimit = true;
    public static final String BLINDS = "blinds";
    public static boolean blinds = true;

    public static DeckSelectionUI deckUI;
    public static StakeSelectionUI stakeUI;

    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        new balatroMod();

        BaseMod.addColor(baseDeck.Enums.CARD_COLOR, Color.LIGHT_GRAY,
                BASEDECK_BG_ATTACK, BASEDECK_BG_SKILL, BASEDECK_BG_POWER, BASEDECK_ENERGY_ORB,
                BASEDECK_BG_ATTACK_P, BASEDECK_BG_SKILL_P, BASEDECK_BG_POWER_P, BASEDECK_ENERGY_ORB_P,
                BASEDECK_SMALL_ORB);
    }

    public balatroMod() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");

        Properties balatroDefaultSettings = new Properties();

        balatroDefaultSettings.setProperty(SELECTED_DECK_INDEX, Integer.toString(selectedDeckIndex));
        balatroDefaultSettings.setProperty(SELECTED_DECK, selectedDeck);
        balatroDefaultSettings.setProperty(SELECTED_STAKE_INDEX, Integer.toString(selectedStakeIndex));
        balatroDefaultSettings.setProperty(SELECTED_STAKE, selectedStake);
        balatroDefaultSettings.setProperty(COMBAT_GOLD_LIMIT, String.valueOf(combatGoldLimit));
        balatroDefaultSettings.setProperty(BLINDS, String.valueOf(blinds));

        try {
            balatroConfig = new SpireConfig("balatroMod", "balatroModConfig", balatroDefaultSettings);
            selectedDeckIndex = balatroConfig.getInt(SELECTED_DECK_INDEX);
            selectedDeck = balatroConfig.getString(SELECTED_DECK);
            selectedStakeIndex = balatroConfig.getInt(SELECTED_STAKE_INDEX);
            selectedStake = balatroConfig.getString(SELECTED_STAKE);
            combatGoldLimit = balatroConfig.getBool(COMBAT_GOLD_LIMIT);
            blinds = balatroConfig.getBool(BLINDS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receivePostInitialize() {
        deckUI = new DeckSelectionUI();
        stakeUI = new StakeSelectionUI();
        GoldPerCombat goldPerCombatPanel = new GoldPerCombat();
        if(combatGoldLimit) {BaseMod.addTopPanelItem(goldPerCombatPanel);}

        BaseMod.addEvent(new AddEventParams.Builder(SpectralMerchantEvent.ID, SpectralMerchantEvent.class)
                .playerClass(BASEDECK)
                .dungeonID(Exordium.ID)
                .create());
        BaseMod.addEvent(new AddEventParams.Builder(WeirdJokerEvent.ID, WeirdJokerEvent.class)
                .playerClass(BASEDECK)
                .dungeonID(TheCity.ID)
                .create());
        BaseMod.addEvent(new AddEventParams.Builder(JackInTheBoxEvent.ID, JackInTheBoxEvent.class)
                .playerClass(BASEDECK)
                .dungeonID(TheBeyond.ID)
                .create());

        ModPanel settingsPanel = new ModPanel();

        ModLabeledToggleButton enableCombatGoldLimit = new ModLabeledToggleButton("Combat Gold Limit", "Limits the amount of gold earned in combat.", 350.0F, 750.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, combatGoldLimit, settingsPanel, (label) -> {
        }, (button) -> {
            combatGoldLimit = button.enabled;
            if(combatGoldLimit) {
                BaseMod.addTopPanelItem(goldPerCombatPanel);
            } else {
                BaseMod.removeTopPanelItem(goldPerCombatPanel);
            }
            balatroConfig.setBool(COMBAT_GOLD_LIMIT, combatGoldLimit);
            try {
                balatroConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(enableCombatGoldLimit);

        ModLabeledToggleButton enableBlinds = new ModLabeledToggleButton("Boss/Elite Blinds", "Enables Boss/Elite blind abilities.", 350.0F, 550.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, blinds, settingsPanel, (label) -> {
        }, (button) -> {
            blinds = button.enabled;
            balatroConfig.setBool(BLINDS, blinds);
            try {
                balatroConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(enableBlinds);

        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, settingsPanel);
        initializeSavedData();
    }

    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
    private static String getLangString()
    {
        return Settings.language.name().toLowerCase();
    }
    private static final String defaultLanguage = "eng";

    public static final Map<String, KeywordInfo> keywords = new HashMap<>();

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage); //no exception catching for default localization; you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            }
            catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        //While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        //Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
    }

    @Override
    public void receiveEditKeywords()
    {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            keyword.prep();
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try
            {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    keyword.prep();
                    registerKeyword(keyword);
                }
            }
            catch (Exception e)
            {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
        if (!info.ID.isEmpty())
        {
            keywords.put(info.ID, info);
        }
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String imagePath(String file) {
        return resourcesFolder + "/images/" + file;
    }
    public static String characterPath(String file) {
        return resourcesFolder + "/images/character/" + file;
    }
    public static String powerPath(String file) {
        return resourcesFolder + "/images/powers/" + file;
    }
    public static String relicPath(String file) {
        return resourcesFolder + "/images/relics/" + file;
    }


    //This determines the mod's ID based on information stored by ModTheSpire.
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo)->{
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(balatroMod.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        }
        else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }
    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new baseDeck(),
                BASEDECK_CHAR_SELECT_BUTTON, BASEDECK_CHAR_SELECT_PORTRAIT, BASEDECK);
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseCard.class) //In the same package as this class
                .setDefaultSeen(true) //And marks them as seen in the compendium
                .cards(); //Adds the cards
    }

    @Override
    public void receiveStartGame() {
        if (CardCrawlGame.loadingSave && AbstractDungeon.player instanceof baseDeck) {
            ((baseDeck) AbstractDungeon.player).setDeck(selectedDeck);
        }
        if (!CardCrawlGame.loadingSave && AbstractDungeon.player instanceof baseDeck) {
            Data.resetData();
            Data.resetBossBlind();

            String deckName = balatroMod.deckUI.selectedDeck;
            ((baseDeck) AbstractDungeon.player).setDeck(deckName);

            String relicID = Lantern.ID;
            if (Objects.equals(deckName, "redDeck")) {
                relicID = LowStakes.ID;
                AbstractDungeon.relicsToRemoveOnStart.add(GamblingChip.ID);
            } else if (Objects.equals(deckName, "blueDeck")) {
                relicID = HeadsUp.ID;
            } else if (Objects.equals(deckName, "yellowDeck")) {
                relicID = NestEgg.ID;
                AbstractDungeon.relicsToRemoveOnStart.add(OldCoin.ID);
            } else if (Objects.equals(deckName, "greenDeck")) {
                relicID = YouGetWhatYouGet.ID;
            } else if (Objects.equals(deckName, "blackDeck")) {
                relicID = Royale.ID;
            } else if (Objects.equals(deckName, "magicDeck")) {
                relicID = CrystalBall.ID;
            } else if (Objects.equals(deckName, "nebulaDeck")) {
                relicID = Astronomy.ID;
            } else if (Objects.equals(deckName, "ghostDeck")) {
                relicID = Clairvoyance.ID;
            } else if (Objects.equals(deckName, "abandonedDeck")) {
                relicID = Flushed.ID;
            } else if (Objects.equals(deckName, "checkeredDeck")) {
                relicID = Retrograde.ID;
            } else if (Objects.equals(deckName, "zodiacDeck")) {
                relicID = ROI.ID;
            } else if (Objects.equals(deckName, "paintedDeck")) {
                relicID = BigHands.ID;
            } else if (Objects.equals(deckName, "anaglyphDeck")) {
                relicID = HighStakes.ID;
            } else if (Objects.equals(deckName, "plasmaDeck")) {
                relicID = RuleBender.ID;
            } else if (Objects.equals(deckName, "erraticDeck")) {
                relicID = Shattered.ID;
            }

            AbstractDungeon.relicsToRemoveOnStart.add(StrikeDummy.ID);
            AbstractDungeon.relicsToRemoveOnStart.add(QuestionCard.ID);
            AbstractDungeon.relicsToRemoveOnStart.add(MembershipCard.ID);
            if (!blinds) {AbstractDungeon.relicsToRemoveOnStart.add(Retcon.ID);}

            AbstractRelic r = RelicLibrary.getRelic(relicID).makeCopy();
            r.instantObtain(AbstractDungeon.player, 0, true);
            AbstractDungeon.relicsToRemoveOnStart.add(relicID);
        }
    }

    private void initializeSavedData() {
        BaseMod.addSaveField("Data", new CustomSavable<Map<String, Integer>>() {
            @Override
            public Type savedType() {
                return new TypeToken<Map<String, Integer>>(){}.getType();
            }
            @Override
            public Map<String, Integer> onSave() {
                return Data.getData();
            }
            @Override
            public void onLoad(Map<String, Integer> dataMap) {
                if (dataMap != null) {
                    Data.setData(dataMap);
                }
            }
        });
        BaseMod.addSaveField("BossBlinds", new CustomSavable<List<BlindPower.BlindType>>() {
            @Override
            public Type savedType() {
                return new TypeToken<List<BlindPower.BlindType>>(){}.getType();
            }
            @Override
            public List<BlindPower.BlindType> onSave() {
                return Data.getBossBlinds();
            }
            @Override
            public void onLoad(List<BlindPower.BlindType> dataMap) {
                if (dataMap != null) {
                    Data.setBossBlinds(dataMap);
                }
            }
        });
        BaseMod.addSaveField("StakesUnlocked", new CustomSavable<Map<String, Integer>>() {
            @Override
            public Type savedType() {
                return new TypeToken<Map<String, Integer>>(){}.getType();
            }
            @Override
            public Map<String, Integer> onSave() {
                return Data.getStakesUnlocked();
            }
            @Override
            public void onLoad(Map<String, Integer> dataMap) {
                if (dataMap != null) {
                    Data.setStakesUnlocked(dataMap);
                }
            }
        });
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseRelic.class) //In the same package as this class
                .any(BaseRelic.class, (info, relic) -> { //Run this code for any classes that extend this class
                    if (relic.pool != null)
                        BaseMod.addRelicToCustomPool(relic, relic.pool); //Register a custom character specific relic
                    else
                        BaseMod.addRelic(relic, relic.relicType); //Register a shared or base game character specific relic

                    //If the class is annotated with @AutoAdd.Seen, it will be marked as seen, making it visible in the relic library.
                    //If you want all your relics to be visible by default, just remove this if statement.
                    if (info.seen)
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                });
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        Data.saveBattleData();
        Data.resetBattleData();
        if(balatroMod.selectedStakeIndex >= 2) {
            int TotalHpToGive = AbstractDungeon.floorNum;
            int enemyCount = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            int[] hpValues = new int[enemyCount];
            int leftToDistribute = TotalHpToGive % enemyCount;
            Arrays.fill(hpValues, TotalHpToGive / enemyCount);
            Random random = new Random();
            for (int i = 0; i < leftToDistribute; i++) {
                hpValues[random.nextInt(enemyCount)]++;
            }

            for (int i = 0; i < enemyCount; i++) {
                AbstractDungeon.getCurrRoom().monsters.monsters.get(i).maxHealth += hpValues[i];
                AbstractDungeon.getCurrRoom().monsters.monsters.get(i).currentHealth += hpValues[i];
            }
        }
        if(balatroMod.selectedStakeIndex >= 5) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                monster.addToBot(new ApplyPowerAction(monster, monster, new TopUpPower(monster, 1), 1));
            }
        }


        if (blinds && (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite || AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)) {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m.type == AbstractMonster.EnemyType.ELITE) {
                    BlindPower.BlindType newBlindIndex = BlindPower.BlindType.randomType();
                    switch (newBlindIndex) {
                        case THE_HOOK:
                            m.addToBot(new ApplyPowerAction(m, m, new TheHookPower(m)));
                            break;
                        case THE_OX:
                            m.addToBot(new ApplyPowerAction(m, m, new TheOxPower(m)));
                            break;
                        case THE_HOUSE:
                            m.addToBot(new ApplyPowerAction(m, m, new TheHousePower(m)));
                            break;
                        case THE_WALL:
                            m.addToBot(new ApplyPowerAction(m, m, new TheWallPower(m)));
                            break;
                        case THE_WHEEL:
                            m.addToBot(new ApplyPowerAction(m, m, new TheWheelPower(m)));
                            break;
                        case THE_ARM:
                            m.addToBot(new ApplyPowerAction(m, m, new TheArmPower(m)));
                            break;
                        case THE_PSYCHIC:
                            m.addToBot(new ApplyPowerAction(m, m, new ThePsychicPower(m)));
                            break;
                        case THE_FISH:
                            m.addToBot(new ApplyPowerAction(m, m, new TheFishPower(m)));
                            break;
                        case THE_WATER:
                            m.addToBot(new ApplyPowerAction(m, m, new TheWaterPower(m)));
                            break;
                        case THE_MANACLE:
                            m.addToBot(new ApplyPowerAction(m, m, new TheManaclePower(m)));
                            break;
                        case THE_EYE:
                            m.addToBot(new ApplyPowerAction(m, m, new TheEyePower(m)));
                            break;
                        case THE_MOUTH:
                            m.addToBot(new ApplyPowerAction(m, m, new TheMouthPower(m)));
                            break;
                        case THE_PLANT:
                            m.addToBot(new ApplyPowerAction(m, m, new ThePlantPower(m)));
                            break;
                        case THE_NEEDLE:
                            m.addToBot(new ApplyPowerAction(m, m, new TheNeedlePower(m)));
                            break;
                        case THE_HEAD:
                            m.addToBot(new ApplyPowerAction(m, m, new TheHeadPower(m)));
                            break;
                        case THE_FLINT:
                            m.addToBot(new ApplyPowerAction(m, m, new TheFlintPower(m)));
                            break;
                        case THE_TOOTH:
                            m.addToBot(new ApplyPowerAction(m, m, new TheToothPower(m)));
                            break;
                        case THE_MARK:
                            m.addToBot(new ApplyPowerAction(m, m, new TheMarkPower(m)));
                            break;
                        case THE_CLUB:
                            m.addToBot(new ApplyPowerAction(m, m, new TheMarkPower(m)));
                            break;
                        case THE_GOAD:
                            m.addToBot(new ApplyPowerAction(m, m, new TheMarkPower(m)));
                            break;
                        case THE_WINDOW:
                            m.addToBot(new ApplyPowerAction(m, m, new TheMarkPower(m)));
                            break;
                        case THE_SERPENT:
                            m.addToBot(new ApplyPowerAction(m, m, new TheMarkPower(m)));
                            break;
                        default:
                            balatroMod.logger.info("blind doesnt exist: {}", newBlindIndex);
                            break;
                    }
                    break;
                }
                if (m.type == AbstractMonster.EnemyType.BOSS) {
                    BlindPower.BlindType newBlindIndex = BlindPower.BlindType.randomBossType();
                    while (Data.getBossBlinds().contains(newBlindIndex) || (Objects.equals(m.name, "Corrupt Heart") && newBlindIndex == BlindPower.BlindType.VIOLET_VESSEL)) {
                        newBlindIndex = BlindPower.BlindType.randomBossType();
                    }
                    Data.useBossBlind(newBlindIndex);
                    switch (newBlindIndex) {
                        case AMBER_ACORN:
                            m.addToBot(new ApplyPowerAction(m, m, new AmberAcornPower(m)));
                            break;
                        case VERDANT_LEAF:
                            m.addToBot(new ApplyPowerAction(m, m, new VerdantLeafPower(m)));
                            break;
                        case VIOLET_VESSEL:
                            m.addToBot(new ApplyPowerAction(m, m, new VioletVesselPower(m)));
                            break;
                        case CRIMSON_HEART:
                            m.addToBot(new ApplyPowerAction(m, m, new CrimsonHeartPower(m)));
                            break;
                        case CERULEAN_BELL:
                            m.addToBot(new ApplyPowerAction(m, m, new CeruleanBellPower(m)));
                            break;
                        default:
                            balatroMod.logger.info("boss blind doesnt exist: {}", newBlindIndex);
                            break;
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        Data.saveBattleData();
        Data.resetBattleData();
    }

    @Override
    public void receivePostPotionUse(AbstractPotion abstractPotion) {
        Data.changePotionsUsed(1);
    }

    @Override
    public void receiveStartAct() {
        Data.resetRestSites();
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof RandomType) {((RandomType) c).setRandomType();}
        }
        for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof RandomType) {((RandomType) c).setRandomType();}
        }
        for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof RandomType) {((RandomType) c).setRandomType();}
        }
        for(AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof RandomType) {((RandomType) c).setRandomType();}
        }
    }

}
