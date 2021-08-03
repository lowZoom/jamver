package luj.game.server.internal.data.id.state;

import luj.game.server.api.plugin.JamverDataIdGenNext;

public class DataIdGenState {

  public DataIdGenState(JamverDataIdGenNext<?> generatePlugin) {
    _generatePlugin = generatePlugin;
  }

  public Object getAppState() {
    return _appState;
  }

  public void setAppState(Object appState) {
    _appState = appState;
  }

  public String getIdField() {
    return _idField;
  }

  public void setIdField(String idField) {
    _idField = idField;
  }

  public JamverDataIdGenNext<?> getGeneratePlugin() {
    return _generatePlugin;
  }

  private Object _appState;

  private String _idField;
  private Comparable<?> _globalId;

  private final JamverDataIdGenNext<?> _generatePlugin;
}
