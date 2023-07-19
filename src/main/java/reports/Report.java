package reports;

import dom.ChildTask;
import dom.Task;
import loader.Loader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Report {
	private String path;
	private int processedTasks;
	private String header = "TaskId" + "\t" + "TaskText" + "\t" + "MamaId" + "\t" + "Start" + "\t" + "End" + "\t" + "Cost";
	
	public Report(String path) {
		super();
		this.path = path;
	}
	
	public File createTextReport(Loader loader) {
		File file = new File(path);
		List<String> data = new ArrayList<>();
		setProcessedTasks(0);
		
		try {
			FileWriter txtReport = new FileWriter(file);
			
			txtReport.write(header + '\n');
			
			 {
				
				for (Task t: loader.getTopTasksArr()) {
					data.add(loader.taskToString(t));
					setProcessedTasks(getProcessedTasks() + 1);
					for (ChildTask ct: t.children) {
						data.add(loader.taskToString(ct));
						setProcessedTasks(getProcessedTasks() + 1);
					}
				}
				
				for (String s: data) {
					if (s == data.get(data.size() - 1)) {
						txtReport.write(s);
					}
					else txtReport.write(s + "\n");
				}
			}
			txtReport.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			setProcessedTasks(-1);
		}
		return file;
	}
	
	public File createMdReport(Loader loader) {
		File file = new File(path);
		setProcessedTasks(0);
		
		try {
			FileWriter mdReport = new FileWriter(file);
			
			mdReport.write("*TaskId*" + "\t" + "*TaskText*" + "\t" + "*MamaId*" + "\t" + "*Start*" + "\t" + "*End*" + "\t" + "*Cost*" + "\n");
			
			 	{
				
				for (Task t: loader.getTopTasksArr()) {
					if (getProcessedTasks() == loader.tasks.size() - 1) {
						mdReport.write("**" + loader.taskToString(t) + "**");
						setProcessedTasks(getProcessedTasks() + 1);
					}
					mdReport.write("**" + loader.taskToString(t) + "**" + "\n");
					setProcessedTasks(getProcessedTasks() + 1);
					
					for (ChildTask ct: t.children) {
						mdReport.write(loader.taskToString(ct) + "\n");
						setProcessedTasks(getProcessedTasks() + 1);
					}
				}
			mdReport.close();
			 	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			setProcessedTasks(-1);
		}
		return file;
	}
	
	public File createHtmlReport(Loader loader) {
		File file = new File(path);
		setProcessedTasks(0);
		
		try {
			FileWriter htmlReport = new FileWriter(file);
			
			htmlReport.write("<!doctype html>" + "\n" + "<html>" + "\n" + "<head>" + "\n" +
			"<meta http-equiv=\"Content-Type\" content\"text/html; charset=windows-1253\">" + "\n" +
			"<title>Gantt Project Data</title>" + "\n" +
			"</head>" + "\n" +
			"<body>" + "\n" +
			"<table>" + "\n" +
			"<tr>" + "\n" +
			"<td>TaskId</td>" + "\n" +
			"<td>TaskName</td>" + "\n" +
			"<td>MamaId</td>" + "\n" +
			"<td>Start</td>" + "\n" +
			"<td>End</td>" + "\n" +
			"<td>Cost</td>" + "\n");
			
				
				for (Task t: loader.getTopTasksArr()) {
					htmlReport.write("<tr>" + "\n" + "<td>" + t.getTaskId() + "</td>" + "\n" +
							"<td>" + t.getTaskName() + "</td>" + "\n" +
							"<td>" + t.getMamaId() + "</td>" + "\n" +
							"<td>" + t.getStartTime() + "</td>" + "\n" +
							"<td>" + t.getEndTime() + "</td>" + "\n" +
							"<td>" + t.getCost() + "</td>" + "\n" + "</tr>" + "\n");
					setProcessedTasks(getProcessedTasks() + 1);
					for (ChildTask ct: t.children) {
						htmlReport.write("<tr>" + "\n" + "<td>" + ct.getTaskId() + "</td>" + "\n" +
								"<td>" + ct.getTaskName() + "</td>" + "\n" +
								"<td>" + ct.getMamaId() + "</td>" + "\n" +
								"<td>" + ct.getStartTime() + "</td>" + "\n" +
								"<td>" + ct.getEndTime() + "</td>" + "\n" +
								"<td>" + ct.getCost() + "</td>" + "\n" + "</tr>" + "\n");
						setProcessedTasks(getProcessedTasks() + 1);
					}
				}
				htmlReport.write("</table></body>" + "\n" + "</html>");
			htmlReport.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			setProcessedTasks(-1);
		}
		return file;
	}

	public int getProcessedTasks() {
		return processedTasks;
	}

	public void setProcessedTasks(int processedTasks) {
		this.processedTasks = processedTasks;
	}
	
	
}
