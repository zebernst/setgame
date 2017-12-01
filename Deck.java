import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private ArrayList<Card> deck;

    /**
     * Constructor
     */
    public Deck() {
        deck = new ArrayList<>(81);
        for (Card.Color color : Card.Color.values())
            for (Card.Shading shading : Card.Shading.values())
                for (Card.Shape shape : Card.Shape.values())
                    for (Card.Number number : Card.Number.values())
                        deck.add(new Card(color, shading, shape, number));

        this.shuffle();
    }

    /**
     * Determine if this Deck is empty.
     * @return true if there are no more Cards in this Deck, false otherwise.
     */
    public boolean isEmpty() {
        return (deck.size() == 0);
    }

    /**
     * @return the number of Cards left in this Deck.
     */
    public int cardsRemaining() {
        return deck.size();
    }

    /**
     * Get the Card on top of the Deck.
     * @return the reference to the topmost Card.
     */
    public Card getTopCard() {
        return deck.remove(deck.size() - 1);
    }

    /**
     * Shuffle the Cards in this Deck.
     */
    public void shuffle() {
        // TODO: review code and adjust for my vars
        int randNum;
        Card temp;
        Random r = new Random();
        for (int i = 0; i < deck.size(); i++)
        {
            randNum = r.nextInt(deck.size());
            temp = deck.get(i);
            deck.set(i,deck.get(randNum));
            deck.set(randNum,temp);
        }
    }

    /**
     * @return a string representation of this Deck.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (Card c : deck) {
            sb.append("\t");
            sb.append(c.toString());
            sb.append(",\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
