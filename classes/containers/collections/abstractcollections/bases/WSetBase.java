package apsd.classes.containers.collections.abstractcollections.bases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.collections.Set;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.traits.Predicate;

abstract public class WSetBase<Data, Chn extends Chain<Data>> implements Set<Data> {

    protected Chn chn;

    protected WSetBase() {
        ChainAlloc();
    }

    protected WSetBase(Chn chain) {
        chn = chain;
    }

    protected WSetBase(TraversableContainer<Data> container) {
        ChainAlloc();
        container.TraverseForward(dat -> { Insert(dat); return true; });
    }

    protected WSetBase(Chn chain, TraversableContainer<Data> container) {
        chn = chain;
        container.TraverseForward(dat -> { Insert(dat); return true; });
    }

    protected abstract void ChainAlloc();

    @Override
    public Natural Size() {
        return chn.Size();
    }

    @Override
    public void Clear() {
        chn.Clear();
    }

    @Override
    public boolean Insert(Data dat) {
        return chn.InsertIfAbsent(dat);
    }

    @Override
    public boolean Remove(Data dat) {
        return chn.Remove(dat);
    }

    @Override
    public ForwardIterator<Data> FIterator() {
        return chn.FIterator();
    }

    @Override
    public BackwardIterator<Data> BIterator() {
        return chn.BIterator();
    }

    @Override
    public boolean Filter(Predicate<Data> fun) {
        return chn.Filter(fun);
    }

    @Override
    public void Union(Set<Data> set) {
        set.TraverseForward(dat -> { Insert(dat); return false; });
    }

    @Override
    public void Difference(Set<Data> set) {
        if (set == this) {
            Clear();
            return;
        }
        set.TraverseForward(dat -> { Remove(dat); return false; });
    }

    @Override
    public void Intersection(Set<Data> set) {
        Filter(dat -> set.Exists(dat));
    }

}