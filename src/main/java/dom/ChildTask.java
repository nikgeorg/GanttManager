package dom;

public class ChildTask extends Task {
	public ChildTask(int taskId, String taskName, int mamaId, int startTime, int endTime, int cost) {
	super(taskId, taskName, mamaId,startTime, endTime, cost);
	}
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	public boolean checkParentTask(Task t) {
		if (t.getTaskId() == this.getMamaId()) return true;
		return false;
	}

}