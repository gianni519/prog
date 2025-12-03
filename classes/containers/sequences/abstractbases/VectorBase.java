package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.MutableNatural;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.MutableSequence;
import apsd.interfaces.containers.sequences.Vector;

/**
 * Object: Abstract vector base implementation.
 */
abstract public class VectorBase<Data> implements Vector<Data> { // Must implement Vector

    protected Data[] arr = null;

    // VectorBase
    public VectorBase() {
        ArrayAlloc(Natural.ZERO);
    }

    public VectorBase(Natural inisize) {
        if (inisize == null) {
            throw new NullPointerException("Natural cannot be null!");
        }
        ArrayAlloc(inisize);
    }

    public VectorBase(TraversableContainer<Data> con) {
        if (con == null) {
            throw new NullPointerException("TraversableContainer cannot be null!");
        }
        ArrayAlloc(con.Size());
        final MutableNatural idx = new MutableNatural();
        con.TraverseForward(dat -> {
            SetAt(dat, Natural.Of(idx.GetLongNIncrement()));
            return false;
        });
    }

    protected VectorBase(Data[] arr) {
        this.arr = arr;
    }

    // NewVector
    abstract protected VectorBase<Data> NewVector(Data[] arr);

    @SuppressWarnings("unchecked")
    protected void ArrayAlloc(Natural newsize) {
        long size = newsize.ToLong();
        if (size >= Integer.MAX_VALUE) {
            throw new ArithmeticException("Overflow: size cannot exceed Integer.MAX_VALUE!");
        }
        arr = (Data[]) new Object[(int) size];
    }

    /* ************************************************************************ */
    /* Override specific member functions from ClearableContainer               */
    /* ************************************************************************ */

    @Override
    public void Clear() {
        ArrayAlloc(Natural.ZERO);
    }

    /* ************************************************************************ */
    /* Override specific member functions from ResizableContainer               */
    /* ************************************************************************ */

    @Override
    public Natural Capacity() {
        return Natural.Of(arr.length);
    }

    /* ************************************************************************ */
    /* Override specific member functions from IterableContainer                */
    /* ************************************************************************ */

    protected class VectorFIterator implements MutableForwardIterator<Data> {

        protected long cur = 0L;

        @Override
        public boolean IsValid () {
        return (cur < Size().ToLong());
        }

        @Override
        public void Reset () {
        cur = 0L;
        }

        @Override
        public Data GetCurrent () {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
        return GetAt(Natural.Of(cur));
        }

        @Override
        public void SetCurrent (Data dat){
        if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
        SetAt(dat, Natural.Of(cur));
        }

        @Override
        public void Next () {
        if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
        cur++;
        }

        @Override
        public Data DataNNext() {
            if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
            return GetAt(Natural.Of(cur++));
        }


    }
    
    @Override
    public MutableForwardIterator<Data> FIterator() { return new VectorFIterator(); }

    protected class VectorBIterator implements MutableBackwardIterator<Data> {

        protected long cur = Size().ToLong() - 1;

        @Override
        public boolean IsValid () {
            return (cur >= 0);
        }

        @Override
        public void Reset () {
            cur = Size().ToLong() - 1;
        }

        @Override
        public Data GetCurrent () {
            if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
            return GetAt(Natural.Of(cur));
        }

        @Override
        public void SetCurrent (Data dat){
            if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
            SetAt(dat, Natural.Of(cur));
        }

        @Override
        public void Prev () {
            if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
            cur--;
        }

        @Override
        public Data DataNPrev() {
            if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
            return GetAt(Natural.Of(cur--));
        }


    }

    @Override
    public MutableBackwardIterator<Data> BIterator() { return new VectorBIterator(); }

    /* ************************************************************************ */
    /* Override specific member functions from Sequence                         */
    /* ************************************************************************ */

    @Override
    abstract public Data GetAt(Natural pos);
    
    @Override
    @SuppressWarnings("unchecked")
    public MutableSequence<Data> SubSequence(Natural from, Natural to) {
        if (from == null || to == null) {
            throw new NullPointerException("Natural cannot be null!");
        }
        long valfrom=from.ToLong();
        long valto=to.ToLong();
        long size = Size().ToLong();

        if (valfrom < 0 || valfrom >= size) {
            throw new IndexOutOfBoundsException("From index out of bounds: " + valfrom);
        }
        if (valto < 0 || valto >= size) {
            throw new IndexOutOfBoundsException("To index out of bounds: " + valto);
        }
        if (valfrom > valto) {
            throw new IllegalArgumentException("From index cannot be greater than to index!");
        }

        Data[] newarr= (Data[]) new Object[(int) (valto - valfrom + 1)];
        for (int rdr = (int) valfrom, wrt=0; rdr <= valto; rdr++, wrt++) {
            newarr[wrt] = GetAt(Natural.Of(rdr));
        }
        return NewVector(newarr);
    }
    

    /* ************************************************************************ */
    /* Override specific member functions from MutableSequence                  */
    /* ************************************************************************ */

    @Override
    abstract public void SetAt(Data dat, Natural pos);

}