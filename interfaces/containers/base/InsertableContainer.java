package apsd.interfaces.containers.base;
import apsd.classes.utilities.Box;

/** Interface: Container con supporto all'inserimento di un dato. */
public interface InsertableContainer<Data> extends Container { // Must extend Container

  // Insert
	boolean Insert (Data dat);

  // InsertAll

	default boolean InsertAll(TraversableContainer<Data> conn) {
		final Box<Boolean> flag = new Box<Boolean>(true);
		if(conn!= null) conn.TraverseForward(dat -> {flag.Set(flag.Get() && Insert(dat)); return false;});
		return flag.Get();
	}
	
  // InsertSome
	default boolean InsertSome(TraversableContainer<Data> conn) {
		final Box<Boolean> flag = new Box<Boolean>(false);
		if(conn!= null) conn.TraverseForward(dat -> {flag.Set(flag.Get() || Insert(dat)); return false;});
		return flag.Get();
	}
	
	
}
