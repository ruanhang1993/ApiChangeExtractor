package cn.edu.fudan.se.apiChangeExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.fudan.se.apiChangeExtractor.bean.Repository;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
	private ExecutorService service = new MyThreadPool(8, 8, 0, TimeUnit.MINUTES, queue);
	
	public static void main(String[] args) {
		Main main = new Main();
		main.extractRepositories(main.getTestData());
	}
	
	public List<Repository> getTestData(){
		String repositoryPath1 = "D:/javaee/parser/ApiChangeExtractor";
		String repositoryPath2 = "D:/github/ChangeExtractor";
		String repositoryPath3 = "D:/github/SEDataExtractor";
		String repositoryPath4 = "D:/javaee/LykProject";
		String repositoryPath5 = "D:/github/checkstyle";
		Repository repository1 = new Repository(-1, repositoryPath1);
		Repository repository2 = new Repository(-2, repositoryPath2);
		Repository repository3 = new Repository(-3, repositoryPath3);
		Repository repository4 = new Repository(-4, repositoryPath4);
		Repository repository5 = new Repository(-5, repositoryPath5);
		
		List<Repository> list = new ArrayList<>(); 
		list.add(repository1);
		list.add(repository2);
		list.add(repository3);
		list.add(repository4);
		list.add(repository5);
		return list;
	}
	
	public void extractRepositories(List<Repository> list){
		for(Repository r : list){
			RepositoryTask task = new RepositoryTask(r);
			service.submit(task);
		}
		service.shutdown();
		logger.info("Main End");
	}
	public void extractRepositoriesInLine(List<Repository> list){
		for(Repository r : list){
			ApiChangeExtractor a = new ApiChangeExtractor(r);
			a.extractApiChangeByDiff();
		}
	}
}
