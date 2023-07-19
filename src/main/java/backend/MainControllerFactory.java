package backend;

public class MainControllerFactory {

	public IMainController createMainController() {
		GanttLoader loader = new GanttLoader();
		return loader;
	}
}