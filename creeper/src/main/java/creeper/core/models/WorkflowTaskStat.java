package creeper.core.models;

/**
 * 工作流任务的状态
 * @author jcai
 */
public class WorkflowTaskStat {
    public long getUnsigned() {
        return unsigned;
    }

    public void setUnsigned(long unsigned) {
        this.unsigned = unsigned;
    }

    public long getTodo() {
        return todo;
    }

    public void setTodo(long todo) {
        this.todo = todo;
    }

    public long getUnfinished() {
        return unfinished;
    }

    public void setUnfinished(long unfinished) {
        this.unfinished = unfinished;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }

    private long unsigned;
    private long todo;
    private long unfinished;
    private long finished;
}
