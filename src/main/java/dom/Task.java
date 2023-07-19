package dom;

import java.util.Objects;
import java.util.ArrayList;

public class Task {
	public Task(int taskId, String taskName, int mamaId, int startTime, int endTime, int cost) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.mamaId = mamaId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.cost = cost;
	}
	private Integer taskId;
	private String taskName;
	private int mamaId;
	private Integer startTime;
	private int endTime;
	private int cost;
	public ArrayList<ChildTask> children = new ArrayList<>();
	
	public Integer getStartTime() {
		return startTime;
	}


	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}


	public int getEndTime() {
		return endTime;
	}


	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}


	public int getCost() {
		return cost;
	}


	public void setCost(int cost) {
		this.cost = cost;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cost, endTime, mamaId, startTime, taskId, taskName);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return cost == other.cost && endTime == other.endTime && mamaId == other.mamaId && startTime == other.startTime
				&& taskId == other.taskId && Objects.equals(taskName, other.taskName);
	}


	public Integer getTaskId() {
		return taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setMamaId(int mamaId) {
		this.mamaId = mamaId;
	}

	public int getMamaId() {
		return mamaId;
	}
	
	public boolean checkIfTopLevel() {
		if (this.mamaId == 0) return true;
		else return false;
	}
	
	public boolean isStandalone() {
		if (this.mamaId == 0 && this.cost == 0) return true;
		return false;
	}
	
	public int computeStartTimeForComplexTask() {
		if (!this.children.isEmpty()) {
			int start = this.children.get(0).getStartTime();
			return start;
		}
		return this.startTime;
	}
	
	public int computeEndTimeForComplexTask() {
		if (!this.children.isEmpty()) {
			int end = this.children.get(this.children.size() - 1).getEndTime();
			return end;
		}
		return this.endTime;
	}
	
	public int computeCostForComplexTask() {
		int cost = 0;
		if (!this.children.isEmpty()) {
			for (ChildTask ct: this.children) {
				cost += ct.getCost();
			}
			return cost;
		}
		return this.cost;
	}
	
	public void addChildrenTasks(ChildTask ct) {
		children.add(ct);
	}
	
	
}
