package apsd.interfaces.containers.iterators;

import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Predicate;

/** Interface: Iteratore in avanti. */
public interface ForwardIterator<Data> extends Iterator<Data> 
{
    default void Next() 
    { 
        DataNNext(); 
    }

    // va avanti di un certo numero di 'passi'
    default void Next(long numeroPassi) 
    {
        // uso un ciclo FOR standard per eseguire Next() 'passi' volte
        for (long i = 0; i < numeroPassi; i++) 
        {
            Next(); // chiamo il Next() singolo
        }
    }

    default void Next(Natural passiNatural) 
    { 
        // chiamo la versione long
        Next(passiNatural.ToLong()); 
    }

    Data DataNNext();

    default boolean ForEachForward(Predicate<Data> predicatoDaApplicare) 
    {
        // se il predicato non esiste, esco subito
        if (predicatoDaApplicare == null)
        {
            return false;
        }

        // scorro in avanti finché l'iteratore è valido
        while (this.IsValid()) 
        {
            // applico la funzione. Se ritorna true, ho trovato ciò che cercavo
            if (predicatoDaApplicare.Apply(DataNNext())) 
            { 
                return true; 
            }
        }
        
        // se il ciclo finisce (o non è mai partito), ritorno false
        return false;
    }
}