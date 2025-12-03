package apsd.interfaces.containers.collections;

import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.iterators.ForwardIterator;

public interface Set<Data> extends Collection<Data> {

    default void Union(Set<Data> set){
        set.TraverseForward(dat->{Insert(dat);return false;});
    }

    default void Difference(Set<Data> set){
        set.TraverseForward(dat->{Remove(dat);return false;});
    }

    default void Intersection(Set<Data> set){
        Filter(dat->set.Exists(dat));
    }
    
    default boolean IsEqual(IterableContainer<Data>conn) {
        if(conn == this) return true;
        ForwardIterator<Data> itr1= this.FIterator();
        ForwardIterator<Data> itr2= conn.FIterator();
        while(itr1.IsValid()&& itr2.IsValid()) {
            if(!itr1.GetCurrent().equals(itr2.GetCurrent())) return false;
            itr1.Next();
            itr2.Next();
        }
        return !(itr1.IsValid() || itr2.IsValid());
    }

}