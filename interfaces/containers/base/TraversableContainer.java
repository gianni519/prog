package apsd.interfaces.containers.base;

import apsd.classes.utilities.Box;
import apsd.classes.utilities.MutableNatural;
import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Accumulator;
import apsd.interfaces.traits.Predicate;


/** Interface: MembershipContainer con supporto all'attraversamento. */
public interface TraversableContainer<Data> extends MembershipContainer<Data> 
{
    boolean TraverseForward(Predicate<Data> predicato);
    boolean TraverseBackward(Predicate<Data> predicato);

    default <Acc> Acc FoldForward(Accumulator<Data, Acc> accumulatore, Acc valoreIniziale) 
    {
        // "Scatola" per il risultato che si accumula
        final Box<Acc> risultatoParziale = new Box<>(valoreIniziale);
        
        if (accumulatore != null) 
        {
            TraverseForward(elemento -> { 
                // applico la funzione di accumulo
                risultatoParziale.Set(accumulatore.Apply(elemento, risultatoParziale.Get())); 
                return false; // continua a scorrere
            });
        }
        
        return risultatoParziale.Get();
    }

    default <Acc> Acc FoldBackward(Accumulator<Data, Acc> accumulatore, Acc valoreIniziale) 
    {
        // "Scatola" per il risultato che si accumula
        final Box<Acc> risultatoParziale = new Box<>(valoreIniziale);
        
        if (accumulatore != null) 
        {
            TraverseBackward(elemento -> { 
                // uguale a sopra, ma scorrendo al contrario
                risultatoParziale.Set(accumulatore.Apply(elemento, risultatoParziale.Get())); 
                return false; // continua a scorrere
            });
        }
        
        return risultatoParziale.Get();
    }

    /******************************************************************/
    /* Overridden specific member functions from Container            */
    /******************************************************************/
    
    @Override
    default Natural Size() 
    {
        // uso un contatore mutabile per contare gli elementi
        final MutableNatural contatore = MutableNatural.Of(0);
        
        // scorro tutto e incremento
        TraverseForward(dato -> { 
            contatore.Increment(); 
            return false; // continua
        });
        
        // ritorno il valore "immutabile"
        return Natural.Of(contatore);
    }

    /******************************************************************/
    /* Overridden specific member functions from MembershipContainer  */
    /******************************************************************/

    @Override
    default boolean Exists (Data elementoCercato) 
    {
        // cerco l'elemento. Il predicato ritorna 'true' quando lo trova
        return TraverseForward(elementoCorrente -> 
            // gestisco il caso null
            (elementoCorrente == null && elementoCercato == null) || 
            // gestisco il caso non-null
            (elementoCorrente != null && elementoCercato != null && elementoCorrente.equals(elementoCercato))
        );
    }
}