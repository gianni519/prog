package apsd.classes.containers.collections.concretecollections;

import apsd.interfaces.containers.sequences.DynVector;
import apsd.classes.containers.collections.concretecollections.bases.VChainBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.MutableSequence;

/** Object: Concrete list implementation on (dynamic circular) vector. */
public class VList<Data> extends VChainBase<Data> implements List<Data> {

    public VList() { 
        super(); 
    }

    public VList(TraversableContainer<Data> con) { 
        super(con); 
    }

    public VList(DynVector<Data> vec) { 
        super(vec); 
    }

    @Override
    protected VChainBase<Data> NewChain(DynVector<Data> vec) {
        return new VList<>(vec);
    }

    @Override
    public MutableForwardIterator<Data> FIterator() { 
        return vettore.FIterator();
    }

    @Override
    public MutableBackwardIterator<Data> BIterator() { 
        return vettore.BIterator();
    }

    @Override
    public void SetAt(Data dat, Natural pos) {
        vettore.SetAt(dat, pos);
    }

    @Override
    public MutableSequence<Data> SubSequence(Natural from, Natural to) { 
        return (MutableSequence<Data>) super.SubSequence(from, to);
    }

    @Override
    public void InsertAt(Data dat, Natural pos) {
        vettore.InsertAt(dat, pos);
    }
}