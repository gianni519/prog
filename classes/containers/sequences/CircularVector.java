package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.CircularVectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Concrete (static) circular vector implementation. */
public class CircularVector<Data>  extends CircularVectorBase<Data>{ // Must extend CircularVectorBase

	public CircularVector() 
    {  
    
    }

    public CircularVector(Natural dimensioneIniziale) 
    { 
        super(dimensioneIniziale); 
    }

    public CircularVector(TraversableContainer<Data> contenitoreSorgente) 
    { 
        super(contenitoreSorgente); 
    }

    protected CircularVector(Data[] arrayDati) 
    { 
        super(arrayDati); 
    }

    @Override
    protected CircularVector<Data> NewVector(Data[] arrayDati) 
    { 
        return new CircularVector<>(arrayDati); 
    }

}
