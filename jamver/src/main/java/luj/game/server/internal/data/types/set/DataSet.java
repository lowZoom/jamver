package luj.game.server.internal.data.types.set;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import luj.game.server.internal.data.types.HasOp;
import luj.game.server.internal.data.types.set.history.SetDataAdder;
import luj.game.server.internal.data.types.set.history.SetWithHistory;

public class DataSet<E> implements Set<E>, HasOp {

  DataSet(SetWithHistory<E> value, SetOp op) {
    _value = value;
    _op = op;
  }

  @Override
  public int size() {
    return _value.getData().size();
  }

  @Override
  public boolean isEmpty() {
    return _value.getData().isEmpty();
  }

  @Override
  public Iterator<E> iterator() {
    return _value.getData().iterator();
  }

  @Override
  public boolean contains(Object o) {
    throw new UnsupportedOperationException("");
  }

  @Override
  public boolean add(E e) {
    return SetDataAdder.GET.add(_value, e);
  }

  @Override
  public boolean remove(Object o) {
    throw new UnsupportedOperationException("");
  }

  @Override
  public String toString() {
    return _value.toString();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getDataOp() {
    return (T) _op;
  }

  @Override
  public Object[] toArray() {
    throw new UnsupportedOperationException("toArray");
  }

  @Override
  public <T> T[] toArray(T[] a) {
    throw new UnsupportedOperationException("toArray");
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    throw new UnsupportedOperationException("");
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    throw new UnsupportedOperationException("");
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException("");
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    throw new UnsupportedOperationException("");
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException("");
  }

  final SetWithHistory<E> _value;

  final SetOp _op;
}
