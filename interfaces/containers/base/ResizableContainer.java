package apsd.interfaces.containers.base;

import apsd.classes.utilities.Natural;

/** Interface: ReallocableContainer che è espandibile e riducibile. */
public interface ResizableContainer extends ReallocableContainer { 

  double THRESHOLD_FACTOR = 2.0; // Must be strictly greater than 1.

  // Expand

  default void Expand() 
  { 
      Expand(Natural.ONE); 
  }

  void Expand(Natural quantita);
  // Reduce
  default void Reduce() 
  { 
      Reduce(Natural.ONE); 
  }

  void Reduce(Natural quantita);
 

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  abstract Natural Size();
  

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  @Override
  default void Grow (Natural dim){
	  if(Capacity().ToLong()==Integer.MAX_VALUE) {
		  throw new ArithmeticException("overflow:capaciy cannot overcome ");
	  }
	  if (Size().ToLong() + dim.ToLong()>= Capacity().ToLong()) {
		  ReallocableContainer.super.Grow(dim);
	  }
  }
  
  

  @Override

   default void Shrink() {
    if ((long) (THRESHOLD_FACTOR * SHRINK_FACTOR * Size().ToLong()) <= Capacity().ToLong()) {
    	ReallocableContainer.super.Shrink();
    }
   }

}
