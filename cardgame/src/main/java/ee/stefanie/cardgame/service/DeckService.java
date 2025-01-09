package ee.stefanie.cardgame.service;

import ee.stefanie.cardgame.entity.Card;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class DeckService {
    private List<Card> deck;

    public DeckService() {
        resetDeck();
    }

    public void resetDeck() {
        deck = new ArrayList<>(Arrays.asList(Card.values()));
        Collections.shuffle(deck);
    }

    public Card drawCard() {
        if (deck.isEmpty()) {
            resetDeck();
        }
        return deck.remove(0);
    }
}
