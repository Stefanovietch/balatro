package balatro.powers;

import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static balatro.balatroMod.makeID;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.aiRng;

public class ToDoListPower extends BasePower{
    public static final String POWER_ID = makeID("ToDoList");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private AbstractCard.CardType cardtype;
    private final Random typeRandom = new Random();
    List<AbstractCard.CardType> typeList =
            Arrays.asList(AbstractCard.CardType.ATTACK, AbstractCard.CardType.SKILL, AbstractCard.CardType.POWER);


    public ToDoListPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        cardtype = typeList.get(typeRandom.random(2));
    }

    public void updateDescription() {
        String typeString;
        if (cardtype == AbstractCard.CardType.ATTACK) {
            typeString = "an #yAttack";
        } else if (cardtype == AbstractCard.CardType.SKILL) {
            typeString = "a #ySkill";
        } else {
            typeString = "a #yPower";
        }
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + typeString + DESCRIPTIONS[2];
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if(card.type == cardtype) {
            addToBot(new GainGoldAction(this.amount));
        }
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        cardtype = typeList.get(typeRandom.random(2));
        updateDescription();
    }
}
