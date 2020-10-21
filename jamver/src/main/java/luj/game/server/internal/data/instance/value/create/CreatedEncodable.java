package luj.game.server.internal.data.instance.value.create;

public interface CreatedEncodable {

  /**
   * 序列化新建时的快照，避免后续被修改
   */
  Object encodeInit();
}
