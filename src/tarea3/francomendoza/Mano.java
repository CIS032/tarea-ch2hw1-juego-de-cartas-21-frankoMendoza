
package tarea3.francomendoza;

import java.util.ArrayList;

public class Mano {
    
    private ArrayList<Carta> mano;   

    public Mano() {
        mano = new ArrayList<Carta>();
    }

    public void limpiar() {
        mano.clear();
    }

    public void añadirCarta(Carta c) {
        if (c == null)
            throw new NullPointerException("No puede agregar una carta nula a la mano.");
        mano.add(c);
    }

    public void removerCarta(Carta c) {
        mano.remove(c);
    }

    public void removerCrta(int posicion) {
        if (posicion < 0 || posicion >= mano.size())
            throw new IllegalArgumentException("La posición no existe en la mano: "
                    + posicion);
        mano.remove(posicion);
    }

    public int getCarraCortada() {
        return mano.size();
    }

    public Carta getCarta(int position) {
        if (position < 0 || position >= mano.size())
            throw new IllegalArgumentException("La posición no existe en la mano: "
                    + position);
        return mano.get(position);
    }

    public void ordenarPorConjunto() {
        ArrayList<Carta> newHand = new ArrayList<Carta>();
        while (mano.size() > 0) {
            int pos = 0;  
            Carta c = mano.get(0); 
            for (int i = 1; i < mano.size(); i++) {
                Carta c1 = mano.get(i);
                if ( c1.getAcomodar()< c.getAcomodar()||
                        (c1.getAcomodar()== c.getAcomodar() && c1.getValor()< c.getValor()) ) {
                    pos = i;
                    c = c1;
                }
            }
            mano.remove(pos);
            newHand.add(c);
        }
        mano = newHand;
    }

    public void ordenarPorValor() {
        ArrayList<Carta> newHand = new ArrayList<Carta>();
        while (mano.size() > 0) {
            int pos = 0;  
            Carta c = mano.get(0); 
            for (int i = 1; i < mano.size(); i++) {
                Carta c1 = mano.get(i);
                if ( c1.getValor() < c.getValor()||
                        (c1.getValor() == c.getValor() && c1.getAcomodar() < c.getAcomodar()) ) {
                    pos = i;
                    c = c1;
                }
            }
            mano.remove(pos);
            newHand.add(c);
        }
        mano = newHand;
    }

}
