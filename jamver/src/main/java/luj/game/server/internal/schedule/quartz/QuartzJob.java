package luj.game.server.internal.schedule.quartz;

import java.util.function.BiFunction;
import luj.game.server.api.data.service.CommandService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

public class QuartzJob implements Job {

  @Override
  public void execute(JobExecutionContext context) {
    JobDataMap dataMap = context.getJobDetail().getJobDataMap();

    CommandService<Object> commandSvc = getValue(dataMap, "cmd.service");
    BiFunction<CommandService.Param, Object, CommandService.Param> param =
        getValue(dataMap, "cmd.param");

    commandSvc.execute(param);
  }

  @SuppressWarnings("unchecked")
  private <T> T getValue(JobDataMap map, String key) {
    return (T) map.get(key);
  }
}
