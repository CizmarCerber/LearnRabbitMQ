package by.cizmar.internal.learn_rabbit_mq.scheduler;

import by.cizmar.internal.learn_rabbit_mq.scheduler.task.RunnableTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledTasksManager {

    private final TaskScheduler scheduler;

    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public void runScheduledTask(RunnableTask task, Duration period) {
        cancelTaskIfExists(task.getTitle());

        log.info("Run task {} with period {} ms", task.getTitle(), period.toMillis());
        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(task, period);
        scheduledTasks.put(task.getTitle(), future);
    }

    public void cancelTaskIfExists(String taskName) {
        ScheduledFuture<?> existing = scheduledTasks.get(taskName);
        if (existing != null) {
            log.info("Abort execution of task {}", taskName);
            existing.cancel(true);
        }
    }

}
