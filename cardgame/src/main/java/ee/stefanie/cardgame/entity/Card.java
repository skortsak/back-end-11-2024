package ee.stefanie.cardgame.entity;

import lombok.Getter;

@Getter
public enum Card {
    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),
    SEVEN(7), EIGHT(8), NINE(9), TEN(10),
    JACK(10), QUEEN(10), KING(10), ACE(10);

    private final int value;

    Card(int value) {
        this.value = value;
    }

}
