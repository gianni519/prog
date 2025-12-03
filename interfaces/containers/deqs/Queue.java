package apsd.interfaces.containers.deqs;

 import apsd.interfaces.containers.base.ClearableContainer;
 import apsd.interfaces.containers.base.InsertableContainer;


public interface Queue<Data> extends ClearableContainer, InsertableContainer<Data> { // Must extend ClearableContainer and InsertableContainer

  // Head
  Data Head();
  // Dequeue
  void Dequeue();
  // HeadNDequeue
  default Data HeadNDequeue() {
      if (IsEmpty()) return null;
      Data dat = Head();
      Dequeue();
      return dat;
  }

  // Enqueue
  void Enqueue(Data dat);


  /* ** /
  / Override specific member functions from ClearableContainer               /
  / ** /

    @Override
    default void Clear() {
        while (!IsEmpty()) {
            Dequeue();
        }
    }

  / ** /
  / Override specific member functions from InsertableContainer              /
  / ** */

    @Override
     default boolean Insert(Data dat) {
        Enqueue(dat);
        return true;
    }

}
