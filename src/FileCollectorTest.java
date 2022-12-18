import org.junit.Assert;
import org.junit.Test;

public class FileCollectorTest {
	
	private static String NO_PATH = null;

	private static String EMPTY_PATH = "";

	private static String ABSOLUTE_PATH = "/Users/percyrotteveel/workspace/FileCollector/logFiles";
	
	private static String RELATIVE_PATH = "./logFiles";
	
	private static String RELATIVE_PATH_WILD_CARD_1 = "./logFiles/*.txt";
	
	private static String RELATIVE_PATH_WILD_CARD_2 = "./logFiles/*.txt*";
	
	private static String RELATIVE_PATH_WILD_CARD_3 = "./logFiles/*Logs.*";
	
	private static String RELATIVE_HOME_PATH = "~/workspace/FileCollector/logFiles";

	private static String RELATIVE_HOME_PATH_WILD_CARD_1 = "~/workspace/FileCollector/logFiles/*.txt";

	@Test
	public void FileCollector1() {
		FileCollector myFileCollector = new FileCollector(NO_PATH);
		Assert.assertNotNull(myFileCollector);
	}

	@Test
	public void FileCollector2() {
		FileCollector myFileCollector = new FileCollector(EMPTY_PATH);
		Assert.assertNotNull(myFileCollector);
	}

	@Test
	public void FileCollector3() {
		FileCollector myFileCollector = new FileCollector(ABSOLUTE_PATH);
		Assert.assertNotNull(myFileCollector);
	}

	@Test
	public void getAllOK1() {
		FileCollector myFileCollector = new FileCollector(NO_PATH);
		Assert.assertFalse(myFileCollector.getAllOK());
	}

	@Test
	public void getAllOK2() {
		FileCollector myFileCollector = new FileCollector(EMPTY_PATH);
		Assert.assertFalse(myFileCollector.getAllOK());
	}

	@Test
	public void getAllOK3() {
		FileCollector myFileCollector = new FileCollector(ABSOLUTE_PATH);
		Assert.assertTrue(myFileCollector.getAllOK());
	}

	@Test
	public void run1() {
		FileCollector myFileCollector = new FileCollector(ABSOLUTE_PATH);
		Assert.assertTrue(myFileCollector.run());
	}

	@Test
	public void getMatchingFiles1() {
		String[] expected = {
			"/Users/percyrotteveel/workspace/FileCollector/logFiles/.DS_Store",
            "/Users/percyrotteveel/workspace/FileCollector/logFiles/WorkflowLogs.txt.1",
            "/Users/percyrotteveel/workspace/FileCollector/logFiles/WorkflowLogs.txt",
            "/Users/percyrotteveel/workspace/FileCollector/logFiles/logs/StagingLogs.txt",
            "/Users/percyrotteveel/workspace/FileCollector/logFiles/logs/TestingLogs.txt",
            "/Users/percyrotteveel/workspace/FileCollector/logFiles/logs/PreProdLogs.txt",
            "/Users/percyrotteveel/workspace/FileCollector/logFiles/logs/DevelopmentLogs.log",
            "/Users/percyrotteveel/workspace/FileCollector/logFiles/WorkflowLogs.txt.2"};
		FileCollector myFileCollector = new FileCollector(ABSOLUTE_PATH);
		String[] actual = null;
		if(myFileCollector.run()) {
			actual = myFileCollector.getMatchingFiles();
		}
		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void getMatchingFiles2() {
		String[] expected = {
			"./logFiles/.DS_Store",
            "./logFiles/WorkflowLogs.txt.1",
            "./logFiles/WorkflowLogs.txt",
            "./logFiles/logs/StagingLogs.txt",
            "./logFiles/logs/TestingLogs.txt",
            "./logFiles/logs/PreProdLogs.txt",
            "./logFiles/logs/DevelopmentLogs.log",
            "./logFiles/WorkflowLogs.txt.2"};
		FileCollector myFileCollector = new FileCollector(RELATIVE_PATH);
		String[] actual = null;
		if(myFileCollector.run()) {
			actual = myFileCollector.getMatchingFiles();
		}
		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void getMatchingFiles3() {
		String[] expected = {
			"./logFiles/WorkflowLogs.txt",
            "./logFiles/logs/StagingLogs.txt",
            "./logFiles/logs/TestingLogs.txt",
            "./logFiles/logs/PreProdLogs.txt"};
		FileCollector myFileCollector = new FileCollector(RELATIVE_PATH_WILD_CARD_1);
		String[] actual = null;
		if(myFileCollector.run()) {
			actual = myFileCollector.getMatchingFiles();
		}
		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void getMatchingFiles4() {
		String[] expected = {
			"./logFiles/WorkflowLogs.txt.1",
            "./logFiles/WorkflowLogs.txt",
            "./logFiles/logs/StagingLogs.txt",
            "./logFiles/logs/TestingLogs.txt",
            "./logFiles/logs/PreProdLogs.txt",
            "./logFiles/WorkflowLogs.txt.2"};
		FileCollector myFileCollector = new FileCollector(RELATIVE_PATH_WILD_CARD_2);
		String[] actual = null;
		if(myFileCollector.run()) {
			actual = myFileCollector.getMatchingFiles();
		}
		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void getMatchingFiles5() {
		String[] expected = {
			"./logFiles/WorkflowLogs.txt.1",
            "./logFiles/WorkflowLogs.txt",
            "./logFiles/logs/StagingLogs.txt",
            "./logFiles/logs/TestingLogs.txt",
            "./logFiles/logs/PreProdLogs.txt",
            "./logFiles/logs/DevelopmentLogs.log",
            "./logFiles/WorkflowLogs.txt.2"};
		FileCollector myFileCollector = new FileCollector(RELATIVE_PATH_WILD_CARD_3);
		String[] actual = null;
		if(myFileCollector.run()) {
			actual = myFileCollector.getMatchingFiles();
		}
		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void getMatchingFiles6() {
		String[] expected = {
			"/Users/percyrotteveel/workspace/FileCollector/logFiles/.DS_Store",
            "/Users/percyrotteveel/workspace/FileCollector/logFiles/WorkflowLogs.txt.1",
            "/Users/percyrotteveel/workspace/FileCollector/logFiles/WorkflowLogs.txt",
            "/Users/percyrotteveel/workspace/FileCollector/logFiles/logs/StagingLogs.txt",
            "/Users/percyrotteveel/workspace/FileCollector/logFiles/logs/TestingLogs.txt",
            "/Users/percyrotteveel/workspace/FileCollector/logFiles/logs/PreProdLogs.txt",
            "/Users/percyrotteveel/workspace/FileCollector/logFiles/logs/DevelopmentLogs.log",
            "/Users/percyrotteveel/workspace/FileCollector/logFiles/WorkflowLogs.txt.2"};
		FileCollector myFileCollector = new FileCollector(RELATIVE_HOME_PATH);
		String[] actual = null;
		if(myFileCollector.run()) {
			actual = myFileCollector.getMatchingFiles();
		}
		Assert.assertArrayEquals(expected, actual);
	}
	
	@Test
	public void getMatchingFiles7() {
		String[] expected = {
	            "/Users/percyrotteveel/workspace/FileCollector/logFiles/WorkflowLogs.txt",
	            "/Users/percyrotteveel/workspace/FileCollector/logFiles/logs/StagingLogs.txt",
	            "/Users/percyrotteveel/workspace/FileCollector/logFiles/logs/TestingLogs.txt",
	            "/Users/percyrotteveel/workspace/FileCollector/logFiles/logs/PreProdLogs.txt"};
		FileCollector myFileCollector = new FileCollector(RELATIVE_HOME_PATH_WILD_CARD_1);
		String[] actual = null;
		if(myFileCollector.run()) {
			actual = myFileCollector.getMatchingFiles();
		}
		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void main1() {
		String[] args = {ABSOLUTE_PATH};
		FileCollector.main(args);
	}

}
