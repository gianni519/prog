package apsd.classes.containers.collections.concretecollections.bases;


 import apsd.classes.utilities.Box;
 import apsd.classes.utilities.MutableNatural;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.collections.Chain;
 import apsd.interfaces.containers.iterators.BackwardIterator;
 import apsd.interfaces.containers.iterators.ForwardIterator;
 import apsd.interfaces.containers.iterators.MutableBackwardIterator;
 import apsd.interfaces.containers.iterators.MutableForwardIterator;
 import apsd.interfaces.containers.sequences.Sequence;
 import apsd.interfaces.traits.Predicate;

/** Object: Abstract chain base implementation on linked-list. */
abstract public class LLChainBase<Data> implements Chain<Data> { // Must implement Chain

   protected final MutableNatural size = new MutableNatural();
   protected final Box<LLNode<Data>> headref = new Box<>();
   protected final Box<LLNode<Data>> tailref = new Box<>();

  // LLChainBase

    public LLChainBase() {}

   public LLChainBase(TraversableContainer<Data> con) {
     size.Assign(con.Size());
     final Box<Boolean> first = new Box<>(true);
     con.TraverseForward(dat -> {
       LLNode<Data> node = new LLNode<>(dat);
       if (first.Get()) {
         headref.Set(node);
         first.Set(false);
       } else {
         tailref.Get().SetNext(node);
       }
       tailref.Set(node);
       return false;
     });
   }

   protected LLChainBase(long size, LLNode<Data> head, LLNode<Data> tail) {
       this.size.Assign(size);
       headref.Set(head);
       tailref.Set(tail);
   }

  // NewChain
    abstract protected LLChainBase<Data> NewChain(long size, LLNode<Data> head, LLNode<Data> tail);

  /* ************************************************************************ */
  /* Specific member functions from LLChainBase                               */
  /* ************************************************************************ */

  protected class ListFRefIterator implements ForwardIterator<Box<LLNode<Data>>> {
      protected Box<LLNode<Data>> cur;

      public ListFRefIterator() {
          cur = new Box<>(headref.Get());
      }

      public ListFRefIterator(ListFRefIterator itr) { cur =itr.cur; }

      @Override
      public boolean IsValid() { return !cur.IsNull(); }

      @Override
      public void Reset() { cur.Set(headref.Get()); }

      @Override
      public Box<LLNode<Data>> GetCurrent() {
          if(!IsValid()) throw new IllegalStateException("Iterator terminated!");
          return cur;
      }

      @Override
      public void Next() {
          if(!IsValid()) throw new IllegalStateException("Iterator terminated!");
          cur = cur.Get().GetNext();
      }

      @Override
      public Box<LLNode<Data>> DataNNext() {
          if(!IsValid()) throw new IllegalStateException("Iterator termined!");
          Box<LLNode<Data>> oldcur=new Box<>(cur.Get());;
          cur= cur.Get().GetNext();
          return oldcur;
      }

  }

  protected ForwardIterator<Box<LLNode<Data>>> FRefIterator() { return new ListFRefIterator(); }

    protected class ListBRefIterator implements BackwardIterator<Box<LLNode<Data>>> {
        protected Box<LLNode<Data>> cur;

        public ListBRefIterator() {
            cur = new Box<>(tailref.Get());
        }

        public ListBRefIterator(ListBRefIterator itr) {
            cur = itr.cur;
        }

        @Override
        public boolean IsValid() {
            return !cur.IsNull();
        }

        @Override
        public void Reset() {
            cur.Set(tailref.Get());
        }

        @Override
        public Box<LLNode<Data>> GetCurrent() {
            if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
            return cur;
        }

        @Override
        public void Prev() {
            if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
            if (cur.Get() == headref.Get()) {
                cur.Set(null);
            } else {
                Box<LLNode<Data>> temp = new Box<>(headref.Get());
                while (temp.Get().GetNext().Get() != cur.Get()) {
                    temp.Set(temp.Get().GetNext().Get());
                }
                cur = temp;
            }
        }

        @Override
        public Box<LLNode<Data>> DataNPrev() {
            if (!IsValid()) throw new IllegalStateException("Iterator terminated!");
            Box<LLNode<Data>> oldcur = cur;
            Prev();
            return oldcur;
        }

    }

    protected BackwardIterator<Box<LLNode<Data>>> BRefIterator() {
        return new ListBRefIterator();
    }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

    @Override
    public Natural Size() {
        return size.ToNatural();
    }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

    @Override
    public void Clear() {
        headref.Set(null);
        tailref.Set(null);
        size.Zero();
    }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

   @Override
   
   public boolean Remove(Data dat) {
       final Box<LLNode<Data>> prd = new Box<>();
       return FRefIterator().ForEachForward(cur -> {
           LLNode<Data> node = cur.Get();
           Data nodeData = node.Get();
           if ((nodeData == null && dat == null) || (nodeData != null && dat != null && nodeData.equals(dat))) {
               if (prd.IsNull()) {
                   headref.Set(node.GetNext().Get());
               } else {
                   prd.Get().SetNext(node.GetNext().Get());
               }
               if (tailref.Get() == node) {
                   tailref.Set(prd.Get());
               }
               size.Decrement();
               return true;
           }
           prd.Set(node);
           return false;
       });
   }


  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

    protected class ListFIterator implements MutableForwardIterator<Data> {
        protected final ForwardIterator<Box<LLNode<Data>>> itr;

        public ListFIterator() {
            itr = FRefIterator();
        }

        @Override
        public boolean IsValid() {
            return itr.IsValid();
        }

        @Override
        public void Reset() {
            itr.Reset();
        }

        @Override
        public Data GetCurrent() {
            return itr.GetCurrent().Get().Get();
        }

        @Override
        public void SetCurrent(Data dat) {
            itr.GetCurrent().Get().Set(dat);
        }

        @Override
        public void Next() {
            itr.Next();
        }

        @Override
        public Data DataNNext() {
            Box<LLNode<Data>> oldref = itr.DataNNext();
            return oldref.Get().Get();
        }
    }

    protected class ListBIterator implements MutableBackwardIterator<Data> {
        protected final BackwardIterator<Box<LLNode<Data>>> itr;

        public ListBIterator() {
            itr = BRefIterator();
        }

        @Override
        public boolean IsValid() {
            return itr.IsValid();
        }

        @Override
        public void Reset() {
            itr.Reset();
        }

        @Override
        public Data GetCurrent() {
            return itr.GetCurrent().Get().Get();
        }

        @Override
        public void SetCurrent(Data dat) {
            itr.GetCurrent().Get().Set(dat);
        }

        @Override
        public void Prev() {
            itr.Prev();
        }

        @Override
        
        public Data DataNPrev() {
            Box<LLNode<Data>> oldref = itr.GetCurrent();  // Ottieni il Box corrente
            LLNode<Data> node = oldref.Get();              // Salva il NODO (non il Box)
            Data result = node.Get();                      // Salva il dato
            itr.Prev();                                    // Muovi l'iteratore
            return result;                                 // Restituisci il dato salvato
        }
    }

    @Override
    public MutableForwardIterator<Data> FIterator() {
        return new ListFIterator();
    }

    @Override
    public MutableBackwardIterator<Data> BIterator() {
        return new ListBIterator();
    }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */



  @Override
  public Data GetFirst() {
      if(headref.IsNull()) throw new IndexOutOfBoundsException("First element does not exist!");
      return headref.Get().Get();
  }

    @Override
    public Data GetLast() {
        if(tailref.IsNull()) throw new IndexOutOfBoundsException("Last element does not exist!");
        return tailref.Get().Get();
    }

    @Override
    public Sequence<Data> SubSequence(Natural from, Natural to) {
      long f = from.ToLong(), t = to.ToLong();
      if (f > t || t>=size.ToLong()) return null;
      final Box<Long> idx = new Box<>(0L);
      final Box<LLNode<Data>> headlst = new Box<>();
      final Box<LLNode<Data>> taillst = new Box<>();
      TraverseForward(dat -> {
          if(idx.Get() > t) return true;
          LLNode<Data> node = new LLNode<>(dat);
          if(idx.Get() == f) {
              headlst.Set(node);
          } else if (idx.Get() > f) {
              taillst.Get().SetNext(node);
          }
          taillst.Set(node);
          idx.Set(idx.Get() + 1);
          return false;
      });
      return NewChain(t - f + 1, headlst.Get(), taillst.Get());
    }




  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

  @Override
  public Data AtNRemove(Natural pos) {
      long idx = ExcIfOutOfBound(pos);
      if (idx == 0) {
          return FirstNRemove();
      } else if (idx == Size().ToLong()-1) {
          return LastNRemove();
      } else {
          ForwardIterator<Box<LLNode<Data>>> itr = FRefIterator();
          itr.Next(idx-1);
          Box<LLNode<Data>> prdref = itr.GetCurrent();
          LLNode<Data> prd = prdref.Get();
          LLNode<Data> node = prd.GetNext().Get();
          prd.SetNext(node.GetNext().Get());
          size.Decrement();
          return node.Get();

      }
  }

    @Override
    public void RemoveFirst() {
        if (headref.IsNull()) throw new IndexOutOfBoundsException("Empty chain");
        LLNode<Data> node = headref.Get();
        //headref.Set(node.GetNext().Get());
        if (tailref.Get() == node) {
            headref.Set(null);
            tailref.Set(null);
        } else {
            //headref.Set(headref.Get().GetNext().Get());
            headref.Set(node.GetNext().Get());
        }
        size.Decrement();
    }

    @Override
    
    public void RemoveLast() {
       if (headref.IsNull()) throw new IndexOutOfBoundsException("Empty chain");
        if (headref.Get() == tailref.Get()) {
            headref.Set(null);
            tailref.Set(null);
        } else {
            BackwardIterator<Box<LLNode<Data>>> itr = BRefIterator();
            itr.Prev();
            LLNode<Data> newtail = itr.GetCurrent().Get();
            newtail.SetNext(null);
            tailref.Set(newtail);
        }
        size.Decrement();
    }

    @Override
    public Data FirstNRemove() {
        LLNode<Data> node = headref.Get();
        if (node == null) return null;
        Data dat = node.Get();
        RemoveFirst();
        return dat;
    }

    @Override
    public Data LastNRemove() {
        LLNode<Data> node = tailref.Get();
        if(node == null) return null;
        Data dat = node.Get();
        RemoveLast();
        return dat;
    }



  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */
@Override
    public boolean Filter(Predicate<Data> fun) {
        if (fun == null) return false;
        
        long oldsize = size.ToLong();
        
        LLNode<Data> prev = null;
        LLNode<Data> curr = headref.Get();
        
        while (curr != null) {
            Data val = curr.Get();
            // Salviamo il riferimento al prossimo nodo PRIMA di scollegare
            LLNode<Data> nextNode = curr.GetNext().Get();
            
            // Applichiamo il filtro
            if (fun.Apply(val)) {
                // CASO KEEP: Il nodo resta.
                // Il nodo corrente diventa il precedente per il prossimo passo.
                prev = curr;
            } else {
                // CASO REMOVE: Il nodo va via.
                
                if (prev == null) {
                    // Rimuoviamo la TESTA
                    headref.Set(nextNode);
                } else {
                    // Rimuoviamo un nodo INTERNO: 'prev' salta 'curr'
                    prev.GetNext().Set(nextNode);
                }
                
                // Aggiorniamo la CODA se necessario
                if (tailref.Get() == curr) {
                    tailref.Set(prev);
                }
                
                // Decrementiamo la size (con protezione)
                if (size.ToLong() > 0) {
                    size.Decrement();
                }
                
                // NOTA BENE: Qui NON aggiorniamo 'prev'.
                // Perché abbiamo rimosso 'curr', quindi 'prev' deve rimanere dov'è
                // per agganciarsi eventualmente al prossimo nodo.
            }
            
            // Avanziamo sempre il cursore principale
            curr = nextNode;
        }
        
        return size.ToLong() != oldsize;
    }
}