package apsd.interfaces.containers.deqs;

 import apsd.interfaces.containers.base.ClearableContainer;
 import apsd.interfaces.containers.base.InsertableContainer;


public interface Stack<Data> extends ClearableContainer, InsertableContainer<Data>  { // Must extend ClearableContainer and InsertableContainer

  // Top
  Data Top();
  // Pop
  void Pop();
  // TopNPop
  default Data TopNPop() {
      if (IsEmpty()) return null;
      Data dat = Top();
      Pop();
      return dat;
  }

  // SwapTop
  default void SwapTop(Data dat) {
      Pop();
      Push(dat);
  }
  // TopNSwap
  default Data TopNSwap(Data newdat) {
      Data dat = Top();
      SwapTop(newdat);
      return dat;
  }

  // Push
  void Push(Data dat);

  /* ** /
  / Override specific member functions from ClearableContainer               /
  / ** /

    @Override
    default void Clear() {
        while (!IsEmpty()) {
            Pop();
        }
    }


  / ** /
  / Override specific member functions from InsertableContainer              /
  / ** */

    @Override
    default boolean Insert(Data dat) {
        Push(dat);
        return true;
    }

}
