package apsd.classes.containers.sequences;

 import apsd.classes.containers.sequences.abstractbases.DynLinearVectorBase;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Concrete dynamic (linear) vector implementation. */
public class DynVector<Data> extends DynLinearVectorBase<Data> { // Must extend DynLinearVectorBase
    public DynVector() {
        super();
    }
    public DynVector(Natural capacitaIniziale) 
    { 
        // passo la capacità al costruttore della base
        super(capacitaIniziale); 
    }

    public DynVector(TraversableContainer<Data> contenitoreInput) 
    { 
        // costruisco copiando da un altro contenitore
        super(contenitoreInput); 
    }

    protected DynVector(Data[] arrayDati) 
    { 
        // costruttore protetto per l'array diretto
        super(arrayDati); 
    }

    @Override
    protected DynVector<Data> NewVector(Data[] arrayDati) 
    { 
        // metodo factory: ritorno una nuova istanza di questo vettore
        return new DynVector<>(arrayDati); 
    }
}
