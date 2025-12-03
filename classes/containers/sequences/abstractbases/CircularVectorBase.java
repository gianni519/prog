package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Abstract (static) circular vector base implementation. */
abstract public class CircularVectorBase<Data> extends VectorBase<Data> { // Must extend VectorBase

   protected long start = 0L;

  // CircularVectorBase
   public CircularVectorBase() {
       super();
   }

   public CircularVectorBase(Natural insize) {
       super(insize);
   }

   public CircularVectorBase(TraversableContainer<Data> con) {
       super(con);
   }

   public CircularVectorBase(Data[] arr) {
       super(arr);
   }
   
   @Override
   protected void ArrayAlloc(Natural newsize) {
       super.ArrayAlloc(newsize);
       start = 0L;
   }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

   @Override
   public void Realloc(Natural newsize) {

       if (newsize == null) {
           throw new NullPointerException("Natural cannot be null");
       }

       Data[] vecOld = arr;
       long oldStart = start;

       long curSize = Size().ToLong();
       long reqSize = newsize.ToLong();
       long copyLen = (curSize < reqSize) ? curSize : reqSize;

       ArrayAlloc(newsize);

       for (long k = 0; k < copyLen; k++) {
           int dest = (int) k;
           int src = (int) ((oldStart + k) % vecOld.length);
           arr[dest] = vecOld[src];
       }

       // dopo la riallocazione il nuovo start rimane 0
       start = 0L;
   }
  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */
   @Override
   public Data GetAt(Natural pos) {
       long logical = ExcIfOutOfBound(pos);
       int idx = (int) ((start + logical) % arr.length);
       return arr[idx];
   }


  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */


   @Override
   public void SetAt(Data dat, Natural pos) {
       long logical = ExcIfOutOfBound(pos);
       int idx = (int) ((start + logical) % arr.length);
       arr[idx] = dat;
   }
  /* ************************************************************************ */
  /* Specific member functions of Vector                                      */
  /* ************************************************************************ */
   @Override
   public void ShiftLeft(Natural pos, Natural num) {

       long base = ExcIfOutOfBound(pos);
       long total = Size().ToLong();
       long amount = num.ToLong();

       long max = total - base;
       if (amount > max) {
           amount = max;
       }

       if (amount <= 0) {
           return;
       }

       long write = base;
       long limit = total;

       // sposta verso sinistra il blocco successivo
       for (long read = base + amount; read < limit; read++, write++) {

           int dst = (int) ((start + write) % arr.length);
           int src = (int) ((start + read) % arr.length);

           arr[dst] = arr[src];
       }

       // “svuota” la parte finale
       long clearFrom = total - amount;
       for (long k = clearFrom; k < total; k++) {
           int idx = (int) ((start + k) % arr.length);
           arr[idx] = null;
       }
   }

   @Override
   public void ShiftRight(Natural pos, Natural num) {

       long base = ExcIfOutOfBound(pos);
       long total = Size().ToLong();
       long amount = num.ToLong();

       long max = total - base;
       if (amount > max) {
           amount = max;
       }

       if (amount <= 0) {
           return;
       }

       long read = total - 1;
       long stop = base;

       // sposta verso destra partendo dal fondo
       for (long write = total - 1 + amount; read >= stop; read--, write--) {

           int dst = (int) ((start + write) % arr.length);
           int src = (int) ((start + read) % arr.length);

           arr[dst] = arr[src];
       }

       // “svuota” gli slot iniziali
       long clearTo = base + amount;
       for (long k = base; k < clearTo; k++) {
           int idx = (int) ((start + k) % arr.length);
           arr[idx] = null;
       }
   }
}
