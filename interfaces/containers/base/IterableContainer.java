package apsd.interfaces.containers.base;

import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.traits.Predicate;

/** Interface: TraversableContainer con supporto all'iterazione. */
public interface IterableContainer<Data>  extends TraversableContainer<Data> { 

  // FIterator
	ForwardIterator<Data> FIterator();
 // BIterator
	BackwardIterator<Data> BIterator();
	
  
  // IsEqual
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

  /* ************************************************************************ */
  /* Override specific member functions from TraversableContainer             */
  /* ************************************************************************ */

  @Override
  default boolean TraverseForward(Predicate<Data>fun) {return FIterator().ForEachForward(fun);}
  default boolean TraverseBackward(Predicate<Data>fun) {return BIterator().ForEachBackward(fun);}
  
  
}
