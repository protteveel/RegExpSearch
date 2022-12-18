import org.junit.Assert;
import org.junit.Test;

public class RegExpSearchTest {

	@Test
	public void RegExpSearch1() {
		String[] args = {
			"-f",
			"~/workspace/RegExpSearch/logFiles/*.txt*",
			"-c",
			"~/workspace/RegExpSearch/RegExpSearch.conf",
			"-o",
			"~/workspace/RegExpSearch/RegExpSearch.txt"};
		RegExpSearch myRegExpSearch = new RegExpSearch(args);
		Assert.assertNotNull(myRegExpSearch);
	}
	
	@Test
	public void isAllOK1() {
		String[] args = {
			"-f",
			"~/workspace/RegExpSearch/logFiles/*.txt*",
			"-c",
			"~/workspace/RegExpSearch/RegExpSearch.conf",
			"-o",
			"~/workspace/RegExpSearch/RegExpSearch.txt"};
		Assert.assertTrue(new RegExpSearch(args).getAllOK());
	}

	@Test
	public void main1() {
		String[] args = {
			"-f",
			"~/workspace/RegExpSearch/logFiles/*.txt*",
			"-c",
			"~/workspace/RegExpSearch/RegExpSearch.conf",
			"-o",
			"~/workspace/RegExpSearch/RegExpSearch.txt"};
		RegExpSearch.main(args);
	}

}
