package apsd.classes.containers.deqs;

 import apsd.classes.containers.collections.concretecollections.VList;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.collections.List;
 import apsd.interfaces.containers.deqs.Queue;

/** Object: Wrapper queue implementation. */
public class WQueue<Data> implements Queue<Data> { // Must implement Queue

   protected final List<Data> lst;

    public WQueue() {
        lst = new VList<>();
    }

    public WQueue(List<Data> lst) {
        if (lst == null) { throw new NullPointerException("List cannot be null!"); }
        this.lst = lst;
    }

    public WQueue(TraversableContainer<Data> con) {
        lst = new VList<>();
        con.TraverseForward(dat -> { lst.InsertLast(dat); return false; });
    }

    public WQueue(List<Data> lst, TraversableContainer<Data> con) {
        if (lst == null) { throw new NullPointerException("List cannot be null!"); }
        this.lst = lst;
        con.TraverseForward(dat -> { lst.InsertLast(dat); return false; });
    }




    /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

    @Override
    public Natural Size() {
        return lst.Size();
    }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

    @Override
    public void Clear() {
        lst.Clear();
    }

  /* ************************************************************************ */
  /* Override specific member functions from Queue                            */
  /* ************************************************************************ */

    @Override
    public Data Head() {
        if (lst.IsEmpty()) return null;
        return lst.GetFirst();
    }

    @Override
    public void Dequeue() {
         if (!lst.IsEmpty()) {
            lst.RemoveFirst();
        }
    }

    @Override
    public Data HeadNDequeue() {
        if (lst.IsEmpty()) return null;
        return lst.FirstNRemove();
    }

    @Override
    public void Enqueue(Data dat) {
        lst.InsertLast(dat);
    }

}
