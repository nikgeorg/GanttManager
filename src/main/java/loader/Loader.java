package loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dom.ChildTask;
import dom.Task;
import dom2app.SimpleTableModel;


public class Loader {
	public ArrayList<Task> tasks = new ArrayList<>();
	private String[] columnNames = {"Task ID", "Task Name", "Mama ID", "Start", "End", "Cost"};
	private ArrayList<Task> topTasksArr = new ArrayList<>();
	private ArrayList<ChildTask> childTasksArr = new ArrayList<>();
	public String thisFileName;
	
	/** Creates a task from the contents parameter by splitting the string with the given delimiter.
	 * The following format is assumed: 
	 * TaskId, delimiter, TaskName, delimiter, MamaId, delimiter, StartTime, delimiter, EndTime, delimiter, Cost
	 * 
	 * For top level tasks the above format is assumed but stopping at the MamaId field.
	 * 
	 * @param contents A string that contains the task.
	 * @param delimiter The delimiter that separates the above fields.
	 * 
	 * @return A Task object if it's top level, otherwise a ChildTask object.
	 */
	
	public Task createTask(String contents, String delimiter) {
		int thisStartTime = 0, thisEndTime = 0, thisCost = 0;
		String[] taskElements = contents.split(delimiter);
					int thisId = Integer.parseInt(taskElements[0]);
				// gets task NAME
					String thisName = taskElements[1];
				// gets task Mama ID
					int thisMamaId = Integer.parseInt(taskElements[2]);
				// for standalone top level tasks or child ones, gets start time, end time, cost respectively
					if (taskElements.length > 3) {
						thisStartTime = Integer.parseInt(taskElements[3]);
						thisEndTime = Integer.parseInt(taskElements[4]);
						thisCost = Integer.parseInt(taskElements[5]);
					}
					else {
						thisStartTime = 0;
						thisEndTime = 0;
						thisCost = 0;
					}
		// verifies if the method parameter equals what is expected to be given by the method normally, that is,
		// each task field separated by tabs
		if (thisMamaId > 0) {
			ChildTask task = new ChildTask(thisId, thisName, thisMamaId, thisStartTime, thisEndTime, thisCost);
			return task;
		}
		else {
		Task task = new Task(thisId, thisName, thisMamaId, thisStartTime, thisEndTime, thisCost);
		return task;
		}
	}
	
	/**
	 * Converts a given Task object back to a string, with fields delimited by a '\t' character.
	 * @param t A Task object.
	 * @return A string representation of the task.
	 */
	
	public String taskToString(Task t) {
		String taskIdToString = Integer.toString(t.getTaskId());
		String taskName = t.getTaskName();
		String mamaIdToString = Integer.toString(t.getMamaId());
		String startTimeToString = Integer.toString(t.getStartTime());
		String endTimeToString = Integer.toString(t.getEndTime());
		String costToString = Integer.toString(t.getCost());
		String finalArr = taskIdToString + "\t" + taskName + "\t" + mamaIdToString + "\t" + startTimeToString + "\t" + endTimeToString + "\t" + costToString;
		return finalArr;
	}
	
	/**
	 * Sorts the tasks given by the list and returns a new list where the top level tasks are kept,
	 * with their children (if they have any) kept in as fields of each Task object.
	 * @see dom.Task
	 * @param list An ArrayList that has all tasks which have been loaded from a file.
	 * @return A sorted ArrayList of the top level tasks, where their children fields are also sorted.
	 */
	
	public ArrayList<Task> sortTasks(ArrayList<Task> list) {
		
		for (Task t: list) {
			if (t.getMamaId() == 0) {
				topTasksArr.add(t);
			}
			else getChildTasksArr().add((ChildTask) t);
		}
		
		Comparator<Task> compar = Comparator.comparing(Task::getStartTime)
				.thenComparing(Comparator.comparing(Task::getTaskId));
		
		topTasksArr.stream().sorted(compar);
		
		Collections.sort(getChildTasksArr(), compar);
		
		int i = 0, j = 0;
		// change the code so that we keep the current mama id of each task in the loop,
		// then reiterate once it's gone through all of them and let it finish once it's found
		// all children
		
		while (j < getChildTasksArr().size() && i < topTasksArr.size()) {
			if (topTasksArr.get(i).getTaskId() == getChildTasksArr().get(j).getMamaId()) {
				topTasksArr.get(i).children.add(getChildTasksArr().get(j));
			}
			else if (j == getChildTasksArr().size() - 1 && i < topTasksArr.size()) {
				j = 0;
				i++;
				continue;
			}
			else if (j == getChildTasksArr().size() - 1 && i == topTasksArr.size() - 1) break;
			j++;
		}
		
		for (Task t: getTopTasksArr()) {
			if (t.children.isEmpty()) continue;
			Collections.sort(t.children, compar);
		}
		
		return (ArrayList<Task>) topTasksArr;
	}
	
	public SimpleTableModel load(String fileName, String delimiter) {
		List<String[]> data = new ArrayList<>();
		File file = new File(fileName);
		try {
			BufferedReader bufReader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = bufReader.readLine()) != null) {
				// load all tasks in the array list
				if (!Character.isDigit(line.charAt(0))) {
					break;
				}
				tasks.add(createTask(line, delimiter));
			}
			bufReader.close();
			// initialize table variables for the constructor
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// IGNORE THIS COMMENT BLOCK
		
		// arrays for keeping index of tasks in the original array, and one for the tasks with same start time respectively
		/* ArrayList<Integer> indexOfSameStartTimeTasks = new ArrayList<>();
		ArrayList<Task> listOfSameStartTimeTasks = new ArrayList<>();
		
		 
		for (int i = 0; i < listOfSameStartTimeTasks.size(); i++) {
			Collections.sort(listOfSameStartTimeTasks, compareByTaskId);
		}
		Set<Task> startTimeTasksWithoutDuplicates = new LinkedHashSet<Task>(listOfSameStartTimeTasks);
		Set<Integer> indexofStartTimeTasksWithoutDuplicates = new LinkedHashSet<Integer>(indexOfSameStartTimeTasks);
		
		listOfSameStartTimeTasks.clear();
		indexOfSameStartTimeTasks.clear();
		startTimeTasksWithoutDuplicates.addAll(listOfSameStartTimeTasks);
		indexofStartTimeTasksWithoutDuplicates.addAll(indexOfSameStartTimeTasks);
		
		for (Task t: startTimeTasksWithoutDuplicates) {
			listOfSameStartTimeTasks.add(t);
		}
		
		for (Integer i: indexofStartTimeTasksWithoutDuplicates) {
			for (int j = 0; j < listOfSameStartTimeTasks.size(); j++) {
				tasks.set(i, listOfSameStartTimeTasks.get(j));
			}
		}
		*/
		setTopTasksArr(sortTasks(tasks));
		for (Task t: getTopTasksArr()) {
			t.setStartTime(t.computeStartTimeForComplexTask());
			t.setEndTime(t.computeEndTimeForComplexTask());
			t.setCost(t.computeCostForComplexTask());
			data.add(taskToString(t).split(delimiter));
			for (ChildTask ct: t.children) {
				data.add(taskToString(ct).split(delimiter));
			}
		}
		
		SimpleTableModel table = new SimpleTableModel("Loaded tasks", file.getName(), columnNames, data);
		thisFileName = file.getName();
		return table;
	}

	public ArrayList<Task> getTopTasksArr() {
		return topTasksArr;
	}

	public void setTopTasksArr(ArrayList<Task> TopTasksArr) {
		this.topTasksArr = TopTasksArr;
	}

	public ArrayList<ChildTask> getChildTasksArr() {
		return childTasksArr;
	}

	public void setChildTasksArr(ArrayList<ChildTask> childTasksArr) {
		this.childTasksArr = childTasksArr;
	}
}
