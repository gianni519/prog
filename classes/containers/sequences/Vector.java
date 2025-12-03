package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.LinearVectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Concrete (static linear) vector implementation. */
public class Vector<Data> extends LinearVectorBase<Data> 
{

    public Vector() 
    {
      
    }

    public Vector(Natural capacitaIniziale) 
    { 
        super(capacitaIniziale); 
    }

    public Vector(TraversableContainer<Data> contenitoreSorgente) 
    { 
        super(contenitoreSorgente); 
    }

    protected Vector(Data[] arrayInput) 
    { 
       
        super(arrayInput); 
    }

    @Override
    protected Vector<Data> NewVector(Data[] arrayInput) 
    { 

        return new Vector<>(arrayInput); 
    }

	
}