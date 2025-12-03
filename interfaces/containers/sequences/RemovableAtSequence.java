package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;

/** Interface: Sequence con supporto alla rimozione di un dato tramite posizione. */
public interface RemovableAtSequence<Data> extends Sequence<Data> { // Must extend Sequence

	default void RemoveAt(Natural pos) {
	    if (IsInBound(pos)) {
	        AtNRemove(pos);
	    }
	}
	
    Data AtNRemove(Natural pos);
    
    
	default void RemoveFirst() {
        if (IsEmpty()) {
            throw new IndexOutOfBoundsException("Empty sequence");
        }
        RemoveAt(Natural.ZERO);
    }
	default Data FirstNRemove() {
	    return AtNRemove(Natural.ZERO);
	}

	default void RemoveLast() {
    if (IsEmpty()) {
            throw new IndexOutOfBoundsException("Empty sequence");
        }
	    Natural ultimo;

	    if (IsEmpty()) {
	        ultimo = Natural.ZERO;
	    } else {
	        ultimo = Size().Decrement();
	    }

	    RemoveAt(ultimo);
	}

	default Data LastNRemove() {

	    Natural ultimo;

	    if (IsEmpty()) {
	        ultimo = Natural.ZERO;
	    } else {
	        ultimo = Size().Decrement();
	    }

	    return AtNRemove(ultimo);
	}

}
