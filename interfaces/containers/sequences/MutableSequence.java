package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.MutableIterableContainer;


/** Interface: Sequence & MutableIterableContainer con supporto alla scrittura tramite posizione. */
public interface MutableSequence<Data> extends Sequence<Data> ,MutableIterableContainer<Data>{ // Must extend Sequence and MutableIterableContainer

	void SetAt(Data dat, Natural pos);

	

	    /**
	     * Sostituisce il valore in posizione 'pos' con 'newdat'
	     * restituendo il valore precedente.
	     */
	default Data GetNSetAt(Data newdat, Natural pos) {
	        // prelevo l'elemento attuale presente alla posizione
	        Data previous = GetAt(pos);

	        // eseguo l'aggiornamento
	        SetAt(newdat, pos);

	        // restituisco ciò che c'era prima
	        return previous;
	}

	    /**
	     * Imposta il primo elemento della sequenza a 'dat'.
	     */
	    default void SetFirst(Data dat) {
	        SetAt(dat, Natural.ZERO);
	    }

	    /**
	     * Sostituisce il primo elemento con 'newdat'
	     * restituendo il valore vecchio.
	     */
	    default Data GetNSetFirst(Data newdat) {
	        Data old = GetFirst();
	        SetFirst(newdat);
	        return old;
	    }

	    /**
	     * Imposta l'ultimo elemento della sequenza.
	     * Se la sequenza è vuota, modifica la posizione zero.
	     */
	    default void SetLast(Data dat) {

	        Natural target = IsEmpty()
	                ? Natural.ZERO
	                : Size().Decrement();

	        SetAt(dat, target);
	    }

	    /**
	     * Sostituisce l’ultimo elemento restituendo quello precedente.
	     */
	    default Data GetNSetLast(Data newdat) {

	        Data before = GetLast();
	        SetLast(newdat);
	        return before;
	    }
	    default void Swap(Natural pos1, Natural pos2) {
	        // Leggo i due elementi dalle rispettive posizioni
	        Data first = GetAt(pos1);
	        Data second = GetAt(pos2);

	        // Li riscrivo scambiando le posizioni
	        SetAt(second, pos1);
	        SetAt(first, pos2);
	    }


  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */
 MutableSequence<Data> SubSequence( Natural index1, Natural index2 );
  

}
