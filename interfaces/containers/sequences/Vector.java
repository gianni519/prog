package apsd.interfaces.containers.sequences;

 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.ReallocableContainer;

public interface Vector<Data> extends ReallocableContainer , MutableSequence<Data> { // Must extend ReallocableContainer and MutableSequence

    default void ShiftLeft(Natural pos) {
        ShiftLeft(pos, Natural.ONE);
    }

    default void ShiftLeft(Natural pos, Natural num) {

        long idx = ExcIfOutOfBound(pos);
        long amount = num.ToLong();
        long limit = Size().ToLong();

        long len = limit - idx; 
        if (len > amount) {
            len = limit - idx;
        }

        if (len > 0) {

            long wStart = idx;
            long wEnd = idx + len;

            for (long r = wStart + amount; r < wEnd; r++) {

                Natural src = Natural.Of(r);
                Natural dst = Natural.Of(r - amount);

                SetAt(GetAt(src), dst);
            }

            for (long f = wEnd - amount; f < wEnd; f++) {
                Natural clearPos = Natural.Of(f);
                SetAt(null, clearPos);
            }
        }
    }

    default void ShiftFirstLeft() {
        ShiftLeft(Natural.ZERO);
    }

    default void ShiftLastLeft() {

        Natural last;

        if (IsEmpty()) {
            last = Natural.ZERO;
        } else {
            last = Size().Decrement();
        }

        ShiftLeft(last);
    }


    default void ShiftRight(Natural pos) {
        ShiftRight(pos, Natural.ONE);
    }

    default void ShiftRight(Natural pos, Natural num) {

        long idx = ExcIfOutOfBound(pos);
        long amount = num.ToLong();
        long limit = Size().ToLong();

        long len = limit - idx;
        if (len > amount) {
            len = limit - idx;
        }

        if (len > 0) {

            long wStart = limit - 1;
            long wEnd = idx;

            for (long r = wStart; r >= wEnd + amount; r--) {

                Natural src = Natural.Of(r - amount);
                Natural dst = Natural.Of(r);

                SetAt(GetAt(src), dst);
            }

            for (long f = wEnd; f < wEnd + amount; f++) {

                Natural clearPos = Natural.Of(f);
                SetAt(null, clearPos);
            }
        }
    }

    default void ShiftFirstRight() {
        ShiftRight(Natural.ZERO);
    }

    default void ShiftLastRight() {

        Natural last;

        if (IsEmpty()) {
            last = Natural.ZERO;
        } else {
            last = Size().Decrement();
        }

        ShiftRight(last);
    }


    default Vector<Data> SubVector(Natural from, Natural to) {
        return (Vector<Data>) SubSequence(from, to);
    }
  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

     @Override default Natural Size() {
         return Capacity();
     }
}
