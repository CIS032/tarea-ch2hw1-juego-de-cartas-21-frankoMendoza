package tarea3.francomendoza;

public class Blackjack {

    public static void main(String[] args)  {

        int dinero;          // La cantidad de dinero que el usuario tiene.
        int apuesta;            // La cantidad de dinero que el usuario apuesta en el juego.
        boolean usuarioGanador;   // Si el usuario gano el juego?

        System.out.println("Bienvenido al juego Blacjack.");
        System.out.println();

        dinero = 100;  // El usuario empieza con $100.

        while (true) {
            System.out.println("Tu tienes: $ " + dinero + " dolares.");
            do {
                System.out.println("Cuántos dolares quieres apostar?  (Enter 0 para terminar)");
                
                apuesta = TextIO.getlnInt();
                if (apuesta < 0 || apuesta > dinero) {
                    System.out.println("Tu respuesta debe ser de entre 0 y " + dinero + '.');
                }
            } while (apuesta < 0 || apuesta > dinero);
            if (apuesta == 0) {
                break;
            }
            usuarioGanador = playBlackjack();
            if (usuarioGanador) {
                dinero = dinero + apuesta;
            } else {
                dinero = dinero - apuesta;
            }
            System.out.println();
            if (dinero == 0) {
                System.out.println("Parece que te has quedado sin dinero!");
                break;
            }
        }

        System.out.println();
        System.out.println("Tu tienes $" + dinero + '.');
        

    } // fin main()

    /**
     * Permita que el usuario juegue un juego de Blackjack, con la computadora como distribuidor.
     *
     * @devuelve verdadero si el usuario gana el juego, falso si el usuario pierde.
     */
    static boolean playBlackjack() {

        Baraja deck;                  // Una baraja de cartas. Una nueva baraja para cada juego.
        BlackjackMano manoDistribuidor;   // La mano del crupier.
        BlackjackMano manoUsuario;     // La mano del usuario.

        deck = new Baraja();
        manoDistribuidor = new BlackjackMano();
        manoUsuario = new BlackjackMano();

        /*  Baraja el mazo, reparte dos cartas a cada jugador. . */
        deck.barajar();
        manoDistribuidor.añadirCarta(deck.cartaOferta());
        manoDistribuidor.añadirCarta(deck.cartaOferta());
        manoUsuario.añadirCarta(deck.cartaOferta());
        manoUsuario.añadirCarta(deck.cartaOferta());

        System.out.println();
        System.out.println();

        /* Verifica si uno de los jugadores tiene Blackjack (dos cartas que suman un total de 21).
         El jugador con Blackjack gana el juego. El distribuidor gana lazos..
         */
        if (manoDistribuidor.getBlackjackValor() == 21) {
            System.out.println("Distribuidor tiene " + manoDistribuidor.getCarta(0)
                    + " y la " + manoDistribuidor.getCarta(1) + ".");
            System.out.println("Usuario tiene la " + manoUsuario.getCarta(0)
                    + " y la " + manoUsuario.getCarta(1) + ".");
            System.out.println();
            System.out.println("Distribuidor tiene Blackjack.  Distribuidor gana.");
            return false;
        }

        if (manoUsuario.getBlackjackValor() == 21) {
            System.out.println("Distribuidor tiene la " + manoDistribuidor.getCarta(0)
                    + " y la " + manoDistribuidor.getCarta(1) + ".");
            System.out.println("Usuario tiene la " + manoUsuario.getCarta(0)
                    + " y la " + manoUsuario.getCarta(1) + ".");
            System.out.println();
            System.out.println("Tienes Blackjack.  Ganaste.");
            return true;
        }

        /*  Si ninguno de los jugadores tiene Blackjack, juega el juego. Primero el usuario
          tiene la oportunidad de robar cartas (es decir, de "golpear"). El bucle while termina
          cuando el usuario elige "Stand". Si el usuario supera los 21,
          el usuario pierde inmediatamente.
         */
        while (true) {

            /* Mostrar tarjetas de usuario, y dejar que el usuario decida golpear o pararse. */
            System.out.println();
            System.out.println();
            System.out.println("Tus cartas son:");
            for (int i = 0; i < manoUsuario.getCarraCortada(); i++) {
                System.out.println("    " + manoUsuario.getCarta(i));
            }
            System.out.println("Tu total es " + manoUsuario.getBlackjackValor());
            System.out.println();
            System.out.println("Distribuidor muestra " + manoDistribuidor.getCarta(0));
            System.out.println();
            System.out.print("Golpear (H) o estar (S)? ");
            char userAction;  // Usuario responde, 'H' o 'S'.
            do {
                userAction = Character.toUpperCase(TextIO.getlnChar());
                if (userAction != 'H' && userAction != 'S') {
                    System.out.print("Por favor responda H o S:  ");
                }
            } while (userAction != 'H' && userAction != 'S');

            /* Si el usuario tiene éxito, el usuario obtiene una tarjeta. Si el usuario está parado,
              el bucle termina (y es el turno del crupier de robar cartas).
             */
            if (userAction == 'S') {
                // Extremo del bucle; el usuario no ha tomado cartas.
                break;
            } else {  // La acción del usuario es 'H'.  Dar al usuario una carta.  
                // Si el usuario va sobre los 21, el usuario pierde.
                Carta newCard = deck.cartaOferta();
                manoUsuario.añadirCarta(newCard);
                System.out.println();
                System.out.println("Usuario golpea.");
                System.out.println("Tus cartas son las " + newCard);
                System.out.println("Tu total ahora es " + manoUsuario.getBlackjackValor());
                if (manoUsuario.getBlackjackValor() > 21) {
                    System.out.println();
                    System.out.println("Tu estas quebredo por ir mas de 21.  Peridste.");
                    System.out.println("La otra carta del distribuidor era "
                            + manoDistribuidor.getCarta(1));
                    return false;
                }
            }

        } // fin while loop

        /* Si llegamos a este punto, el usuario tiene Stood con 21 o menos. Ahora es
         la oportunidad del distribuidor para dibujar. El distribuidor roba cartas hasta que el crupier
         el total es> 16. Si el crupier supera los 21, el crupier pierde.
         */
        System.out.println();
        System.out.println("Usuario empieza.");
        System.out.println("La cartad del distribuidor era ");
        System.out.println("    " + manoDistribuidor.getCarta(0));
        System.out.println("    " + manoDistribuidor.getCarta(1));
        while (manoDistribuidor.getBlackjackValor() <= 16) {
            Baraja a = new Baraja();
            Carta newCard = a.cartaOferta();
            System.out.println("El distribuidor golpea y obtiene " + newCard);
            manoDistribuidor.añadirCarta(newCard);
            if (manoDistribuidor.getBlackjackValor() > 21) {
                System.out.println();
                System.out.println("El distribuidor se detiene por ir mas de 21.  Ganaste.");
                return true;
            }
        }
        System.out.println("Total del distribuidor " + manoDistribuidor.getBlackjackValor());

        System.out.println();
        if (manoDistribuidor.getBlackjackValor() == manoUsuario.getBlackjackValor()) {
            System.out.println("El distribuidor gana por empate.  Perdiste.");
            return false;
        } else if (manoDistribuidor.getBlackjackValor() > manoUsuario.getBlackjackValor()) {
            System.out.println("Gana el distribuidor, " + manoDistribuidor.getBlackjackValor()
                    + " puntos a " + manoUsuario.getBlackjackValor() + ".");
            return false;
        } else {
            System.out.println("Ganas, " + manoUsuario.getBlackjackValor()
                    + " puntos a " + manoDistribuidor.getBlackjackValor() + ".");
            return true;
        }

    }  // fin playBlackjack()
    
   

} // fin class Blackjack
