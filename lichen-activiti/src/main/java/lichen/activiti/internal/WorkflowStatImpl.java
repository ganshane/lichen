package lichen.activiti.internal;


import lichen.activiti.models.WorkflowStat;

/**
 * 工作流任务的状态
 * @author jcai
 */
class WorkflowStatImpl implements WorkflowStat {
	/* (non-Javadoc)
	 * @see creeper.core.models.WorkflowStat#getUnsigned()
	 */
    @Override
	public long getUnsigned() {
        return unsigned;
    }

    public void setUnsigned(long unsigned) {
        this.unsigned = unsigned;
    }

    /* (non-Javadoc)
	 * @see creeper.core.models.WorkflowStat#getTodo()
	 */
    @Override
	public long getTodo() {
        return todo;
    }

    public void setTodo(long todo) {
        this.todo = todo;
    }

    /* (non-Javadoc)
	 * @see creeper.core.models.WorkflowStat#getUnfinished()
	 */
    @Override
	public long getUnfinished() {
        return unfinished;
    }

    public void setUnfinished(long unfinished) {
        this.unfinished = unfinished;
    }

    /* (non-Javadoc)
	 * @see creeper.core.models.WorkflowStat#getFinished()
	 */
    @Override
	public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }

    /**待签收**/
    private long unsigned;
    /** 待处置 **/
    private long todo;
    /** 未完成 **/
    private long unfinished;
    /** 已完成 **/
    private long finished;
}
