package backend;

import dom2app.SimpleTableModel;
import loader.Loader;
import reports.Report;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import dom.Task;

public class GanttLoader implements IMainController {
	private String[] columnNames = {"Task ID", "Task Name", "Mama ID", "Start", "End", "Cost"};
	Loader loader = new Loader();
	
	
	public boolean isSorted = false;
	
	public void clearArrays() {
		loader.tasks.clear();
		loader.getTopTasksArr().clear();
		loader.getChildTasksArr().clear();
	}
	
	
	public SimpleTableModel load(String fileName, String delimiter) {
		SimpleTableModel table = new SimpleTableModel("Loaded tasks", null, columnNames, null);
		table = loader.load(fileName, delimiter);
		return table;
	}
	
	@Override
	public SimpleTableModel getTasksByPrefix(String prefix) {
		List<String[]> data = new ArrayList<>();
		for (Task t: loader.tasks) {
			if (t.getTaskName().startsWith(prefix)) {
				data.add(loader.taskToString(t).split("\t"));
			}
		}
		SimpleTableModel searchedPrefixTable = new SimpleTableModel("Searched items", loader.thisFileName, columnNames, data);
		return searchedPrefixTable;
	}

	
	@Override
	public SimpleTableModel getTaskById(int id) {
		List<String[]> data = new ArrayList<>();
		for (int i = 0; i < loader.tasks.size(); i++) {
			if (loader.tasks.get(i).getTaskId() == id) {
				data.add(loader.taskToString(loader.tasks.get(i)).split("\t"));
			}
		}
		SimpleTableModel idTable = new SimpleTableModel("Searched IDs", loader.thisFileName, columnNames, data);
		return idTable;
	}

	
	@Override
	public SimpleTableModel getTopLevelTasks() {
		List<String[]> data = new ArrayList<>();
		for (Task t: loader.getTopTasksArr()) {
				data.add(loader.taskToString(t).split("\t"));
		}
		SimpleTableModel topLevelTasksTable = new SimpleTableModel("Top Level Tasks", loader.thisFileName, columnNames, data);
		return topLevelTasksTable;
	}

	
	@Override
	public int createReport(String path, ReportType type) {
		Report report = new Report(path);
		if (type == ReportType.TEXT) {
			report.createTextReport(loader);
		}
		else if (type == ReportType.MD) {
			report.createMdReport(loader);
		}
		else if (type == ReportType.HTML) {
			report.createHtmlReport(loader);
		}
		return report.getProcessedTasks();
		// get the number of tasks by the ArrayList. Report type will be gotten from a report object, same as path
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(columnNames);
		result = prime * result + Objects.hash(loader.tasks);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GanttLoader other = (GanttLoader) obj;
		return Arrays.equals(columnNames, other.columnNames) && Objects.equals(loader.tasks, other.loader.tasks);
	}

}