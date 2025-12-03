package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.SortedSequence;

public interface SortedChain<Data extends Comparable<? super Data>> extends OrderedChain<Data>, SortedSequence<Data> {

    default Natural SearchPredecessor(Data valoreRiferimento) {
        if (valoreRiferimento == null || Size().IsZero()) {
            return null;
        }
        long indiceTrovato = -1;
        long sinistra = 0;
        long destra = Size().ToLong() - 1;
        while (sinistra <= destra) {
            long centro = (sinistra + destra) / 2;
            Natural indiceCentro = Natural.Of(centro);
            Data elementoAlCentro = GetAt(indiceCentro);
            int confronto = elementoAlCentro.compareTo(valoreRiferimento);
            if (confronto < 0) {
                indiceTrovato = centro;
                sinistra = centro + 1;
            } else {
                destra = centro - 1;
            }
        }
        if (indiceTrovato > -1) {
            return Natural.Of(indiceTrovato);
        }
        return null;
    }

    default Natural SearchSuccessor(Data valoreRiferimento) {
        if (valoreRiferimento == null || Size().IsZero()) {
            return null;
        }
        long indiceTrovato = -1;
        long sinistra = 0;
        long destra = Size().ToLong() - 1;
        while (sinistra <= destra) {
            long centro = (sinistra + destra) / 2;
            Natural indiceCentro = Natural.Of(centro);
            Data elementoAlCentro = GetAt(indiceCentro);
            int confronto = elementoAlCentro.compareTo(valoreRiferimento);
            if (confronto <= 0) {
                sinistra = centro + 1;
            } else {
                indiceTrovato = centro;
                destra = centro - 1;
            }
        }
        if (indiceTrovato > -1) {
            return Natural.Of(indiceTrovato);
        }
        return null;
    }

    @Override
    default Natural Search(Data valoreDaCercare) {
        if (valoreDaCercare == null) {
            return null;
        }
        return SortedSequence.super.Search(valoreDaCercare);
    }

    default void Intersection(SortedChain<Data> altraCatena) {
        Natural i = Natural.ZERO;
        Natural j = Natural.ZERO;
        while (i.compareTo(Size()) < 0 && j.compareTo(altraCatena.Size()) < 0) {
            int confronto = GetAt(i).compareTo(altraCatena.GetAt(j));
            if (confronto < 0) {
                RemoveAt(i);
            } else {
                j = j.Increment();
                if (confronto == 0) {
                    i = i.Increment();
                }
            }
        }
    }

    @Override
    default Data Min() {
        if (Size().IsZero()) {
            return null;
        }
        return GetFirst();
    }

    @Override
    default void RemoveMin() {
        if (Size().IsZero()) {
            return;
        }
        RemoveFirst();
    }

    @Override
    default Data MinNRemove() {
        if (Size().IsZero()) {
            return null;
        }
        return FirstNRemove();
    }

    @Override
    default Data Max() {
        if (Size().IsZero()) {
            return null;
        }
        return GetLast();
    }

    @Override
    default void RemoveMax() {
        if (Size().IsZero()) {
            return;
        }
        RemoveLast();
    }

    @Override
    default Data MaxNRemove() {
        if (Size().IsZero()) {
            return null;
        }
        return LastNRemove();
    }

    @Override
    default Data Predecessor(Data valoreRiferimento) {
        Natural indice = SearchPredecessor(valoreRiferimento);
        if (indice == null) {
            return null;
        }
        return GetAt(indice);
    }

    @Override
    default void RemovePredecessor(Data valoreRiferimento) {
        Natural indice = SearchPredecessor(valoreRiferimento);
        if (indice != null) {
            RemoveAt(indice);
        }
    }

    @Override
    default Data PredecessorNRemove(Data valoreRiferimento) {
        Natural indice = SearchPredecessor(valoreRiferimento);
        if (indice != null) {
            return AtNRemove(indice);
        }
        return null;
    }

    @Override
    default Data Successor(Data valoreRiferimento) {
        Natural indice = SearchSuccessor(valoreRiferimento);
        if (indice == null) {
            return null;
        }
        return GetAt(indice);
    }

    @Override
    default void RemoveSuccessor(Data valoreRiferimento) {
        Natural indice = SearchSuccessor(valoreRiferimento);
        if (indice != null) {
            RemoveAt(indice);
        }
    }

    @Override
    default Data SuccessorNRemove(Data valoreRiferimento) {
        Natural indice = SearchSuccessor(valoreRiferimento);
        if (indice != null) {
            return AtNRemove(indice);
        }
        return null;
    }
}
