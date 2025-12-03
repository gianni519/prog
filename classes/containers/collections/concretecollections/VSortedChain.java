package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.bases.VChainBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.SortedChain;
import apsd.interfaces.containers.sequences.DynVector;

/** Object: Concrete set implementation via (dynamic circular) vector. */
public class VSortedChain<Data extends Comparable<? super Data>> extends VChainBase<Data> implements SortedChain<Data> {

    public VSortedChain() { 
        super(); 
    }

    public VSortedChain(VSortedChain<Data> chn) { 
        super(chn); 
    }

    public VSortedChain(TraversableContainer<Data> con) {
        con.TraverseForward(dat -> { Insert(dat); return false; });
    }

    public VSortedChain(DynVector<Data> vec) { 
        super(vec); 
    }

    @Override
    protected VChainBase<Data> NewChain(DynVector<Data> vec) {
        return new VSortedChain<>(vec);
    }

    @Override
    public boolean Insert(Data dat) {
        if(dat == null) return false;
        
        Natural prd = SearchPredecessor(dat);
        Natural pos = (prd == null) ? Natural.ZERO : prd.Increment();
        vettore.InsertAt(dat, pos);
        return true;
    }

    @Override
    public boolean InsertIfAbsent(Data dat) {
        if (dat == null) return false;
        Natural prd = SearchPredecessor(dat);
        Natural pos = (prd == null) ? Natural.ZERO : prd.Increment();
        if (IsInBound(pos)) {
            Data elm = vettore.GetAt(pos);
            if (elm != null && elm.equals(dat)) {
                return false;
            }
        }
        vettore.InsertAt(dat, pos);
        return true;
    }

    @Override
    public void RemoveOccurrences(Data dat) {
        if (dat == null) return;
        Natural prd = SearchPredecessor(dat);
        long cur = (prd == null) ? 0 : prd.ToLong() + 1;
        long ini = cur;
        long size = vettore.Size().ToLong();
        while (cur < size) {
            Data elm = vettore.GetAt(Natural.Of(cur));
            if (elm != null && elm.compareTo(dat) == 0) {
                cur++;
            } else {
                break;
            }
        }
        long del = cur - ini;
        if (del > 0) {
            vettore.ShiftLeft(Natural.Of(ini), Natural.Of(del));
        }
    }
}