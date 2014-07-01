package creeper.core.models;

/**
 * 工作流的任务状态
 * @author jcai
 *
 */
public interface WorkflowStat {

	/**
	 * 得到未签收的任务数
	 * @return 未签收的任务数
	 */
	public abstract long getUnsigned();

	/**
	 * 得到待处理的任务数
	 * @return 待处理任务数
	 */
	public abstract long getTodo();

	/**
	 * 得到未完成的任务数
	 * @return 未完成的任务数
	 */
	public abstract long getUnfinished();

	/**
	 * 得到已经完成的任务数
	 * @return 已经完成的任务数
	 */
	public abstract long getFinished();

}