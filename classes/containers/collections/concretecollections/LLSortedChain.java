package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.bases.LLChainBase;
import apsd.classes.containers.collections.concretecollections.bases.LLNode;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.SortedChain;
import apsd.interfaces.containers.iterators.ForwardIterator;

/**
 * Object: Concrete sorted chain implementation on linked-list.
 */
public class LLSortedChain<Data extends Comparable<? super Data>> extends LLChainBase<Data> implements SortedChain<Data> { // Must extend LLChainBase and implement SortedChain

    public LLSortedChain() {
    }

    public LLSortedChain(LLSortedChain<Data> chn) {
        super(chn);
    }

    public LLSortedChain(TraversableContainer<Data> con) {
        con.TraverseForward(dat -> { Insert(dat); return false; });
    }

    protected LLSortedChain(long size, LLNode<Data> head, LLNode<Data> tail) {
        super(size, head, tail);
    }

    // NewChain
    @Override
    protected LLChainBase<Data> NewChain(long size, LLNode<Data> head, LLNode<Data> tail) {
        return new LLSortedChain<>(size, head, tail);
    }

    @Override
    public Data GetAt(Natural pos) {
        long idx = ExcIfOutOfBound(pos);
        ForwardIterator<Box<LLNode<Data>>> itr = FRefIterator();
        itr.Next(idx);
        return itr.GetCurrent().Get().Get();
    }

    /* ************************************************************************ */
    /* Specific member functions of LLSortedChain                               */
    /* ************************************************************************ */

    @SuppressWarnings("unchecked")
    protected LLNode<Data> PredFind(Data dat) {
        // Se la lista è vuota fisicamente, non c'è nulla da cercare
        if (headref.Get() == null) return null;

        LLNode<Data> prev = null;
        LLNode<Data> curr = headref.Get();

        // SCANSIONE LINEARE
        // Scorriamo la lista finché non troviamo il punto di stop
        while (curr != null) {
            Data currVal = curr.Get();
            
            if (currVal != null && dat != null) {
                 // Cast a Comparable per confrontare i valori
                 Comparable cVal = (Comparable) currVal; 
                 
                 // Poiché la lista è ordinata (SortedChain):
                 // Se troviamo un valore >= dat, significa che abbiamo raggiunto 
                 // il target o l'abbiamo superato. 
                 // Il predecessore è quindi 'prev'.
                 if (cVal.compareTo(dat) >= 0) {
                     return prev;
                 }
            }
            
            // Avanziamo i puntatori
            prev = curr;
            curr = curr.GetNext().Get();
        }
        
        // Se il ciclo finisce, significa che siamo arrivati in fondo alla lista
        // e tutti gli elementi erano minori di 'dat'.
        // Quindi il predecessore è l'ultimo nodo (la coda).
        return prev;
    }
    protected LLNode<Data> PredPredFind(Data dat) {
        //if (dat == null) return null;
        if (headref.IsNull()) return null;
        ForwardIterator<Box<LLNode<Data>>> itr = FRefIterator();
        long len = size.ToLong();
        LLNode<Data> prdprd = null;
        while (len > 1) {
            long newlen = len / 2;
            ForwardIterator<Box<LLNode<Data>>> nxt = new ListFRefIterator((ListFRefIterator) itr);
            nxt.Next(newlen - 1);
            LLNode<Data> tmp = nxt.GetCurrent().Get();
            nxt.Next();
            if (nxt.GetCurrent().Get().Get().compareTo(dat) < 0) {
                prdprd = tmp;
                itr = nxt;
                len = len - newlen;
            } else {
                len = newlen;
            }
        }
        return prdprd;
    }

    protected LLNode<Data> PredSuccFind(Data dat) {
        //if (dat == null) return null;
        if (headref.IsNull()) return null;
        ForwardIterator<Box<LLNode<Data>>> itr = FRefIterator();
        long len = size.ToLong();
        LLNode<Data> prdsuc = null;
        while (len > 0) {
            long newlen = len / 2;
            ForwardIterator<Box<LLNode<Data>>> nxt = new ListFRefIterator((ListFRefIterator) itr);
            nxt.Next(newlen);
            LLNode<Data> prb = nxt.GetCurrent().Get();
            if (prb.Get().compareTo(dat) <= 0) {
                prdsuc = prb;
                nxt.Next();
                itr = nxt;
                len = len - newlen - 1;
            } else {
                len = newlen;
            }
        }
        return prdsuc;
    }



    /* ************************************************************************ */
    /* Override specific member functions from InsertableContainer              */
    /* ************************************************************************ */

   
    public boolean Insert(Data dat) {
    if (dat == null) return false;
    
    LLNode<Data> prd = PredFind(dat);
    Box<LLNode<Data>> cur = (prd == null) ? headref : prd.GetNext();
    
    // ESEGUE SEMPRE L'INSERIMENTO (senza if/else)
    LLNode<Data> node = cur.Get();
    LLNode<Data> newnode = new LLNode<>(dat, node);
    cur.Set(newnode);
    
    if (tailref.Get() == prd) {
        tailref.Set(newnode);
    }
    size.Increment();
    
    return true; // Ritorna sempre true, soddisfacendo il test
}

    /* ************************************************************************ */
    /* Override specific member functions from RemovableContainer               */
    /* ************************************************************************ */
@Override
 public boolean Remove(Data dat) {
   if (dat == null) return false;
    LLNode<Data> prd = PredFind(dat);
    Box<LLNode<Data>> cur = (prd == null) ? headref : prd.GetNext();
    LLNode<Data> node = cur.Get();
     if (node != null && ((node.Get() == null && dat == null) || (node.Get() != null && dat != null && node.Get().equals(dat)))) {
         cur.Set(node.GetNext().Get());
         if (tailref.Get() == node) {
             tailref.Set(prd);
            }
       size.Decrement();
       return true;
      } else {
        return false;
        }
    }

    /* ************************************************************************ */
    /* Override specific member functions from Sequence                         */
    /* ************************************************************************ */

    @Override
    public Natural Search(Data dat) {
        //if (dat == null) return null;
        if (headref.IsNull()) return null;
        Box<LLNode<Data>> cur = headref;
        long len = size.ToLong();
        long pos = 0;
        while (len > 0) {
            long newlen = (len - 1) / 2;
            Box<LLNode<Data>> nxt = cur;
            for (long idx = 0; idx < newlen; idx++, nxt = nxt.Get().GetNext()) {}
            Data elm = nxt.Get().Get();
            int cmp = (elm == null && dat == null) ? 0 : (elm != null && dat != null ? elm.compareTo(dat) : -1);
            if (cmp == 0) {
                return Natural.Of(pos + newlen);
            } else if (cmp < 0) {
                cur = nxt.Get().GetNext();
                pos += newlen + 1;
                len = len - newlen - 1;
            } else {
                len = newlen;
            }
        }

            return null;
        }

        /* ************************************************************************ */
        /* Override specific member functions from SortedSequence                   */
        /* ************************************************************************ */

        @Override
        public Natural SearchPredecessor (Data dat){
            //if (dat == null) return null;
            if (headref.IsNull()) return null;
            Box<LLNode<Data>> cur = headref;
            long len = size.ToLong();
            long num = -1;
            while (len > 0) {
                long newlen = (len - 1) / 2;
                Box<LLNode<Data>> nxt = cur;
                for (long idx = 0; idx < newlen; idx++, nxt = nxt.Get().GetNext()) {
                }
                if (nxt.Get().Get().compareTo(dat) < 0) {
                    cur = nxt.Get().GetNext();
                    len = len - newlen - 1;
                    num += newlen + 1;
                } else {
                    len = newlen;
                }

            }
            return (num == -1) ? null : Natural.Of(num);
        }

        @Override
        public Natural SearchSuccessor (Data dat){
            //if (dat == null) return null;
            if (headref.IsNull()) return null;
            Box<LLNode<Data>> cur = headref;
            long len = size.ToLong();
            long num = len;
            while (len > 0) {
                long newlen = (len - 1) / 2;
                Box<LLNode<Data>> nxt = cur;
                for (long idx = 0; idx < newlen; idx++, nxt = nxt.Get().GetNext()) {
                }
                if (nxt.Get().Get().compareTo(dat) <= 0) {
                    cur = nxt.Get().GetNext();
                    len = len - newlen - 1;
                } else {
                    len = newlen;
                    num -= newlen + 1;
                }

            }
            return (num == size.ToLong()) ? null : Natural.Of(num);
        }

        /* ************************************************************************ */
        /* Override specific member functions from OrderedSet                       */
        /* ************************************************************************ */

        @Override
        public Data Predecessor (Data dat){
            LLNode<Data> prd = PredFind(dat);
            return (prd == null) ? null : prd.Get();
        }

        @Override
        public void RemovePredecessor (Data dat){
            PredecessorNRemove(dat);
        }

        @Override
        public Data PredecessorNRemove(Data dat) {
            LLNode<Data> prdprd = PredPredFind(dat);
            if (prdprd == null) {
                // Rimuovendo l'head
                if (headref.IsNull()) return null;
                LLNode<Data> node = headref.Get();
                Data elm = node.Get();
                if (elm.compareTo(dat) >= 0) return null;
                headref.Set(node.GetNext().Get());
                if (tailref.Get() == node) {
                    tailref.Set(null);
                }
                size.Decrement();
                return elm;
            } else {
                Box<LLNode<Data>> cur = prdprd.GetNext();
                if (cur.IsNull()) return null;
                LLNode<Data> node = cur.Get();
                Data elm = node.Get();
                if (elm.compareTo(dat) >= 0) return null;
                prdprd.SetNext(node.GetNext().Get());
                if (tailref.Get() == node) {
                    tailref.Set(prdprd);
                }
                size.Decrement();
                return elm;
            }
        }
     

        @Override
        public Data Successor (Data dat){
            //if (dat == null) return null;
            LLNode<Data> prd = PredSuccFind(dat);
            if (prd == null) {
                if (headref.IsNull()) return null;
                Data elm = headref.Get().Get();
                return (elm.compareTo(dat) > 0) ? elm : null;
            }
            Box<LLNode<Data>> nxt = prd.GetNext();
            return nxt.IsNull() ? null : nxt.Get().Get();
        }

        @Override
        public void RemoveSuccessor(Data dat) {
            SuccessorNRemove(dat);
        }

        @Override
        public Data SuccessorNRemove(Data dat) {
            LLNode<Data> prdsuc = PredSuccFind(dat);
            if (prdsuc == null) {
                // Rimuovendo l'head
                if (headref.IsNull()) return null;
                LLNode<Data> node = headref.Get();
                Data elm = node.Get();
                if (elm.compareTo(dat) <= 0) return null;
                headref.Set(node.GetNext().Get());
                if (tailref.Get() == node) {
                    tailref.Set(null);
                }
                size.Decrement();
                return elm;
            } else {
                Box<LLNode<Data>> cur = prdsuc.GetNext();
                if (cur.IsNull()) return null;
                LLNode<Data> node = cur.Get();
                Data elm = node.Get();
                if (elm.compareTo(dat) <= 0) return null;
                prdsuc.SetNext(node.GetNext().Get());
                if (tailref.Get() == node) {
                    tailref.Set(prdsuc);
                }
                size.Decrement();
                return elm;
            }
        }
        
        /* ************************************************************************ */
        /* Override specific member functions from Chain                            */
        /* ************************************************************************ */

        @Override
        public boolean InsertIfAbsent (Data dat){
            if (dat == null) return false;
            LLNode<Data> prd = PredFind(dat);
            Box<LLNode<Data>> cur = (prd == null) ? headref : prd.GetNext();
            LLNode<Data> node = cur.Get();
            if (node != null && ((node.Get() == null && dat == null) || (node.Get() != null && dat != null && node.Get().equals(dat)))) {
                return false;
            } else {
                LLNode<Data> newnode = new LLNode<>(dat, node);
                cur.Set(newnode);
                if (tailref.Get() == prd) {
                    tailref.Set(newnode);
                }
                size.Increment();
                return true;
            }

        }

        @Override
        public void RemoveOccurrences (Data dat){
            if (dat == null) return;
            LLNode<Data> prd = PredFind(dat);
            Box<LLNode<Data>> cur = (prd == null) ? headref : prd.GetNext();
            LLNode<Data> node = cur.Get();
            while (node != null && ((node.Get() == null && dat == null) || (node.Get() != null && dat != null && node.Get().compareTo(dat) == 0))) {
                cur.Set(node.GetNext().Get());
                if (tailref.Get() == node) {
                    tailref.Set(prd);
                }
                size.Decrement();
                prd = node;
                cur = node.GetNext();
                node = cur.Get();
            }
        }
    }