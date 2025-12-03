package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.bases.LLChainBase;
import apsd.classes.containers.collections.concretecollections.bases.LLNode;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.MutableSequence;

/**
 * Object: Concrete list implementation on linked-list.
 */
public class LLList<Data> extends LLChainBase<Data> implements List<Data> { // Must extend LLChainBase and implement List

    public LLList() {}

    public LLList(TraversableContainer<Data> con) {
        super(con);
    }

    protected LLList(long size, LLNode<Data> head, LLNode<Data> tail) {
        super(size, head, tail);
    }

    // NewChain
    @Override
    protected LLChainBase<Data> NewChain(long size, LLNode<Data> head, LLNode<Data> tail) {
        return new LLList<>(size, head, tail);
    }

    @Override
    public Data GetAt(Natural pos) {
        long idx = ExcIfOutOfBound(pos);
        ForwardIterator<Box<LLNode<Data>>> itr = FRefIterator();
        itr.Next(idx);
        return itr.GetCurrent().Get().Get();
    }

    /* ************************************************************************ */
    /* Override specific member functions from MutableIterableContainer         */
    /* ************************************************************************ */

  @Override
    public MutableForwardIterator<Data> FIterator() { return new ListFIterator(); }

    @Override
    public MutableBackwardIterator<Data> BIterator() { return new ListBIterator(); }

    /* ************************************************************************ */
    /* Override specific member functions from MutableSequence                  */
    /* ************************************************************************ */

    @Override
    /*public void SetAt(Data dat, Natural pos) {
        if (dat == null) return;
        List.super.SetAt(dat, pos);
    }*/

    public void SetAt(Data dat, Natural pos) {
        //if (dat == null) return;
        long idx = ExcIfOutOfBound(pos);
        ForwardIterator<Box<LLNode<Data>>> itr = FRefIterator();
        itr.Next(idx);
        itr.GetCurrent().Get().Set(dat);
    }

    @Override
    public void SetFirst(Data dat) {
        //if (dat == null) return;
        if (headref.IsNull()) throw new IndexOutOfBoundsException("First element does not exist!");
        headref.Get().Set(dat);
    }

    @Override
    public void SetLast(Data dat) {
        //if (dat == null) return;
        if (tailref.IsNull()) throw new IndexOutOfBoundsException("Last element does not exist!");
        tailref.Get().Set(dat);
    }

    @Override
    public MutableSequence<Data> SubSequence(Natural from, Natural to) {
        return (MutableSequence<Data>) super.SubSequence(from,to);
    }



    /* ************************************************************************ */
    /* Override specific member functions from InsertableAtSequence             */
    /* ************************************************************************ */

    @Override
    public void InsertAt(Data dat, Natural pos) {
        if (pos == null) throw new NullPointerException("Natural number cannot be null");
        long idx = pos.ToLong();
        long sizeVal = Size().ToLong();
        if (idx > sizeVal) throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + sizeVal + "!");

        if (idx == 0) {
            InsertFirst(dat);
        } else if (idx == sizeVal) {
            InsertLast(dat);
        } else {
            ForwardIterator<Box<LLNode<Data>>> itr = FRefIterator();
            itr.Next(idx - 1);
            LLNode<Data> prd = itr.GetCurrent().Get();
            LLNode<Data> newnode = new LLNode<>(dat, prd.GetNext().Get());
            prd.SetNext(newnode);
            size.Increment();
        }
    }
    /*public void InsertAt(Data dat, Natural pos) {
        //if (dat == null) return;
        if (pos == null) throw new NullPointerException("Natural number cannot be null");
        long idx= pos.ToLong();
        if (idx > Size().ToLong()) throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + Size().ToLong() + "!");
                if(idx == Size().ToLong()) {
                    InsertLast(dat);
        } else {
                    ForwardIterator<Box<LLNode<Data>>> itr = FRefIterator();
                    itr.Next(idx);
                    Box<LLNode<Data>> cur = itr.GetCurrent();
                    cur.Set(new LLNode<>(dat, cur.Get()));
                    size.Increment();
        }
    }*/

    @Override
    public void InsertFirst(Data dat) {
        //if(dat == null) return;
        LLNode<Data> newnode = new LLNode<>(dat, headref.Get());
        headref.Set(newnode);
        if(tailref.IsNull()) { tailref.Set(newnode); }
        size.Increment();
    }

    @Override
    public void InsertLast(Data dat) {
        if(dat == null) return;
        LLNode<Data> newnode = new LLNode<>(dat, null);
        if(tailref.IsNull()) {
            headref.Set(newnode);
        } else {
            tailref.Get().SetNext(newnode);
        }
        tailref.Set(newnode);
        size.Increment();
    }

}