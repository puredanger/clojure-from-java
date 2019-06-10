package cfj;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import java.util.Date;

public class Support {

  static {
    IFn require = Clojure.var("clojure.core", "require");
    require.invoke(Clojure.read("cfj.core"));
  }
    
  private static final IFn newEvent = Clojure.var("cfj.core", "->EventImpl");

  public static Event createEvent(String name) {
    return (Event) newEvent.invoke(name, new Date());
  }
}
