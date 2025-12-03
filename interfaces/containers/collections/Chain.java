package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.RemovableAtSequence;

public interface Chain<Data> extends RemovableAtSequence<Data>, Set<Data> {

    default boolean InsertIfAbsent(Data dat){
        return (!Exists(dat))? Insert(dat): false;
    }

    default void RemoveOccurrences (Data dat){
        Filter(elm-> !elm.equals(dat));
    }

    default Chain<Data> SubChain(Natural start, Natural end){
        return ( Chain<Data>) SubSequence(start,end);
    }

    @Override
    default Natural Search(Data elementoCercato){
        if(elementoCercato==null) return null;
        return RemovableAtSequence.super.Search(elementoCercato);
    }

}

