package apsd.interfaces.containers.base;

import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Reallocable;

/** Interface: ClearableContainer che è anche Reallocable. */
public interface ReallocableContainer extends ClearableContainer, Reallocable { 

  double GROW_FACTOR = 2.0; // Must be strictly greater than 1.
  double SHRINK_FACTOR = 2.0; // Must be strictly greater than 1.

  // Capacity
 Natural Capacity();
  // Grow
 default void Grow () { Grow(Natural.ZERO); }
 default void Grow(Natural dim) {Realloc (Natural.Of((long) ((Size().ToLong()+ dim.ToLong())* GROW_FACTOR)));}
  // Shrink
 default void Shrink () { Realloc(Natural.Of((long) (Capacity().ToLong()/ SHRINK_FACTOR)));}
 
  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

 @Override
 default Natural Size() {return Capacity();}
  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */
 @Override
 default void Clear() {Realloc(Natural.ZERO);}
  

}
