
package tarea3.francomendoza;

public class Baraja {
    
    private Carta[] deck;
    
    private int cartasUsuario;

    public Baraja() {
        this(false);  
    }

    public Baraja(boolean includeJokers) {
        if (includeJokers)
            deck = new Carta[54];
        else
            deck = new Carta[52];
        int cardCt = 0;
        for ( int suit = 0; suit <= 3; suit++ ) {
            for ( int value = 1; value <= 13; value++ ) {
                deck[cardCt] = new Carta(value,suit);
                cardCt++;
            }
        }
        if (includeJokers) {
            deck[52] = new Carta(1,Carta.JOKER);
            deck[53] = new Carta(2,Carta.JOKER);
        }
        cartasUsuario = 0;
    }

    public void barajar() {
        for ( int i = deck.length-1; i > 0; i-- ) {
            int rand = (int)(Math.random()*(i+1));
            Carta temp = deck[i];
            deck[i] = deck[rand];
            deck[rand] = temp;
        }
        cartasUsuario = 0;
    }

    public int cartasIzquierda() {
        return deck.length - cartasUsuario;
    }

    public Carta cartaOferta() {
        if (cartasUsuario == deck.length)
            throw new IllegalStateException("No hay cartas en la baraja.");
        cartasUsuario++;
        return deck[cartasUsuario - 1];
    }

    public boolean tieneJoker() {
        return (deck.length == 54);
    }

} // FIN class Deck
   
