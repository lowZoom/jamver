package luj.game.server.internal.data.execute.service.proto;

import luj.bean.api.BeanContext;
import luj.game.server.api.data.GameDataCommand;

public enum ProtoServiceFactory {
  GET;

  public GameDataCommand.Proto create(BeanContext lujbean) {
    ProtoServiceImpl svc = new ProtoServiceImpl();
    svc._lujbean = lujbean;
    return svc;
  }
}
