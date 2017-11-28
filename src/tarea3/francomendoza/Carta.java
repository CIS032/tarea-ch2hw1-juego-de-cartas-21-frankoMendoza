
package tarea3.francomendoza;

public class Carta {
    
    public final static int ESPADAS = 0;   
    public final static int CORAZONES = 1;
    public final static int DIAMANTES = 2;
    public final static int TREBOLES = 3;
    public final static int JOKER = 4;

    public final static int As = 1;     
    public final static int J = 11;  
    public final static int Q = 12;   
    public final static int K = 13;

    private final int acomodar; 

    private final int valor;

    public Carta() {
        acomodar = JOKER;
        valor = 1;
    }

    public Carta(int elValor, int elConjunto) {
        if (elConjunto != ESPADAS && elConjunto != CORAZONES && elConjunto != DIAMANTES && 
                elConjunto != TREBOLES && elConjunto != JOKER)
            throw new IllegalArgumentException("Traje de naipes ilegal");
        if (elConjunto != JOKER && (elValor < 1 || elValor > 13))
            throw new IllegalArgumentException("Valor de carta ilegal");
        valor = elValor;
        acomodar = elConjunto;
    }

    public int getAcomodar() {
        return acomodar;
    }

    public int getValor() {
        return valor;
    }

    public String getAcomodarComoString() {
        switch ( acomodar ) {
        case ESPADAS:   return "Espadas";
        case CORAZONES:   return "Corazones";
        case DIAMANTES: return "Diamantes";
        case TREBOLES:    return "Treboles";
        default:       return "Joker";
        }
    }

    public String getValorComoString() {
        if (acomodar == JOKER)
            return "" + valor;
        else {
            switch ( valor ) {
            case 1:   return "As";
            case 2:   return "2";
            case 3:   return "3";
            case 4:   return "4";
            case 5:   return "5";
            case 6:   return "6";
            case 7:   return "7";
            case 8:   return "8";
            case 9:   return "9";
            case 10:  return "10";
            case 11:  return "Jack";
            case 12:  return "Reina";
            default:  return "Rey";
            }
        }
    }

    public String toString() {
        if (acomodar == JOKER) {
            if (valor == 1)
                return "Joker";
            else
                return "Joker #" + valor;
        }
        else
            return getValorComoString() + " de " + getAcomodarComoString();
    }
} // fin class Card
 
