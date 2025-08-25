package balatro.events;

import balatro.cards.Dilation;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import static balatro.balatroMod.imagePath;
import static balatro.balatroMod.makeID;

public class WeirdJokerEvent extends PhasedEvent {
    public static final String ID = makeID("WeirdJokerEvent"); //The event's ID

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String IMG = imagePath("/events/weirdjoker1.jpg");

    public WeirdJokerEvent() {
        super(ID, NAME, IMG);

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(new TextPhase.OptionInfo(OPTIONS[0])
                        .setOptionResult((i)->{
                            imageEventText.loadImage(imagePath("/events/weirdjoker2.jpg"));
                            transitionKey("gainThat");
                        })
                )
        );


        registerPhase("gainThat", new TextPhase(DESCRIPTIONS[1])
                .addOption(new TextPhase.OptionInfo(OPTIONS[1])
                        .setOptionResult((i)->{
                            AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
                            AbstractDungeon.player.gold = 0;

                            AbstractCard rareCard = AbstractDungeon.srcRareCardPool.getRandomCard(true);
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(rareCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                            transitionKey("end");
                        })
                )
                .addOption(new TextPhase.OptionInfo(OPTIONS[2])
                        .setOptionResult((i)->{
                            AbstractDungeon.player.gainGold(200);

                            for (int j = 0; j < 5; j++) {
                                AbstractCard randomCard = AbstractDungeon.player.masterDeck.getRandomCard(true);
                                AbstractDungeon.effectList.add(new PurgeCardEffect(randomCard, MathUtils.random(0.1F, 0.9F) * Settings.WIDTH,MathUtils.random(0.2F, 0.8F) * Settings.HEIGHT));
                                AbstractDungeon.player.masterDeck.removeCard(randomCard);
                            }
                            transitionKey("end");
                        })
                )
                .addOption(new TextPhase.OptionInfo(OPTIONS[3], new Dilation())
                        .setOptionResult((i)->{
                            int effectCount = 0;
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Dilation(), Settings.WIDTH * 0.6F, Settings.HEIGHT / 2.0F));

                            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                                    if (c.canUpgrade()) {
                                        effectCount++;
                                        if (effectCount <= 20) {
                                            float x = MathUtils.random(0.1F, 0.9F) * Settings.WIDTH;
                                            float y = MathUtils.random(0.2F, 0.8F) * Settings.HEIGHT;
                                            AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c
                                                    .makeStatEquivalentCopy(), x, y));
                                            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
                                        }
                                        c.upgrade();
                                        AbstractDungeon.player.bottledCardUpgradeCheck(c);
                                    }
                            }
                            transitionKey("end");
                        })
                )
                .addOption(new TextPhase.OptionInfo(OPTIONS[4])
                        .setOptionResult((i)->{
                            openMap();
                        })
                )
        );

        registerPhase("end", new TextPhase(DESCRIPTIONS[2])
                .addOption(new TextPhase.OptionInfo(OPTIONS[4])
                        .setOptionResult((i)->{
                            openMap();
                        })
                )
        );

        transitionKey("start");

    }


}
