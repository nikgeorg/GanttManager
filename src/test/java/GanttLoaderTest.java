import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import backend.GanttLoader;
import backend.IMainController;
import backend.MainControllerFactory;
import backend.ReportType;
import dom2app.SimpleTableModel;

public class GanttLoaderTest {
	
	private static MainControllerFactory factory;
	private static IMainController mainController;
	private static String inputFilePath;
	private static String delimiter;
	private static SimpleTableModel table;
	
	@BeforeClass
	public static void setUp() {
	factory = new MainControllerFactory();
	inputFilePath = "src/main/resources/input/EggsScrambled.tsv";
	delimiter = "\t";
	mainController = factory.createMainController();
	}
	
	/**
	 * Test method for {@link backend.GanttLoader#load(String fileName, String delimiter)}.
	 */
	@Test
	public void testLoader() {
		((GanttLoader) mainController).clearArrays();
		table = mainController.load(inputFilePath, delimiter);
		MainControllerFactory factoryTest = new MainControllerFactory();
		IMainController controllerTest = factoryTest.createMainController();
		SimpleTableModel tableLoadTest = controllerTest.load("src/main/resources/input/Eggs.tsv", delimiter);
		tableLoadTest.setPrjName("Eggs.tsv");
		assertThat(table.getData()).usingRecursiveComparison().isEqualTo(tableLoadTest.getData());
	}
	
	/**
	 * Test method for {@link backend.GanttLoader#getTopLevelTasks()}.
	 */
	@Test
	public void testGetTopLevelTasks() {
		((GanttLoader) mainController).clearArrays();
		table = mainController.load(inputFilePath, delimiter);
		table = mainController.getTopLevelTasks();
		List<String[]> dataTest = new ArrayList<>();
		dataTest.add(("100" + "\t" + "Prepare Fry" + "\t" + "0" + "\t" + "1" + "\t" + "12" + "\t" + "60").split("\t"));
		dataTest.add(("200" + "\t" + "Prepare the bread" + "\t"  + "0" + "\t" + "10" + "\t" + "12" + "\t" + "20").split("\t"));
		dataTest.add(("300" + "\t" + "Serve eggs" + "\t" + "0" + "\t" + "13" + "\t" + "20" + "\t" + "30").split("\t"));
		SimpleTableModel tableExpected = new SimpleTableModel("Top Level Tasks", "EggsScrambled.tsv", table.getColumnNames(), dataTest);
		assertThat(table).usingRecursiveComparison().isEqualTo(tableExpected);
	}
	
	/**
	 * Test method for {@link backend.GanttLoader#getTaskById(int id)}.
	 */
	@Test
	public void testGetTaskById() {
		((GanttLoader) mainController).clearArrays();
		table = mainController.load(inputFilePath, delimiter);
		table = mainController.getTaskById(301);
		List<String[]> dataTest = new ArrayList<>();
		dataTest.add(("301" + "\t" + "Put bread in plate" + "\t"  + "300" + "\t" + "13" + "\t" + "13" + "\t" + "10").split("\t"));
		
		SimpleTableModel tableTest = new SimpleTableModel("Searched IDs", "EggsScrambled.tsv", table.getColumnNames(), dataTest);
		assertThat(table).usingRecursiveComparison().isEqualTo(tableTest);
	}
	
	/**
	 * Test method for {@link backend.GanttLoader#getTasksByPrefix(String prefix)}.
	 */
	@Test
	public void testGetTaskByPrefix() {
		((GanttLoader) mainController).clearArrays();
		table = mainController.load(inputFilePath, delimiter);
		table = mainController.getTasksByPrefix("Pr");
		List<String[]> dataTest = new ArrayList<>();
		dataTest.add(("100" + "\t" + "Prepare Fry" + "\t" + "0" + "\t" + "1" + "\t" + "12" + "\t" + "60").split("\t"));
		dataTest.add(("200" + "\t" + "Prepare the bread" + "\t"  + "0" + "\t" + "10" + "\t" + "12" + "\t" + "20").split("\t"));
		SimpleTableModel tableTest = new SimpleTableModel("Searched items", "EggsScrambled.tsv", table.getColumnNames(), dataTest);
		assertThat(table).usingRecursiveComparison().isEqualTo(tableTest);
	}
	
	/**
	 * Test method for {@link backend.GanttLoader#createReport(String path, ReportType.TEXT)}.
	 */
	@Test
	public void testCreateTextReport() {
		String outputTxtPath = "src/test/resources/output/EggsScrambled.tsv";
		((GanttLoader) mainController).clearArrays();
		table = mainController.load(inputFilePath, delimiter);
		int processedTasks = mainController.createReport(outputTxtPath, ReportType.TEXT);
		assertEquals(14, processedTasks);
	}
	
	/**
	 * Test method for {@link backend.GanttLoader#createReport(String path, ReportType.MD)}.
	 */
	@Test
	public void testCreateMdReport() {
		String outputMdPath = "src/test/resources/output/EggsScrambled.md";
		((GanttLoader) mainController).clearArrays();
		table = mainController.load(inputFilePath, delimiter);
		int processedTasks = mainController.createReport(outputMdPath, ReportType.MD);
		assertEquals(14, processedTasks);
	}
	
	/**
	 * Test method for {@link backend.GanttLoader#createReport(String path, ReportType.HTML)}.
	 */
	@Test
	public void testCreateHtmlReport() {
		String outputHtmlPath = "src/test/resources/output/EggsScrambled.html";
		((GanttLoader) mainController).clearArrays();
		table = mainController.load(inputFilePath, delimiter);
		int processedTasks = mainController.createReport(outputHtmlPath, ReportType.HTML);
		assertEquals(14, processedTasks);
	}
}