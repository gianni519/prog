package apsd.classes.containers.sequences;

 import apsd.classes.containers.sequences.abstractbases.DynCircularVectorBase;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Concrete dynamic circular vector implementation. */
public class DynCircularVector<Data>  extends DynCircularVectorBase<Data>{ // Must extend DynCircularVectorBase

	public DynCircularVector() 
    {
       
    }

    public DynCircularVector(Natural capacitaIniziale) 
    { 
       
        super(capacitaIniziale); 
    }

    public DynCircularVector(TraversableContainer<Data> contenitoreInput) 
    { 
       
        super(contenitoreInput); 
    }

    protected DynCircularVector(Data[] arrayDati) 
    { 
      
        super(arrayDati); 
    }

    @Override
    protected DynCircularVector<Data> NewVector(Data[] arrayDati) 
    { 
      
        return new DynCircularVector<>(arrayDati); 
    }

}
