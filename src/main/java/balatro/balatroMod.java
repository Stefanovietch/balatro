package balatro;

import balatro.cards.*;
import balatro.util.GoldPerCombat;
import balatro.relics.*;
import balatro.ui.DeckSelectionUI;
import balatro.util.Data;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.*;
import balatro.util.GeneralUtils;
import balatro.util.KeywordInfo;
import balatro.util.TextureLoader;
import balatro.character.baseDeck;
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
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
        PostInitializeSubscriber {

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

    public static DeckSelectionUI deckUI;

    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        new balatroMod();

        BaseMod.addColor(baseDeck.Enums.CARD_COLOR, Color.LIGHT_GRAY,
                BASEDECK_BG_ATTACK, BASEDECK_BG_SKILL, BASEDECK_BG_POWER, BASEDECK_ENERGY_ORB,
                BASEDECK_BG_ATTACK_P, BASEDECK_BG_SKILL_P, BASEDECK_BG_POWER_P, BASEDECK_ENERGY_ORB_P,
                BASEDECK_SMALL_ORB);

        Properties balatroDefaultSettings = new Properties();

        balatroDefaultSettings.setProperty(SELECTED_DECK_INDEX, Integer.toString(selectedDeckIndex));
        balatroDefaultSettings.setProperty(SELECTED_DECK, selectedDeck);

        try {
            balatroConfig = new SpireConfig("balatroMod", "balatroModConfig", balatroDefaultSettings);
            selectedDeckIndex = balatroConfig.getInt(SELECTED_DECK_INDEX);
            selectedDeck = balatroConfig.getString(SELECTED_DECK);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public balatroMod() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");
    }

    @Override
    public void receivePostInitialize() {
        //This loads the image used as an icon in the in-game mods menu.
        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));
        //Set up the mod information displayed in the in-game mods menu.
        //The information used is taken from your pom.xml file.
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, null);

        deckUI = new DeckSelectionUI();
        BaseMod.addTopPanelItem(new GoldPerCombat());
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
                BASEDECK_CHAR_SELECT_BUTTON, BASEDECK_CHAR_SELECT_PORTRAIT, baseDeck.Enums.BASEDECK);
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
        if (!CardCrawlGame.loadingSave && AbstractDungeon.player instanceof baseDeck) {
            Data.resetData();

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
                relicID = CardPlayer.ID;
            } else if (Objects.equals(deckName, "magicDeck")) {
                relicID = Cartomancy.ID;
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
        Data.resetBattleData();
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
