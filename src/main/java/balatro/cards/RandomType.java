package balatro.cards;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import static balatro.balatroMod.makeID;

public interface RandomType {
    UIStrings randomTypeString = CardCrawlGame.languagePack.getUIString(makeID("randomType"));
    String attackString = randomTypeString.TEXT[0];
    String skillString = randomTypeString.TEXT[1];
    String powerString = randomTypeString.TEXT[2];
    void setRandomType();
}
