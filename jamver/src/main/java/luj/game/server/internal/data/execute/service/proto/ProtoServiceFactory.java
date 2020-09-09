package luj.game.server.internal.data.execute.service.proto;

import luj.bean.api.BeanContext;
import luj.game.server.api.data.GameDataCommand;

public class ProtoServiceFactory {

  public ProtoServiceFactory(BeanContext lujbean) {
    _lujbean = lujbean;
  }

  public GameDataCommand.Proto create() {
    ProtoServiceImpl svc = new ProtoServiceImpl();
    svc._lujbean = _lujbean;
    return svc;
  }

  private final BeanContext _lujbean;
}
