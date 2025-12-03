package apsd.classes.containers.collections.abstractcollections;

 import apsd.classes.containers.collections.abstractcollections.bases.WOrderedSetBase;
 import apsd.classes.containers.collections.concretecollections.VSortedChain;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.collections.SortedChain;

/** Object: Wrapper ordered set implementation via ordered chain. */
public class WOrderedSet<Data extends Comparable<? super Data>> extends WOrderedSetBase<Data, SortedChain<Data>> { // Must extend WOrderedSetBase

   public WOrderedSet() { super(); }

   public WOrderedSet(SortedChain<Data> chn) { super(chn); }

   public WOrderedSet(TraversableContainer<Data> con) { super(con); }

   public WOrderedSet(SortedChain<Data> chn, TraversableContainer<Data> con) {
       super(chn, con);
   }

    @Override
    protected void ChainAlloc() {
        chn = new VSortedChain<>();
    }
}