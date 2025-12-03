package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.sequences.DynVector;

/** Object: Abstract dynamic linear vector base implementation. */
public abstract class DynLinearVectorBase<Data> extends LinearVectorBase<Data> implements DynVector<Data> 
{
    protected long size = 0L;

    public DynLinearVectorBase() 
    { 
       

    }

    public DynLinearVectorBase(Natural capacitaIniziale) 
    { 
        super(capacitaIniziale); 
    }

    public DynLinearVectorBase(TraversableContainer<Data> contenitoreInput) 
    { 
        super(contenitoreInput); 
       
        size = contenitoreInput.Size().ToLong(); 
    }

    public DynLinearVectorBase(Data[] arrayInput) 
    { 
        super(arrayInput); 
        
        size = arrayInput.length; 
    }

    @Override
    protected void ArrayAlloc(Natural nuovaCapacita) 
    {
        if (nuovaCapacita == null) 
        { 
            throw new NullPointerException("Natural cannot be null!"); 
        }
        
        final long CAPACITA_MINIMA = 1L;
        
        // calcolo quale capacità usare: non deve essere inferiore al minimo
        Natural capacitaEffettiva = (nuovaCapacita.ToLong() < CAPACITA_MINIMA) ? 
                                     Natural.Of(CAPACITA_MINIMA) : 
                                     nuovaCapacita;

        super.ArrayAlloc(capacitaEffettiva);
    }

    /**************************************************************************/
    /* Override specific member functions from Container                      */
    /**************************************************************************/

    @Override
    public Natural Size() 
    { 
        return Natural.Of(size); 
    }

    /**************************************************************************/
    /* Override specific member functions from ClearableContainer             */
    /**************************************************************************/

    @Override
    public void Clear() 
    {
      

        super.Clear();
        size = 0L;
    }

    /**************************************************************************/
    /* Override specific member functions from ReallocableContainer           */
    /**************************************************************************/

    @Override
    public void Realloc(Natural nuovaCapacita) 
    {
        super.Realloc(nuovaCapacita);
      

        size = Math.min(size, arr.length);
    }

    /**************************************************************************/
    /* Override specific member functions from ResizableContainer             */
    /**************************************************************************/

    @Override
    public void Expand(Natural incremento) 
    {
        DynVector.super.Grow(incremento);
       
        size += incremento.ToLong();
    }

    @Override
    public void Reduce(Natural decremento) 
    {
        if (decremento == null) 
        {
            throw new NullPointerException("Natural number cannot be null!");
        }
        
        long valoreDaRimuovere = decremento.ToLong();
        
        
        if (size < valoreDaRimuovere) 
        { 
            throw new ArithmeticException("Underflow: size cannot be negative!"); 
        }
        
        size -= valoreDaRimuovere;
        
     
        DynVector.super.Shrink();
    }
}