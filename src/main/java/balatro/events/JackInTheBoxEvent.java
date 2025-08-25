package balatro.events;

import balatro.relics.NachoTong;
import balatro.relics.Palette;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import static balatro.balatroMod.imagePath;
import static balatro.balatroMod.makeID;

public class JackInTheBoxEvent extends PhasedEvent {
    public static final String ID = makeID("JackInTheBoxEvent"); //The event's ID

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String IMG = imagePath("/events/jackinthebox1.jpg");

    public JackInTheBoxEvent() {
        super(ID, NAME, IMG);

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(new TextPhase.OptionInfo(OPTIONS[0]).enabledCondition(() -> (AbstractDungeon.player.gold >= 300 && AbstractDungeon.player.maxHealth > 30), OPTIONS[1])
                        .setOptionResult((i)->{
                            imageEventText.loadImage(imagePath("/events/jackinthebox2.jpg"));
                            transitionKey("theBox");
                        })
                )
                .addOption(new TextPhase.OptionInfo(OPTIONS[2])
                        .setOptionResult((i)->{
                            imageEventText.loadImage(imagePath("/events/jackinthebox4.jpg"));
                            transitionKey("kick");
                        })
                )
                .addOption(new TextPhase.OptionInfo(OPTIONS[3])
                        .setOptionResult((i)-> openMap())
                )
        );


        registerPhase("theBox", new TextPhase(DESCRIPTIONS[1])
                .addOption(new TextPhase.OptionInfo(OPTIONS[4])
                        .setOptionResult((i)->{
                            AbstractDungeon.player.decreaseMaxHealth(30);
                            AbstractDungeon.player.loseGold(300);
                            transitionKey("bigReward");
                        })
                )
        );

        int damage;
        int playerHealth = AbstractDungeon.player.currentHealth;
        if (12 > playerHealth) {
            damage = playerHealth - 1;
        } else {
            damage = 12;
        }

        registerPhase("kick", new TextPhase(DESCRIPTIONS[4])
                .addOption(new TextPhase.OptionInfo(OPTIONS[9] + damage + OPTIONS[10])
                        .setOptionResult((i)->{
                            AbstractDungeon.player.damage(new DamageInfo(null, damage, DamageInfo.DamageType.HP_LOSS));
                            AbstractDungeon.player.gainGold(69);
                            openMap();
                        })
                )
        );

        registerPhase("bigReward", new TextPhase(DESCRIPTIONS[2])
                .addOption(new TextPhase.OptionInfo(OPTIONS[5], RelicLibrary.getRelic(NachoTong.ID))
                        .setOptionResult((i)->{
                            AbstractRelic relic = new NachoTong();
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX,this.drawY,relic);
                            imageEventText.loadImage(imagePath("/events/jackinthebox3.jpg"));
                            transitionKey("end");
                        })
                )
                .addOption(new TextPhase.OptionInfo(OPTIONS[6], RelicLibrary.getRelic(Palette.ID))
                        .setOptionResult((i)->{
                            AbstractRelic relic = new Palette();
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX,this.drawY,relic);
                            imageEventText.loadImage(imagePath("/events/jackinthebox3.jpg"));
                            transitionKey("end");
                        })
                )
                .addOption(new TextPhase.OptionInfo(OPTIONS[7])
                        .setOptionResult((i)->{
                            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck, 1, OPTIONS[11], false, false, false, false);
                            imageEventText.loadImage(imagePath("/events/jackinthebox3.jpg"));
                            transitionKey("end");
                        })
                )
        );

        registerPhase("end", new TextPhase(DESCRIPTIONS[3])
                .addOption(new TextPhase.OptionInfo(OPTIONS[8])
                        .setOptionResult((i)-> openMap())
                )
        );
        transitionKey("start");
    }

    public void update() {
        super.update();
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = (AbstractDungeon.gridSelectScreen.selectedCards.get(0)).makeStatEquivalentCopy();
            c.inBottleFlame = false;
            c.inBottleLightning = false;
            c.inBottleTornado = false;
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c.makeStatEquivalentCopy(), Settings.WIDTH / 3.0F, Settings.HEIGHT / 2.0F));
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c.makeStatEquivalentCopy(), Settings.WIDTH * 2.0F / 3.0F, Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }


}
