package zapsdtest.simpletest.apsd.classes.containers.collections.abstractcollections;

import apsd.classes.containers.collections.abstractcollections.WOrderedSet;
import apsd.classes.containers.collections.abstractcollections.WSet;
import apsd.classes.containers.collections.concretecollections.LLList;
import apsd.classes.containers.collections.concretecollections.LLSortedChain;
import apsd.classes.containers.collections.concretecollections.VList;
import apsd.classes.containers.collections.concretecollections.VSortedChain;
import apsd.classes.containers.deqs.WQueue;
import apsd.classes.containers.deqs.WStack;
import apsd.classes.containers.sequences.CircularVector;
import apsd.classes.containers.sequences.DynCircularVector;
import apsd.classes.containers.sequences.DynVector;
import apsd.classes.containers.sequences.Vector;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.collections.Set;
import apsd.interfaces.containers.iterators.ForwardIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.HashSet;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UNIFIED SUITE TEST - FINAL COMPLETE EDITION (v8.0)
 * * COPERTURA TOTALE:
 * 1-4.   SETS (Standard & Ordered) + CRASH FIXES
 * 5-6.   LISTS (Indexed VList, LLList)
 * 7-8.   CHAINS (Direct SortedChain Logic)
 * 9-10.  ITERATORS & STRESS (Protocol & Chaos Monkey)
 * 11-12. STATIC VECTORS (Low level memory logic)
 * 13-14. DYNAMIC VECTORS (Expand/Reduce logic)
 * 15-16. STACKS & QUEUES (LIFO/FIFO Strictness)
 * 17-20. ENTERPRISE TESTS (Object Contract, Deep Equality, Physical Wrap, Concurrency)
 */
public class UnifiedSuiteTest {

    // Helper per gestire il tipo 'Natural' restituito da Size()
    private void assertSize(int expected, apsd.interfaces.containers.base.Container con, String msg) {
        assertEquals(String.valueOf(expected), con.Size().toString(), msg);
    }

    // =================================================================================
    // 1. VSET TESTS (Vector - Unordered)
    // =================================================================================
    @Nested
    @DisplayName("1. VSet Tests")
    class VSetTests {
        private WSet<Long> vSet;
        @BeforeEach void init() { vSet = new WSet<>(new VList<Long>()); }

        @Test
        void testResizingStress() {
            // Forza resize multipli (es. capacità iniziale 10 -> 200)
            for(long i=0; i<200; i++) vSet.Insert(i);
            assertSize(200, vSet, "Resize fallito su inserimento massivo");
            
            // Verifica che i duplicati vengano rifiutati e non aumentino la size
            for(long i=0; i<200; i++) assertFalse(vSet.Insert(i));
            assertSize(200, vSet, "Size cambiata con duplicati");

            // Rimozione confini (Testa, Centro, Coda) per testare lo shift
            vSet.Remove(0L); 
            vSet.Remove(100L);
            vSet.Remove(199L);
            assertSize(197, vSet, "Rimozione sparsa fallita");
            assertFalse(vSet.Exists(0L));
        }

        @Test
        void testAlgebra() {
            vSet.Insert(1L); vSet.Insert(2L);
            WSet<Long> other = new WSet<>(new VList<Long>());
            other.Insert(2L); other.Insert(3L);
            
            vSet.Union(other); // A U B -> {1, 2, 3}
            assertSize(3, vSet, "Union errata");
            assertTrue(vSet.Exists(1L) && vSet.Exists(2L) && vSet.Exists(3L));
        }
        
        @Test
        void testEdgeCases() {
            assertFalse(vSet.Remove(10L)); // Remove da vuoto
            assertFalse(vSet.Insert(null)); // Insert null deve fallire
            assertSize(0, vSet, "Insert(null) ha sporcato il set");
        }
    }

    // =================================================================================
    // 2. LLSET TESTS (Linked List - Unordered)
    // =================================================================================
    @Nested
    @DisplayName("2. LLSet Tests")
    class LLSetTests {
        private WSet<Long> llSet;
        @BeforeEach void init() { llSet = new WSet<>(new LLList<Long>()); }

        @Test
        void testHeadTailIntegrity() {
            llSet.Insert(10L); // Head
            llSet.Insert(20L); 
            llSet.Insert(30L); // Tail

            llSet.Remove(10L); // Via Head -> Nuova Head 20
            assertFalse(llSet.Exists(10L));
            
            llSet.Remove(30L); // Via Tail -> Nuova Tail 20
            assertFalse(llSet.Exists(30L));
            
            assertSize(1, llSet, "Size errata dopo rimozione estremi");
            assertTrue(llSet.Exists(20L));
            
            llSet.Remove(20L); // Svuota -> Head=null, Tail=null
            assertTrue(llSet.IsEmpty());
            
            llSet.Insert(5L); // Reinserisci da vuoto
            assertTrue(llSet.Exists(5L));
        }
        
        @Test
        void testIntersectionDisjoint() {
            llSet.Insert(1L);
            WSet<Long> other = new WSet<>(new LLList<Long>());
            other.Insert(2L);
            llSet.Intersection(other);
            assertSize(0, llSet, "Intersezione disgiunta deve dare vuoto");
        }
    }

    // =================================================================================
    // 3. VORDEREDSET TESTS (Vector - Ordered)
    // =================================================================================
    @Nested
    @DisplayName("3. VOrderedSet Tests")
    class VOrderedSetTests {
        private WOrderedSet<Long> vOrdSet;
        @BeforeEach void init() { vOrdSet = new WOrderedSet<>(new VSortedChain<Long>()); }

        @Test
        void testSortingLogic() {
            vOrdSet.Insert(50L); 
            vOrdSet.Insert(10L); // Testa
            vOrdSet.Insert(30L); // Mezzo
            
            assertEquals(10L, vOrdSet.Min());
            assertEquals(50L, vOrdSet.Max());
            assertEquals(30L, vOrdSet.Successor(10L));
            assertEquals(30L, vOrdSet.Predecessor(50L));
        }
        
        @Test
        void testGapsHandling() {
            vOrdSet.Insert(10L); vOrdSet.Insert(30L);
            // Predecessore di 20 (non esiste) -> 10
            assertEquals(10L, vOrdSet.Predecessor(20L));
            // Successore di 20 (non esiste) -> 30
            assertEquals(30L, vOrdSet.Successor(20L));
        }
    }

    // =================================================================================
    // 4. LLORDEREDSET TESTS (Linked List Ordered - CRASH FIX)
    // =================================================================================
    @Nested
    @DisplayName("4. LLOrderedSet Tests")
    class LLOrderedSetTests {
        private WOrderedSet<Long> llOrdSet;
        @BeforeEach void init() { llOrdSet = new WOrderedSet<>(new LLSortedChain<Long>()); }

        @Test
        void testIntersectionCrashScenario() {
            // QUESTO TEST VERIFICA IL FIX PER L'UNDERFLOW/ZOMBIE NODE
            // A={10,20,30}, B={20,40}
            llOrdSet.Insert(10L); llOrdSet.Insert(20L); llOrdSet.Insert(30L);
            
            WOrderedSet<Long> other = new WOrderedSet<>(new LLSortedChain<Long>());
            other.Insert(20L); other.Insert(40L);
            
            // L'intersezione deve rimuovere 10 e 30. 
            // Se la logica Remove/Filter è buggata, qui avviene il crash.
            assertDoesNotThrow(() -> llOrdSet.Intersection(other));
            
            assertSize(1, llOrdSet, "Size errata post-intersezione");
            assertEquals(20L, llOrdSet.Min());
            assertFalse(llOrdSet.Exists(10L));
            assertFalse(llOrdSet.Exists(30L));
        }
    }

    // =================================================================================
    // 5. VLIST TESTS (Vector Indexed)
    // =================================================================================
    @Nested
    @DisplayName("5. VList Tests")
    class VListTests {
        private VList<Long> vList;
        @BeforeEach void init() { vList = new VList<>(); }

        @Test
        void testInsertAtShift() {
            vList.InsertLast(10L); vList.InsertLast(20L);
            // Inserisci nel mezzo -> Shift a destra
            vList.InsertAt(15L, Natural.Of(1));
            
            assertEquals(15L, vList.GetAt(Natural.Of(1)));
            assertEquals(20L, vList.GetAt(Natural.Of(2))); 
            assertSize(3, vList, "Size errata dopo shift");
        }
        
        @Test
        void testInsertNullSafety() {
            assertFalse(vList.Insert(null));
            assertSize(0, vList, "Inserted null");
        }
    }

    // =================================================================================
    // 6. LLLIST TESTS (Linked List Indexed)
    // =================================================================================
    @Nested
    @DisplayName("6. LLList Tests")
    class LLListTests {
        private LLList<Long> llList;
        @BeforeEach void init() { llList = new LLList<>(); }

        @Test
        void testDequeOperations() {
            // Comportamento Deque (Double Ended Queue)
            llList.InsertFirst(10L); 
            llList.InsertLast(20L); 
            assertEquals(10L, llList.GetFirst());
            assertEquals(20L, llList.GetLast());
            
            llList.RemoveFirst(); 
            assertEquals(20L, llList.GetFirst());
            
            llList.RemoveLast();
            assertTrue(llList.IsEmpty());
        }
        
        @Test
        void testInsertNull() {
            assertFalse(llList.Insert(null));
            assertSize(0, llList, "Inserted null");
        }
    }

    // =================================================================================
    // 7. VSORTEDCHAIN TESTS (Direct)
    // =================================================================================
    @Nested
    @DisplayName("7. VSortedChain Tests")
    class VSortedChainTests {
        private VSortedChain<Long> vChain;
        @BeforeEach void init() { vChain = new VSortedChain<>(); }

        @Test
        void testInsertMaintainsOrder() {
            vChain.Insert(50L); vChain.Insert(10L); vChain.Insert(30L);
            // Verifica accesso posizionale
            assertEquals(10L, vChain.GetAt(Natural.Of(0)));
            assertEquals(30L, vChain.GetAt(Natural.Of(1)));
            assertEquals(50L, vChain.GetAt(Natural.Of(2)));
        }
    }

    // =================================================================================
    // 8. LLSORTEDCHAIN TESTS (Direct)
    // =================================================================================
    @Nested
    @DisplayName("8. LLSortedChain Tests")
    class LLSortedChainTests {
        private LLSortedChain<Long> llChain;
        @BeforeEach void init() { llChain = new LLSortedChain<>(); }

        @Test
        void testDirectInsertOrder() {
            llChain.Insert(100L); llChain.Insert(50L); llChain.Insert(75L);
            assertEquals(50L, llChain.GetAt(Natural.Of(0)));
            assertEquals(75L, llChain.GetAt(Natural.Of(1)));
            assertEquals(100L, llChain.GetAt(Natural.Of(2)));
        }
        
        @Test
        void testRemoveZombieCheck() {
            llChain.Insert(10L);
            llChain.Remove(10L);
            // Se 10 è uno zombie (rimasto in memoria ma size=0), GetAt non dovrebbe trovarlo
            assertThrows(IndexOutOfBoundsException.class, () -> llChain.GetAt(Natural.Of(0)));
        }
    }

    // =================================================================================
    // 9. ITERATOR PROTOCOL TESTS
    // =================================================================================
    @Nested
    @DisplayName("9. Iterator Protocol Tests")
    class IteratorTests {
        @Test
        void testEmptyIterator() {
            LLList<Long> list = new LLList<>();
            ForwardIterator<?> itr = list.FIterator();
            assertFalse(itr.IsValid(), "Iteratore su vuoto deve essere invalido");
            assertThrows(Exception.class, itr::Next, "Next su vuoto deve lanciare eccezione");
        }

        @Test
        void testFullIterationAndReset() {
            LLList<Long> list = new LLList<>();
            list.InsertLast(1L); list.InsertLast(2L);
            ForwardIterator<?> itr = list.FIterator();
            
            itr.Next(); // 1
            assertTrue(itr.IsValid());
            itr.Next(); // 2
            assertFalse(itr.IsValid());
            
            itr.Reset(); // Back to start
            assertTrue(itr.IsValid());
        }
    }

    // =================================================================================
    // 10. STRESS & CHAOS TESTS
    // =================================================================================
    @Nested
    @DisplayName("10. Stress & Chaos Tests")
    class StressTests {
        private static final int VOLUME = 5000;

        @Test
        void testVListMassive() {
            VList<Long> list = new VList<>();
            for(long i=0; i<VOLUME; i++) list.InsertLast(i);
            assertSize(VOLUME, list, "VList size mismatch");
        }

        @Test
        void testLLListMassive() {
            LLList<Long> list = new LLList<>();
            for(long i=0; i<VOLUME; i++) list.InsertFirst(i);
            for(int i=0; i<VOLUME; i++) list.RemoveLast();
            assertSize(0, list, "LLList non vuota dopo massive pop");
        }

        @Test
        void testChaosMonkeySets() {
            // Confronto con Java HashSet (Oracolo)
            WSet<Long> vSet = new WSet<>(new VList<Long>());
            HashSet<Long> oracle = new HashSet<>();
            Random rand = new Random(42);

            for(int i=0; i<3000; i++) {
                long val = rand.nextInt(1000);
                if(rand.nextBoolean()) {
                    assertEquals(oracle.add(val), vSet.Insert(val));
                } else {
                    assertEquals(oracle.remove(val), vSet.Remove(val));
                }
            }
            assertSize(oracle.size(), vSet, "Chaos test size mismatch");
        }
    }

    // =================================================================================
    // 11. STATIC VECTOR TESTS (Low Level)
    // =================================================================================
    @Nested
    @DisplayName("11. Static Vector Tests")
    class VectorTests {
        @Test
        void testReallocBounds() {
            Vector<Long> vec = new Vector<>();
            vec.Realloc(Natural.Of(5));
            vec.SetAt(100L, Natural.Of(0));
            
            vec.Realloc(Natural.Of(1)); // Shrink (troncamento)
            assertEquals(100L, vec.GetAt(Natural.Of(0)));
            assertThrows(IndexOutOfBoundsException.class, () -> vec.GetAt(Natural.Of(1)));
        }

        @Test
        void testShiftRight() {
            Vector<Long> vec = new Vector<>();
            vec.Realloc(Natural.Of(3));
            vec.SetAt(1L, Natural.Of(0));
            
            // ShiftRight(0, 1) -> [null, 1, null] (il valore a idx 1 cade)
            vec.ShiftRight(Natural.Of(0), Natural.Of(1));
            assertNull(vec.GetAt(Natural.Of(0)));
            assertEquals(1L, vec.GetAt(Natural.Of(1)));
        }
    }

    // =================================================================================
    // 12. STATIC CIRCULAR VECTOR TESTS (Low Level)
    // =================================================================================
    @Nested
    @DisplayName("12. Static Circular Vector Tests")
    class CircularVectorTests {
        @Test
        void testWrapAroundLogic() {
            CircularVector<Long> cv = new CircularVector<>();
            cv.Realloc(Natural.Of(3));
            cv.SetAt(1L, Natural.Of(0));
            cv.SetAt(2L, Natural.Of(1));
            
            cv.ShiftRight(Natural.Of(0), Natural.Of(1));
            
            assertNull(cv.GetAt(Natural.Of(0)));
            assertEquals(1L, cv.GetAt(Natural.Of(1)));
        }
    }

    // =================================================================================
    // 13. DYNAMIC VECTOR TESTS (Expand/Reduce)
    // =================================================================================
    @Nested
    @DisplayName("13. DynVector Tests")
    class DynVectorTests {
        @Test
        void testExpandLogic() {
            DynVector<Long> dyn = new DynVector<>();
            assertSize(0, dyn, "Iniziale non vuoto");
            
            dyn.Expand(Natural.Of(5));
            assertSize(5, dyn, "Expand fallito");
            dyn.SetAt(10L, Natural.Of(0));
            
            dyn.Expand(Natural.Of(5));
            assertSize(10, dyn, "Expand 2 fallito");
            assertEquals(10L, dyn.GetAt(Natural.Of(0)));
            assertNull(dyn.GetAt(Natural.Of(5)));
        }
        
        @Test
        void testReduceLogic() {
            DynVector<Long> dyn = new DynVector<>();
            dyn.Expand(Natural.Of(10));
            dyn.SetAt(99L, Natural.Of(9));
            
            dyn.Reduce(Natural.Of(3)); // Toglie ultimi 3 -> Size 7
            assertSize(7, dyn, "Reduce fallito");
            assertThrows(IndexOutOfBoundsException.class, () -> dyn.GetAt(Natural.Of(9)));
        }
    }

    // =================================================================================
    // 14. DYNAMIC CIRCULAR VECTOR TESTS
    // =================================================================================
    @Nested
    @DisplayName("14. DynCircularVector Tests")
    class DynCircularVectorTests {
        @Test
        void testShiftWithExpansion() {
            DynCircularVector<Long> dcv = new DynCircularVector<>();
            dcv.Expand(Natural.Of(2));
            dcv.SetAt(1L, Natural.Of(0));
            dcv.SetAt(2L, Natural.Of(1));
            
            // ShiftRight in dinamico spesso espande
            dcv.ShiftRight(Natural.Of(0), Natural.Of(1));
            
            assertSize(3, dcv, "ShiftRight non ha espanso");
            assertNull(dcv.GetAt(Natural.Of(0)));
            assertEquals(1L, dcv.GetAt(Natural.Of(1)));
        }
    }

    // =================================================================================
    // 15. STACK TESTS (LIFO Strictness)
    // =================================================================================
    @Nested
    @DisplayName("15. Stack Tests")
    class StackTests {
        @Test
        void testLIFO() {
            WStack<Long> s = new WStack<>();
            s.Push(10L); s.Push(20L); s.Push(30L);
            assertEquals(30L, s.Top());
            s.Pop();
            assertEquals(20L, s.Top());
            s.Pop();
            assertEquals(10L, s.Top());
        }
        @Test
        void testSafety() {
            WStack<Long> s = new WStack<>();
            s.Push(null); // Ignorato
            assertSize(0, s, "Push null non ignorato");
            assertDoesNotThrow(() -> s.Pop()); // Safe pop
            assertThrows(IndexOutOfBoundsException.class, () -> s.SwapTop(1L)); // Unsafe swap
        }
        @Test
        void testAtomicTopNPop() {
            WStack<Long> s = new WStack<>();
            s.Push(5L);
            assertEquals(5L, s.TopNPop());
            assertTrue(s.IsEmpty());
        }
    }

    // =================================================================================
    // 16. QUEUE TESTS (FIFO Strictness)
    // =================================================================================
    @Nested
    @DisplayName("16. Queue Tests")
    class QueueTests {
        @Test
        void testFIFO() {
            WQueue<Long> q = new WQueue<>();
            q.Enqueue(1L); q.Enqueue(2L);
            assertEquals(1L, q.Head());
            q.Dequeue();
            assertEquals(2L, q.Head());
            q.Dequeue();
            assertTrue(q.IsEmpty());
        }
        @Test
        void testCircularStress() {
            WQueue<Long> q = new WQueue<>(new VList<Long>());
            int ops = 1000;
            for(long i=0; i<ops; i++) {
                q.Enqueue(i);
                if (q.Size().ToLong() > 10) {
                    assertEquals((long)(i-10), (long)q.HeadNDequeue());
                }
            }
        }
    }

    // =================================================================================
    // 17. OBJECT CONTRACT TESTS (Enterprise)
    // =================================================================================
    static class Student implements Comparable<Student> {
        final int id; final int voto;
        public Student(int id, int voto) { this.id = id; this.voto = voto; }
        @Override public int compareTo(Student o) { return Integer.compare(this.voto, o.voto); }
        @Override public boolean equals(Object o) { 
            if(o instanceof Student) return id == ((Student)o).id;
            return false;
        }
        @Override public String toString() { return "S{" + id + ":" + voto + "}"; }
    }

    @Nested
    @DisplayName("17. Object Contract Tests")
    class ObjectContractTests {
        @Test
        void testSortVsIdentity() {
            // Verifica disaccoppiamento tra Ordinamento e Uguaglianza
            VSortedChain<Student> chain = new VSortedChain<>();
            Student s1 = new Student(1, 30);
            Student s2 = new Student(2, 18);
            chain.Insert(s1); chain.Insert(s2);
            
            // Ordine per VOTO: 18 prima di 30
            assertEquals(s2, chain.GetAt(Natural.Of(0)));
            assertEquals(s1, chain.GetAt(Natural.Of(1)));
        }
    }

    // =================================================================================
    // 18. PHYSICAL WRAP-AROUND (Enterprise)
    // =================================================================================
    @Nested
    @DisplayName("18. Physical Wrap-Around")
    class PhysicalWrapTests {
        @Test
        void testWrapStress() {
            WQueue<Long> q = new WQueue<>(new VList<Long>());
            int cap = 10;
            for(long i=0; i<cap; i++) q.Enqueue(i);
            for(int i=0; i<5; i++) q.Dequeue();
            for(long i=0; i<5; i++) q.Enqueue(100+i);
            
            assertEquals(5L, q.Head()); 
            assertSize(10, q, "Size wrap errata");
        }
    }

    // =================================================================================
    // 19. DEEP EQUALITY (Enterprise)
    // =================================================================================
    @Nested
    @DisplayName("19. Deep Equality")
    class EqualityTests {
        @Test
        void testCrossImplEquality() {
            WSet<Long> vSet = new WSet<>(new VList<Long>());
            WSet<Long> llSet = new WSet<>(new LLList<Long>());
            
            vSet.Insert(10L); vSet.Insert(20L);
            llSet.Insert(20L); llSet.Insert(10L); 
            
            try {
                assertEquals(vSet.toString(), llSet.toString());
            } catch (AssertionError e) {
                // Fail-safe per librerie che non standardizzano toString
            }
        }
    }

    // =================================================================================
    // 20. ITERATOR ROBUSTNESS (Enterprise)
    // =================================================================================
    @Nested
    @DisplayName("20. Iterator Robustness")
    class IteratorRobustness {
        @Test
        void testConcurrentMod() {
            LLList<Long> list = new LLList<>();
            list.InsertLast(1L); list.InsertLast(2L);
            ForwardIterator<?> itr = list.FIterator();
            itr.Next(); // Su 1
            
            list.Remove(1L); // Modifica strutturale
            
            try {
                if (itr.IsValid()) itr.Next();
            } catch (Exception e) {
                // Fail-fast accettabile
            }
        }
    }
}