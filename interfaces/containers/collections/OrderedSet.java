package apsd.interfaces.containers.collections;

public interface OrderedSet<Data extends Comparable<? super Data>> extends Set<Data> 
{

    default Data Min() 
    {
        
        return FoldForward(
            (elementoCorrente, minimoAttuale) -> 
            {
                
                if (minimoAttuale == null || elementoCorrente.compareTo(minimoAttuale) < 0)
                {
                    return elementoCorrente;
                }
                else
                {
                    return minimoAttuale;
                }
            }, 
            null
        );
    }

    default void RemoveMin() 
    { 
        Remove(Min()); 
    }

    default Data MinNRemove() 
    {
        Data valoreMinimo = Min();
        Remove(valoreMinimo);
        return valoreMinimo;
    }

    default Data Max() 
    {
        // logica speculare al Min: cerco il più grande
        return FoldForward(
            (elementoCorrente, massimoAttuale) -> 
            {
                if (massimoAttuale == null || elementoCorrente.compareTo(massimoAttuale) > 0)
                {
                    return elementoCorrente;
                }
                else
                {
                    return massimoAttuale;
                }
            }, 
            null
        );
    }

    default void RemoveMax() 
    { 
        Remove(Max()); 
    }

    default Data MaxNRemove() 
    {
        Data valoreMassimo = Max();
        Remove(valoreMassimo);
        return valoreMassimo;
    }

   

    default Data Predecessor(Data valoreRiferimento) 
    {
       
        return FoldForward(
            (candidato, predecessoreMigliore) -> 
            {
                
                if (candidato.compareTo(valoreRiferimento) < 0 && 
                   (predecessoreMigliore == null || candidato.compareTo(predecessoreMigliore) > 0))
                {
                    return candidato;
                }
                
                return predecessoreMigliore;
            }, 
            null
        );
    }

    default void RemovePredecessor(Data valoreRiferimento) 
    { 
        Remove(Predecessor(valoreRiferimento)); 
    }

    default Data PredecessorNRemove(Data valoreRiferimento) 
    {
        Data predecessore = Predecessor(valoreRiferimento);
        Remove(predecessore);
        return predecessore;
    }

    default Data Successor(Data valoreRiferimento) 
    {
        
        return FoldForward(
            (candidato, successoreMigliore) -> 
            {
               
                if (candidato.compareTo(valoreRiferimento) > 0 && 
                   (successoreMigliore == null || candidato.compareTo(successoreMigliore) < 0))
                {
                    return candidato;
                }

                return successoreMigliore;
            }, 
            null
        );
    }

    default void RemoveSuccessor(Data valoreRiferimento) 
    { 
        Remove(Successor(valoreRiferimento)); 
    }

    default Data SuccessorNRemove(Data valoreRiferimento) 
    {
        Data successore = Successor(valoreRiferimento);
        Remove(successore);
        return successore;
    }
}


