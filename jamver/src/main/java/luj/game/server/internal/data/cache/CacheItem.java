package luj.game.server.internal.data.cache;

import luj.game.server.internal.data.instance.DataTempProxy;
import luj.game.server.internal.data.instancev2.DataEntity;
import luj.game.server.internal.data.instancev2.DataType;

public class CacheItem {

  public static CacheItem create(Class<?> dataType) {
    return new CacheItem(dataType, null);
  }

  public static CacheItem create(DataType dataType) {
    return new CacheItem(null, dataType);
  }

  public DataPresence getPresence() {
    return _presence;
  }

  public void setPresence(DataPresence presence) {
//    StackTraceElement[] chain = Thread.currentThread().getStackTrace();
//    LOG.debug("presence#{}：{} -> {}，{}", _dataTypeV2, _presence, presence, Arrays.stream(chain)
//        .map(e -> e.getClassName() + "#" + e.getMethodName())
//        .skip(1)
//        .filter(s -> !s.startsWith("java."))
//        .filter(s -> !s.startsWith("scala."))
//        .filter(s -> !s.startsWith("akka."))
//        .filter(s -> !s.startsWith("io.netty."))
//        .filter(s -> !s.startsWith("org.springframework."))
//        .collect(Collectors.joining(",")));
    _presence = presence;
  }

  public String getLocker() {
    return _locker;
  }

  public void setLocker(String locker) {
    _locker = locker;
  }

  public DataTempProxy getDataObj() {
    return _dataObj;
  }

  public void setDataObj(DataTempProxy dataObj) {
    _dataObj = dataObj;
  }

  public DataEntity getDataObjV2() {
    return _dataObjV2;
  }

  public void setDataObjV2(DataEntity dataObjV2) {
    _dataObjV2 = dataObjV2;
  }

  public Class<?> getDataType() {
    return _dataType;
  }

  public DataType getDataTypeV2() {
    return _dataTypeV2;
  }

  CacheItem(Class<?> dataType, DataType dataTypeV2) {
    _dataType = dataType;
    _dataTypeV2 = dataTypeV2;
  }

//  private static final Logger LOG = LoggerFactory.getLogger(CacheItem.class);

  private DataPresence _presence;
  private String _locker;

  private DataTempProxy _dataObj;
  private DataEntity _dataObjV2;

  private final Class<?> _dataType;
  private final DataType _dataTypeV2;
}
