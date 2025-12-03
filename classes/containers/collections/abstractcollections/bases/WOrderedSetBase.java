package apsd.classes.containers.collections.abstractcollections.bases;

import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.OrderedSet;
import apsd.interfaces.containers.collections.SortedChain;

abstract public class WOrderedSetBase<Data extends Comparable<? super Data>, Chn extends SortedChain<Data>> extends WSetBase<Data, Chn> implements OrderedSet<Data> {

    protected WOrderedSetBase() {
        super();
    }

    protected WOrderedSetBase(Chn c) {
        super(c);
    }

    protected WOrderedSetBase(TraversableContainer<Data> con) {
        super(con);
    }

    protected WOrderedSetBase(Chn c, TraversableContainer<Data> con) {
        super(c, con);
    }

    @Override
    public boolean IsEqual(IterableContainer<Data> con) { 
        if (con == null) return false;
        return chn.IsEqual(con); }

    @Override
    public Data Min() { return chn.Min(); }

    @Override
    public Data Max() { return chn.Max(); }

    @Override
    public void RemoveMin() { chn.RemoveMin(); }

    @Override
    public void RemoveMax() { chn.RemoveMax(); }

    @Override
    public Data MinNRemove() { return chn.MinNRemove(); }

    @Override
    public Data MaxNRemove() { return chn.MaxNRemove(); }

    @Override
    public Data Predecessor(Data d) { return chn.Predecessor(d); }

    @Override
    public Data Successor(Data d) { return chn.Successor(d); }

    @Override
    public void RemovePredecessor(Data d) { chn.RemovePredecessor(d); }

    @Override
    public void RemoveSuccessor(Data d) { chn.RemoveSuccessor(d); }

    @Override
    public Data PredecessorNRemove(Data d) { return chn.PredecessorNRemove(d); }

    @Override
    public Data SuccessorNRemove(Data d) { return chn.SuccessorNRemove(d); }

}