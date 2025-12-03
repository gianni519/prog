package apsd.interfaces.containers.iterators;

import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Predicate;

/** Interface: Iteratore all'indietro. */
public interface BackwardIterator<Data> extends Iterator<Data> 
{
    default void Prev() 
    { 
        DataNPrev(); 
    }

    // va indietro di un certo numero di 'passi'
    default void Prev(long passi) 
    {
        // uso un ciclo FOR per eseguire Prev() 'passi' volte
        for (long i = 0; i < passi; i++) 
        {
            Prev(); // chiamo il Prev() singolo
        }
    }

    default void Prev(Natural passiNatural) 
    { 
        // chiamo la versione long
        Prev(passiNatural.ToLong()); 
    }

    Data DataNPrev();

    default boolean ForEachBackward(Predicate<Data> predicatoDaApplicare) 
    {
        // controllo che il predicato esista
        if (predicatoDaApplicare == null)
        {
            return false;
        }

        // scorro all'indietro finché l'iteratore è valido
        while (this.IsValid()) 
        {
            // applico la funzione. Se ritorna true, ho finito
            if (predicatoDaApplicare.Apply(DataNPrev())) 
            { 
                return true; 
            }
        }
        
        // se il ciclo finisce, ritorno false
        return false;
    }
}