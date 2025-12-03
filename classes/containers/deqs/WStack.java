package apsd.classes.containers.deqs;

 import apsd.classes.containers.collections.concretecollections.VList;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.collections.List;
 import apsd.interfaces.containers.deqs.Stack;

/** Object: Wrapper stack implementation. */
public class WStack<Data> implements Stack<Data> { // Must implement Stack

   protected final List<Data> lst;

    public WStack() {
        lst = new VList<>();
    }

    public WStack(List<Data> lst) {
        if (lst == null) { throw new NullPointerException("List cannot be null!"); }
        this.lst = lst;
    }

    public WStack(TraversableContainer<Data> con) {
        lst = new VList<>();
        con.TraverseForward(dat -> { lst.InsertLast(dat); return false; });
    }

    public WStack(List<Data> lst, TraversableContainer<Data> con) {
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
  /* Override specific member functions from Stack                            */
  /* ************************************************************************ */

    @Override
    public Data Top() {
        if (lst.IsEmpty()) return null;
        return lst.GetFirst();
    }

    @Override
    public void Pop() {
        if (!lst.IsEmpty()) {
            lst.RemoveFirst(); 
        }
        
    }

    @Override
    public Data TopNPop() {
        if (lst.IsEmpty()) return null;
        return lst.FirstNRemove();
    }

    @Override
    public void SwapTop(Data dat) {
        Pop();
        Push(dat);
    }

    @Override
    public Data TopNSwap(Data newdat) {
        Data dat = Top();
        SwapTop(newdat);
        return dat;
    }

    @Override
    public void Push(Data dat) {
        lst.InsertFirst(dat);

    }

}