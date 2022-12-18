import org.junit.Assert;
import org.junit.Test;

public class RegExpCollectorTest {
	
	private static String NO_PATH = null;

	private static String EMPTY_PATH = "";

	public static final String ABSOLUTE_PATH = "/Users/percyrotteveel/workspace/RegExpCollector/RegExpCollector.conf";


	@Test
	public void RegExCollector1() {
		RegExpCollector myRegExCollector = new RegExpCollector(NO_PATH);
		Assert.assertNotNull(myRegExCollector);
	}

	@Test
	public void RegExCollector2() {
		RegExpCollector myRegExCollector = new RegExpCollector(EMPTY_PATH);
		Assert.assertNotNull(myRegExCollector);
	}

	@Test
	public void RegExCollector3() {
		RegExpCollector myRegExCollector = new RegExpCollector(ABSOLUTE_PATH);
		Assert.assertNotNull(myRegExCollector);
	}

	@Test
	public void getAllOK1() {
		RegExpCollector myRegExCollector = new RegExpCollector(NO_PATH);
		Assert.assertFalse(myRegExCollector.getAllOK());
	}

	@Test
	public void getAllOK2() {
		RegExpCollector myRegExCollector = new RegExpCollector(EMPTY_PATH);
		Assert.assertFalse(myRegExCollector.getAllOK());
	}

	@Test
	public void getAllOK3() {
		RegExpCollector myRegExCollector = new RegExpCollector(ABSOLUTE_PATH);
		Assert.assertTrue(myRegExCollector.getAllOK());
	}

	@Test
	public void run1() {
		RegExpCollector myRegExCollector = new RegExpCollector(ABSOLUTE_PATH);
		Assert.assertTrue(myRegExCollector.run());
	}

	@Test
	public void getRegExpTests1() {
		TestRegExp[] expected = { 
				new TestRegExp("Case ID",".*[C|c][A|a][S|s][E|e][I|i][D|d].*\\d{10}$.*"),
				new TestRegExp("DOB",".*[d|D][o|O][b|B].*([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])).*"),
				new TestRegExp("Driver License",".*[A-Z](?:\\d[- ]*){14}.*"),
				new TestRegExp("Email address",".*[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.]+@[a-zA-Z0-9.]+.*"),
				new TestRegExp("Facsimile number","(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}"),
				new TestRegExp("IP address","(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])"),
				new TestRegExp("Phone number","(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}"),
				new TestRegExp("SSN",".*\\d{3}-\\d{2}-\\d{4}.*"),
				new TestRegExp("URL",".*(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?.*"),
				new TestRegExp("VIN","\\b[(A-H|J-N|P|R-Z|0-9)]{17}\\b"),
				new TestRegExp("Zip code",".*\\d{5}(-\\d{4})?.*")};
		TestRegExp[] actual = null;
		RegExpCollector myRegExCollector = new RegExpCollector(ABSOLUTE_PATH);
		if(myRegExCollector.run()) {
			actual = myRegExCollector.getRegExpTests();
		}
		Boolean isEqual = false;
		if((expected != null) && (expected.length == 11)) {
			if((actual != null) && (actual.length == 11)) {
				isEqual = true;
				for(int i=0; i<expected.length; i++) {
					if(!(expected[i].getName().equalsIgnoreCase(actual[i].getName())) || 
				       !(expected[i].getRegExp().equalsIgnoreCase(actual[i].getRegExp()))) {
						isEqual = false;
						System.out.println("expected["+i+"].name ("+expected[i].getName()+") != actual[\"+i+\"].name (\"+actual[i].getName()+\") or");
						System.out.println("expected["+i+"].regExp ("+expected[i].getRegExp()+") != actual[\"+i+\"].regExp (\"+actual[i].getRegExp()+\") or");
					}
				}
			} else {
				System.out.println("actual is incomplete");
			}
		} else {
			System.out.println("expected is incomplete");
		}
		Assert.assertTrue(isEqual);
	}

	@Test
	public void main1() {
		String[] args = {ABSOLUTE_PATH};
		RegExpCollector.main(args);
	}

}
