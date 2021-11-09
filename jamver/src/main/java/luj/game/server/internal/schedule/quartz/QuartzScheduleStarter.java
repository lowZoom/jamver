package luj.game.server.internal.schedule.quartz;

import com.google.common.collect.ImmutableSet;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.function.BiFunction;
import luj.game.server.api.data.service.CommandService;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public enum QuartzScheduleStarter {
  GET;

  public void start(Scheduler quartz, String command, BiFunction<?, ?, ?> param,
      CommandService<?> commandSvc, Comparable<?> id, Duration delay) {
    JobDataMap dataMap = new JobDataMap();
    dataMap.put("cmd.service", commandSvc);
    dataMap.put("cmd.param", param);

    JobDetail job = JobBuilder.newJob(QuartzJob.class)
        .withIdentity("job_" + id, command)
        .usingJobData(dataMap)
        .build();

    Trigger trigger = TriggerBuilder.newTrigger()
        .withIdentity("trigger_" + id, command)
        .startAt(new Date(Instant.now().plus(delay).toEpochMilli()))
        .build();
//        .startNow()
//        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//            .withIntervalInSeconds(1)
//            .repeatForever())
//        .build();

    try {
      quartz.scheduleJob(job, ImmutableSet.of(trigger), true);
    } catch (SchedulerException e) {
      throw new UnsupportedOperationException(e);
    }
  }
}
