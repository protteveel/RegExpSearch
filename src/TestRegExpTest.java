import org.junit.Assert;
import org.junit.Test;

public class TestRegExpTest {

	@Test
	public void TestRegEx1() {
		TestRegExp myTestRegEx = new TestRegExp(null,null);
		Assert.assertNotNull(myTestRegEx);
	}
	
	@Test
	public void getName1() {
		String expected = null;
		TestRegExp myTestRegEx = new TestRegExp(expected,null);
		String actual = myTestRegEx.getName();
		Assert.assertNull(actual);
	}

	@Test
	public void getName2() {
		String expected = "";
		TestRegExp myTestRegEx = new TestRegExp(expected,null);
		String actual = myTestRegEx.getName();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void getName3() {
		String expected = "Case ID";
		TestRegExp myTestRegEx = new TestRegExp(expected,null);
		String actual = myTestRegEx.getName();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void getRegExp1() {
		String expected = null;
		TestRegExp myTestRegEx = new TestRegExp(null,expected);
		String actual = myTestRegEx.getRegExp();
		Assert.assertNull(actual);
	}

	@Test
	public void getRegExp2() {
		String expected = "";
		TestRegExp myTestRegEx = new TestRegExp(null,expected);
		String actual = myTestRegEx.getRegExp();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void getRegExp3() {
		String expected = ".*[C|c][A|a][S|s][E|e][I|i][D|d].*\\d{10}$.*";
		TestRegExp myTestRegEx = new TestRegExp(null,expected);
		String actual = myTestRegEx.getRegExp();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void setName1() {
		String expected = null;
		TestRegExp myTestRegEx = new TestRegExp(null,null);
		Assert.assertNull(myTestRegEx.setName(expected));
	}

	@Test
	public void setName2() {
		String expected = "";
		TestRegExp myTestRegEx = new TestRegExp(null,null);
		Assert.assertEquals(expected, myTestRegEx.setName(expected));
	}
	
	@Test
	public void setName3() {
		String expected = "Case ID";
		TestRegExp myTestRegEx = new TestRegExp(null,null);
		Assert.assertEquals(expected, myTestRegEx.setName(expected));
	}
	
	@Test
	public void setRegExp1() {
		String expected = null;
		TestRegExp myTestRegEx = new TestRegExp(null,null);
		Assert.assertNull(myTestRegEx.setRegExp(expected));
	}

	@Test
	public void setRegExp2() {
		String expected = "";
		TestRegExp myTestRegEx = new TestRegExp(null,null);
		Assert.assertEquals(expected, myTestRegEx.setRegExp(expected));
	}
	
	@Test
	public void setRegExp3() {
		String expected = ".*[C|c][A|a][S|s][E|e][I|i][D|d].*\\d{10}$.*";
		TestRegExp myTestRegEx = new TestRegExp(null,null);
		Assert.assertEquals(expected, myTestRegEx.setRegExp(expected));
	}
	
}
