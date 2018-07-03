package olympics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Olympics1 {
	WebDriver driver;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");
	}

	@Test(priority = 1)
	public void sortTest1() {
		// =========checking if the rank is ascending order by default=========
		List<Integer> actual = new ArrayList<Integer>();
		List<Integer> expected = new ArrayList<Integer>();
		List<WebElement> rank = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[1]"));
		for (int i = 0; i < rank.size() - 1; i++) {
			Integer n = Integer.parseInt(rank.get(i).getText());
			actual.add(n);
			expected.add(n);
		}
		Collections.sort(expected);
		Assert.assertEquals(expected, actual);
		System.out.println("\n============Rank order============");
		System.out.println("Actual:" + actual);
		System.out.println("Expected:" + expected);
		System.out.println("========================\n");

	}

	@Test(priority = 2)
	public void sortTest2() {
		// ===========checking if the Country names are not ascending order by
		// default======
		List<String> actual = new ArrayList<String>();
		List<String> expected = new ArrayList<String>();
		List<WebElement> countryName = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']//tbody/tr/th/a"));
		for (int i = 0; i < countryName.size(); i++) {
			actual.add(countryName.get(i).getText());
			expected.add(countryName.get(i).getText());
		}
		Collections.sort(expected);
		Assert.assertNotSame(expected, actual);
		System.out.println("=====Names order by Default=====");
		System.out.println("Actual:" + actual);
		System.out.println("Expected:" + expected);
		System.out.println("========================\n");
	}

	@Test(priority = 5)
	public void sortTest3() {
		// ===========checking if the Country names are ascending order after clicking
		// NOC======
		driver.findElement(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/thead/tr/th[2]"))
				.click();
		List<String> actual = new ArrayList<String>();
		List<String> expected = new ArrayList<String>();
		List<WebElement> countryName = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']//tbody/tr/th/a"));
		for (int i = 0; i < countryName.size(); i++) {
			actual.add(countryName.get(i).getText());
			expected.add(countryName.get(i).getText());
		}
		Collections.sort(expected);
		Assert.assertEquals(expected, actual);
		System.out.println("====Checking names order after clicking NOC=====");
		System.out.println("Actual:" + actual);
		System.out.println("Expected:" + expected);
		System.out.println("========================\n");
	}

	@Test(priority = 3)
	public void country18silver() {
		// ===========Getting the Countries with 18 Silver Medals======
		String actual = "";
		String expected = "ChinaFrance";
		List<WebElement> country = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']//tbody/tr/th/a"));
		List<WebElement> silverCount = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']//tbody//td[3]"));
		silverCount.remove(silverCount.size() - 1);
		List<String> names = new ArrayList<String>();
		List<Integer> silver = new ArrayList<Integer>();
		for (int i = 0; i < country.size(); i++) {
			names.add(country.get(i).getText());
			String s = silverCount.get(i).getText();
			Integer in = Integer.parseInt(s);
			silver.add(in);
		}
		for (int i = 0; i < silver.size(); i++) {
			if (silver.get(i) == 18) {
				actual = actual + names.get(i);
			}
		}
		Assert.assertEquals(expected, actual);
		System.out.println("===Countries with 18 Silvers====");
		System.out.println("Actual:" + actual);
		System.out.println("Expected:" + expected);
		System.out.println("========================\n");

	}

	@Test(priority = 4)
	public void testJapan() {
		// ===========Giving the location of Japan======
		List<WebElement> countries = driver.findElements(
				By.xpath("//table[@class = 'wikitable sortable" + " plainrowheaders jquery-tablesorter']//tbody//th"));
		int row = 0;
		for (int i = 0; i < countries.size(); i++) {
			if (countries.get(i).getText().contains("Japan")) {
				row = i + 1;
			}
		}
		System.out.println("=====Japan Location====");
		System.out.println("row= " + row);
		List<WebElement> columns = driver.findElements(
				By.xpath("//table[@class = 'wikitable sortable" + " plainrowheaders jquery-tablesorter']//thead//th"));

		int column = 0;
		for (int i = 0; i < columns.size(); i++) {
			if (columns.get(i).getText().contains("NOC")) {
				column = i + 1;
			}
		}

		System.out.println("column= " + column);
		System.out.println("========================\n");
	}

	@Test
	public void getSum() {
		List<String> ExpectedResult = new ArrayList<String>();
		ExpectedResult.add(" Italy (ITA)");
		ExpectedResult.add(" Australia (AUS)");

		List<WebElement> listBronzeMedals = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[4]"));
		List<WebElement> listCountries = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th"));

		List<String> actual = getSumOfBronze(listBronzeMedals, listCountries);

		Assert.assertTrue(ExpectedResult.equals(actual));
	}

	@Test
	public void theMost() {
		gold();
		silver();
		bronze();
		medal();
	}

	public List<String> getSumOfBronze(List<WebElement> ListOne, List<WebElement> ListTwo) {
		List<String> countriesList = new ArrayList<String>();

		for (int k = 0; k < ListTwo.size() - 1; k++) {
			for (int i = 1; i < ListTwo.size() - k; i++) {
				int a = Integer.parseInt(ListOne.get(k).getText());
				int b = Integer.parseInt(ListOne.get(i + k).getText());

				if ((a + b) == 18) {
					countriesList.add(ListTwo.get(k).getText());
					countriesList.add(ListTwo.get(i + k).getText());
				}
			}
		}
		return countriesList;

	}

	public String gold() {
		List<Integer> golds = new ArrayList<Integer>();

		for (int i = 1; i <= 10; i++) {
			golds.add(Integer.parseInt(driver.findElement(
					By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[" + i
							+ "]/td[2]"))
					.getText()));
		}

		String mostGold = "";
		int maxG = 0;
		for (int i = 0; i < 10; i++) {
			if (maxG < golds.get(i)) {
				maxG = golds.get(i);
				mostGold = driver.findElement(
						By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["
								+ (i + 1) + "]/th/a"))
						.getText();
			}
		}
		return mostGold;

	}

	public String silver() {

		List<Integer> silvers = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			silvers.add(Integer.parseInt(driver.findElement(
					By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["
							+ (i + 1) + "]/td[3]"))
					.getText()));
		}

		String mostSilver = "";
		int maxS = 0;
		for (int i = 0; i < 10; i++) {
			if (maxS < silvers.get(i)) {
				maxS = silvers.get(i);
				mostSilver = driver.findElement(
						By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["
								+ (i + 1) + "]/th/a"))
						.getText();

			}
		}
		return mostSilver;

	}

	public String bronze() {
		List<Integer> bronzes = new ArrayList<Integer>();
		for (int i = 1; i <= 10; i++) {
			bronzes.add(Integer.parseInt(driver.findElement(
					By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[" + i
							+ "]/td[4]"))
					.getText()));
		}
		System.out.println("bronzes" + bronzes);

		String mostBronze = "";
		int maxB = 0;
		for (int i = 0; i < 10; i++) {
			if (maxB < bronzes.get(i)) {
				maxB = bronzes.get(i);
				mostBronze = driver.findElement(
						By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["
								+ (i + 1) + "]/th/a"))
						.getText();

			}
		}
		return mostBronze;

	}

	public String medal() {
		List<Integer> medals = new ArrayList<Integer>();
		for (int i = 1; i <= 10; i++) {
			medals.add(Integer.parseInt(driver.findElement(
					By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[" + i
							+ "]/td[5]"))
					.getText()));
		}

		String mostMedals = "";
		int maxM = 0;
		for (int i = 0; i < 10; i++) {
			if (maxM < medals.get(i)) {
				maxM = medals.get(i);
				mostMedals = driver.findElement(
						By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["
								+ (i + 1) + "]/th/a"))
						.getText();

			}
		}
		return mostMedals;

	}

}
