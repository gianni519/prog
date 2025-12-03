package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.SortedIterableContainer;

/** Interface: Sequence & SortedIterableContainer. */
public interface SortedSequence<Data extends Comparable<? super Data>> extends Sequence<Data> ,SortedIterableContainer <Data>{ // Must extend Sequence and SortedIterableContainer

  /* ************************************************************************ */
  /* Override specific member functions from MembershipContainer              */
  /* ************************************************************************ */

 @Override
	 default boolean Exists(Data dat) {
	     return Search(dat) != null;
	 }
	


  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */
@Override
default Natural Search(Data dat) {
    if (dat == null) return null;

    long start = 0;
    long end = Size().ToLong() - 1; // ultimo indice valido

    while (start <= end) {
        long middle = (start + end) / 2;
        Data current = GetAt(Natural.Of(middle));
        int comparison = current.compareTo(dat);

        if (comparison == 0) {
            return Natural.Of(middle); // trovato
        } else if (comparison < 0) {
            start = middle + 1; // a destra
        } else {
            end = middle - 1;   // a sinistra
        }
    }

    return null; // non trovato
}

}
