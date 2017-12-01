public class SetDriver {
    public static void main(String[] args) {
//        System.out.println("======== CARD  TESTER ========");
//        Card c1 = new Card(Card.Color.GREEN, Card.Shading.STRIPED, Card.Shape.DIAMOND, Card.Number.ONE);
//        Card c2 = new Card(Card.Color.RED, Card.Shading.OUTLINED, Card.Shape.OVAL, Card.Number.TWO);
//        Card c3 = new Card(Card.Color.PURPLE, Card.Shading.SOLID, Card.Shape.SQUIGGLE, Card.Number.THREE);
//        System.out.println(c1);
//        System.out.println(c2);
//        System.out.println(c3);
//        System.out.println("is set: " + Card.isSet(c1,c2,c3));
//
//
//        System.out.println("======== DECK  TESTER ========");
//        Deck d = new Deck();
//        System.out.println(d);
//
//        System.out.println("======== BOARD TESTER ========");
//        d.shuffle();
//        Board b = new Board(d);
//        System.out.println(b);
//        System.out.println("rows: " + b.numRows());
//        System.out.println("cols: " + b.numCols());
//        System.out.println(b.getBoardSquare(0,2));
//        b.add3(d);
//        System.out.println(b);
//        System.out.println("rows: " + b.numRows());
//        System.out.println("cols: " + b.numCols());
//
//        System.out.println("before: " + b.getCard(0,2));
//        System.out.println("cards left: " + d.cardsRemaining());
//        b.replaceCard(d.getTopCard(), 0, 2);
//        System.out.println("after:  " + b.getCard(0,2));
//        System.out.println("cards left: " + d.cardsRemaining());
//
//        System.out.println(b);

        Game g = new Game();
        System.out.println(g);

        BoardSquare[] aSet = g.findSet();
        for (BoardSquare bs : aSet) {
            System.out.println(bs);
        }
    }
}
