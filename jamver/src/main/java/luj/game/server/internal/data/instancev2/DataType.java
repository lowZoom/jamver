package luj.game.server.internal.data.instancev2;

public class DataType {

  public DataType(String name, boolean aTransient) {
    _name = name;
    _transient = aTransient;
  }

  public String getName() {
    return _name;
  }

  public boolean isTransient() {
    return _transient;
  }

  private final String _name;
  private final boolean _transient;
}
