package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.sequences.DynVector;

/** Object: Abstract dynamic circular vector base implementation. */
abstract public class DynCircularVectorBase<Data>  extends CircularVectorBase<Data> implements DynVector<Data>{ // Must extend CircularVectorBase and implement DynVector

  protected long size = 0L;

  public DynCircularVectorBase() 
  { 
      // costruttore vuoto
  }

  public DynCircularVectorBase(Natural capacitaIniziale) 
  { 
      super(capacitaIniziale); 
  }

  public DynCircularVectorBase(TraversableContainer<Data> contenitoreInput) 
  { 
      super(contenitoreInput); 
      // copio la dimensione
      size = contenitoreInput.Size().ToLong(); 
  }

  public DynCircularVectorBase(Data[] arrayInput) 
  { 
      super(arrayInput); 
      // imposto la size pari alla lunghezza dell'array
      size = arrayInput.length; 
  }

  @Override
  protected void ArrayAlloc(Natural capacitaDesiderata) 
  {
      if (capacitaDesiderata == null) 
      { 
          throw new NullPointerException("Natural cannot be null!"); 
      }
      
      // definisco una capacità minima di 1
      final long CAPACITA_BASE = 1L;
      
      // se la capacità richiesta è minore della base, uso la base
      Natural capacitaEffettiva = (capacitaDesiderata.ToLong() < CAPACITA_BASE) ? 
                                   Natural.Of(CAPACITA_BASE) : 
                                   capacitaDesiderata;

      // chiamo l'allocazione della classe padre
      super.ArrayAlloc(capacitaEffettiva);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  public Natural Size() 
  { 
      return Natural.Of(size); 
  }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  @Override
  public void Clear() 
  {
    

      super.Clear();
      size = 0L;
  }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  @Override
  public void Realloc(Natural nuovaCapacita) 
  {
      super.Realloc(nuovaCapacita);
    

      size = Math.min(size, arr.length);
  }

  /* ************************************************************************ */
  /* Override specific member functions from ResizableContainer               */
  /* ************************************************************************ */

  @Override
  public void Expand(Natural quantitaAggiunta) 
  {
      // gestisco l'espansione della memoria fisica
      DynVector.super.Grow(quantitaAggiunta);
      // aggiorno la dimensione logica sommando i nuovi elementi
      size += quantitaAggiunta.ToLong();
  }

  @Override
  public void Reduce(Natural quantitaRimossa) 
  {
      if (quantitaRimossa == null) 
      {
          throw new NullPointerException("Natural number cannot be null!");
      }
      
      long valoreDaTogliere = quantitaRimossa.ToLong();
      
      // controllo che non rimuova più elementi di quanti ce ne siano
      if (size < valoreDaTogliere) 
      { 
          throw new ArithmeticException("Underflow: size cannot be negative!"); 
      }
      
      size -= valoreDaTogliere;
      
      // gestisco il restringimento della memoria fisica se necessario
      DynVector.super.Shrink();
  }

  /* ************************************************************************ */
  /* Specific member functions of Vector                                      */
  /* ************************************************************************ */

  @Override
  public void ShiftLeft(Natural indicePartenza, Natural spostamento) 
  {
      // sposto gli elementi a sinistra
      super.ShiftLeft(indicePartenza, spostamento);
      // riduco la dimensione perché gli elementi shiftati "escono"
      Reduce(spostamento);
  }

  @Override
  public void ShiftRight(Natural indicePartenza, Natural spostamento) 
  {
      // espando prima la dimensione per fare spazio
      Expand(spostamento);
      // sposto gli elementi a destra
      super.ShiftRight(indicePartenza, spostamento);
  }

}
