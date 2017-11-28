
package tarea3.francomendoza;

    
public class BlackjackMano extends Mano {

    public int getBlackjackValor() {

        int val;     
        boolean ace;  
        int cartas;    

        val = 0;
        ace = false;
        cartas = getCarraCortada(); 

        for ( int i = 0;  i < cartas;  i++ ) {
            Carta card;    
            int cardVal; 
            card = getCarta(i);
            cardVal = card.getValor();  
            if (cardVal > 10) {
                cardVal = 10;   
            }
            if (cardVal == 1) {
                ace = true;    
            }
            val = val + cardVal;
        }

        if ( ace == true  &&  val + 10 <= 21 )
            val = val + 10;

        return val;

    }  // Fin getBlackjackValue()

} // Fin class BlackjackHand