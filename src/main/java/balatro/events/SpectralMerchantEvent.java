package balatro.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import static balatro.balatroMod.imagePath;
import static balatro.balatroMod.makeID;

public class SpectralMerchantEvent extends PhasedEvent {
    public static final String ID = makeID("SpectralMerchantEvent"); //The event's ID

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String IMG = imagePath("/events/spectralmerchant1.jpg");

    public SpectralMerchantEvent() {
        super(ID, NAME, IMG);

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(new TextPhase.OptionInfo(OPTIONS[0])
                        .setOptionResult((i)->{
                                imageEventText.loadImage(imagePath("/events/spectralmerchant2.jpg"));
                                transitionKey("loseCard");
                        })
                )
                .addOption(new TextPhase.OptionInfo(OPTIONS[1])
                        .setOptionResult((i)->{
                                openMap();
                        })
                )
        );

        registerPhase("loseCard", new TextPhase(DESCRIPTIONS[1])
                .addOption(new TextPhase.OptionInfo(OPTIONS[2])
                        .setOptionResult((i)->{
                                AbstractCard randomCard = AbstractDungeon.player.masterDeck.getRandomCard(true);
                                AbstractDungeon.effectList.add(new PurgeCardEffect(randomCard));
                                AbstractDungeon.player.masterDeck.removeCard(randomCard);
                                imageEventText.loadImage(imagePath("/events/spectralmerchant3.jpg"));
                                transitionKey("gainCards");
                        })
                )
        );

        registerPhase("gainCards", new TextPhase(DESCRIPTIONS[2])
                .addOption(new TextPhase.OptionInfo(OPTIONS[3])
                        .setOptionResult((i)->{
                                AbstractCard rareCard = AbstractDungeon.srcRareCardPool.getRandomCard(true);
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(rareCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                                transitionKey("end");
                        })
                )
                .addOption(new TextPhase.OptionInfo(OPTIONS[4])
                        .setOptionResult((i)->{
                                AbstractCard uncommonCard = AbstractDungeon.srcUncommonCardPool.getRandomCard(true);
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(uncommonCard, Settings.WIDTH / 3.0F, Settings.HEIGHT / 2.0F));
                                uncommonCard = AbstractDungeon.srcUncommonCardPool.getRandomCard(true);
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(uncommonCard, Settings.WIDTH * 2.0F / 3.0F, Settings.HEIGHT / 2.0F));
                                transitionKey("end");
                        })
                )
                .addOption(new TextPhase.OptionInfo(OPTIONS[5])
                        .setOptionResult((i)->{
                                AbstractCard commonCard = AbstractDungeon.srcCommonCardPool.getRandomCard(true);
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(commonCard, Settings.WIDTH / 4.0F, Settings.HEIGHT / 2.0F));
                                commonCard = AbstractDungeon.srcCommonCardPool.getRandomCard(true);
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(commonCard, Settings.WIDTH * 2.0F / 4.0F, Settings.HEIGHT / 2.0F));
                                commonCard = AbstractDungeon.srcCommonCardPool.getRandomCard(true);
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(commonCard, Settings.WIDTH * 3.0F / 4.0F, Settings.HEIGHT / 2.0F));
                                transitionKey("end");
                        })
                )
        );

        registerPhase("end", new TextPhase(DESCRIPTIONS[3])
                .addOption(new TextPhase.OptionInfo(OPTIONS[1])
                        .setOptionResult((i)->{
                                openMap();
                        })
                )
        );

        transitionKey("start");

    }


}
