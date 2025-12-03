package apsd.interfaces.containers.base;



import apsd.classes.utilities.Box;

//** Interface: Contenitore con supporto alla rimozione di un dato. */
public interface RemovableContainer<Data> extends Container
{
    boolean Remove(Data valore);

    default boolean RemoveAll(TraversableContainer<Data> altroContenitore) 
    {
        // serve un "contenitore" per la variabile da usare nella lambda
        final Box<Boolean> esito = new Box<>(true);

        if (altroContenitore != null) 
        {
            // scorro tutti gli elementi dell'altro contenitore
            altroContenitore.TraverseForward(datoSingolo -> { 
                // aggiorno il risultato (basta che uno fallisca per rendere tutto false)
                esito.Set(esito.Get() && Remove(datoSingolo)); 
                return false; // continua a scorrere
            });
        }
        
        return esito.Get();
    }

    default boolean RemoveSome(TraversableContainer<Data> altroContenitore) 
    {
        // questa volta inizio da false
        final Box<Boolean> almenoUnSuccesso = new Box<>(false);

        if (altroContenitore != null) 
        {
            altroContenitore.TraverseForward(datoSingolo -> { 
                // aggiorno: qui basta che uno solo sia true per rendere tutto true
                almenoUnSuccesso.Set(almenoUnSuccesso.Get() || Remove(datoSingolo)); 
                return false; // continua a scorrere
            });
        }

        return almenoUnSuccesso.Get();
    }
}