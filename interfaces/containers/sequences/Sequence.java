package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.IterableContainer;


/** Interface: IterableContainer con supporto alla lettura e ricerca tramite posizione. */
public interface Sequence<Data>extends IterableContainer<Data> { 

	Data GetAt(Natural posizione);

    default Data GetFirst() 
    { 
        // ottiene il primo elemento (posizione 0)
        return GetAt(Natural.ZERO); 
    }

    default Data GetLast() 
    { 
        Natural posizioneDaPrendere;
        
        // se la sequenza è vuota, prendo l'elemento 0 
       
        if (IsEmpty()) 
        {
            posizioneDaPrendere = Natural.ZERO;
        } 
        // altrimenti prendo l'ultimo elemento (size - 1)
        else 
        {
            posizioneDaPrendere = Size().Decrement();
        }
        
        return GetAt(posizioneDaPrendere);
    }

    default Natural Search(Data elementoCercato) 
    {
        // uso un Box per l'indice, partendo da -1
        final Box<Long> indiceTrovato = new Box<>(-1L);

        // scorro la sequenza
        // se TraverseForward ritorna true, vuol dire che l'ho trovato
        if (TraverseForward(elementoCorrente -> {
            
            // tengo traccia dell'indice attuale (incremento prima del check)
            indiceTrovato.Set(indiceTrovato.Get() + 1);

            // logica di confronto (gestendo i null)
            boolean uguali = (elementoCorrente == null && elementoCercato == null) ||
                             (elementoCorrente != null && elementoCercato != null && elementoCorrente.equals(elementoCercato));
            
            // se 'uguali' è true, la lambda ritorna true e ferma la traversata
            return uguali;
        })) 
        {
            // l'ho trovato, ritorno l'indice
            return Natural.Of(indiceTrovato.Get());
        }

        // non l'ho trovato (TraverseForward ha ritornato false)
        return null;
    }

    default boolean IsInBound(Natural posizione) 
    {
        // controllo anti-null
        if (posizione == null) 
        {
            throw new NullPointerException("Natural number cannot be null");
        }

        long indiceLong = posizione.ToLong();
        return (indiceLong < Size().ToLong());
    }

    default long ExcIfOutOfBound(Natural posizione) 
    {
        if (posizione == null) 
        {
            throw new NullPointerException("Natural number cannot be null");
        }

        long indiceLong = posizione.ToLong();

        // controllo se è fuori dai limiti
        if (indiceLong >= Size().ToLong()) 
        {
            
            throw new IndexOutOfBoundsException("index out of bounds: " + indiceLong );
        }

        return indiceLong;
    }
     
    Sequence<Data> SubSequence(Natural from, Natural to);
    
    @Override
    default boolean  Exists(Data dat) {
    	return(Search(dat)!=null);
    }
}
