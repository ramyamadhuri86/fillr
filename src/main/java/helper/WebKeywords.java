package helper;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Arrays;



public class WebKeywords {
	private WebDriver driver;
	public ExtentReports report;
	private ExtentTest logger;
	private CustomAssertion ca = new CustomAssertion();
	private CommonUtil util = new CommonUtil();


	public WebKeywords() {

	}

	public WebKeywords(ExtentTest logger) {
		this.logger = logger;
	}

	public void setLogger(ExtentTest logger) {
		this.logger = logger;

	}

	/**
	 * Verifies if the driver is on the correct page by checking the page title and
	 * matching it with expected page title.
	 *
	 * @param driver WebDriver browser instance
	 * @param Title  title of the current web page
	 * @author Ramya
	 */
	public void VerifyPageExists(WebDriver driver, String title) {
		String currtitle = driver.getTitle();

		try {
			boolean isTrue = ca.cu_assertEquals(currtitle, title);
			if (isTrue) {
				if (logger != null) {
					logger.log(Status.PASS,
							"Verify page with title exisits : Page with title '" + title + "' is found.");
				}
			} else {
				if (logger != null) {
					logger.log(Status.FAIL,
							"Verify page with title exisits : Page with title '" + title + "' is not found.");
				}
				Assert.assertEquals(currtitle, title);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String em = Arrays.toString(e.getStackTrace());
			if (logger != null) {
				logger.log(Status.FAIL, "StackTrace Result: " + em.replaceAll(",", "<br>"));
			}
			e.printStackTrace();
		}

	}

	/**
	 * Verifies if the webelement with a matching text exists on the web page.
	 * 
	 *
	 * @param elm  WebElement on the page to verify
	 * @param Text text of the webelment to verify
	 * @author Ramya
	 */
	public void VerifyElementExists(WebElement elm, String Text) {
		String currtitle = elm.getText();

		try {
			boolean isTrue = ca.cu_assertEquals(currtitle, Text);
			if (isTrue) {
				if (logger != null) {
					logger.log(Status.PASS,
							"Verify element with text exisits : Element with text '" + Text + "' is found.");
				}
			} else {
				if (logger != null) {
					logger.log(Status.FAIL,
							"Verify element with text exisits : Page with text '" + Text + "' is not found.");
				}
				Assert.assertEquals(currtitle.trim(), Text);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String em = Arrays.toString(e.getStackTrace());
			if (logger != null) {
				logger.log(Status.FAIL, "StackTrace Result: " + em.replaceAll(",", "<br>"));
			}
			e.printStackTrace();
		}

	}





	/**
	 * Launch URL.
	 * 
	 *
	 * @param driver WebDriver
	 * @param url    url of the application
	 * @author Ramya
	 */
	public void launchURL(WebDriver driver, String url) {
		if (!url.isEmpty()) {
			driver.get(url);
			logger.log(Status.PASS, "Launch URL : Successfully  launched browser and navigated to  " + url + ".");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				String em = Arrays.toString(e.getStackTrace());
				if (logger != null) {
					logger.log(Status.FAIL, "StackTrace Result: " + em.replaceAll(",", "<br>"));
				}
				e.printStackTrace();
			}
		}
		/*
		 * WebDriverWait wait = new WebDriverWait(driver,10); try { wait.wait(5000); }
		 * catch (Exception e) { // TODO Auto-generated catch block e.printStackTrace();
		 * }
		 */

		// Assert.assertEquals(currtitle, Title);
	}

	/**
	 * Clicks on the link element.
	 *
	 *
	 * @param driver WebDriver
	 * @param Title  Link title
	 * @author Ramya
	 */
	public void ClickLink(WebElement link) {

		if (link.isEnabled() && link.isDisplayed()) {
			String Title = link.getText();
			link.click();
			logger.log(Status.PASS, "Click Link : Successfully clicked on link " + Title + ".");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				String em = Arrays.toString(e.getStackTrace());
				if (logger != null) {
					logger.log(Status.FAIL, "StackTrace Result: " + em.replaceAll(",", "<br>"));
				}
				e.printStackTrace();
			}
		}

		/*
		 * WebDriverWait wait = new WebDriverWait(driver,10); try { wait.wait(5000); }
		 * catch (Exception e) { // TODO Auto-generated catch block e.printStackTrace();
		 * }
		 */

		// Assert.assertEquals(currtitle, Title);
	}

	/**
	 * Clicks a button
	 * 
	 *
	 * @param button Button element to be clicked
	 * @return
	 */
	public void ClickButton(WebElement button) {

		if (button.isEnabled() && button.isDisplayed()) {
			String Title = button.getAttribute("value");
			button.click();
			logger.log(Status.PASS, "Click Button : Successfully clicked on button " + Title + ".");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				String em = Arrays.toString(e.getStackTrace());
				if (logger != null) {
					logger.log(Status.FAIL, "StackTrace Result: " + em.replaceAll(",", "<br>"));
				}
				e.printStackTrace();
			}
		}

		/*
		 * WebDriverWait wait = new WebDriverWait(driver,10); try { wait.wait(5000); }
		 * catch (Exception e) { // TODO Auto-generated catch block e.printStackTrace();
		 * }
		 */

		// Assert.assertEquals(currtitle, Title);
	}


	/**
	 * Sets a value into an edit field and verifies the value set.
	 * 
	 *
	 * @param elm WebElement
	 * @param val Value to be set
	 * @author Ramya
	 */
	public void Enter_Se(WebElement elm, String val) {
		if (!val.isEmpty() && !(elm == null)) {
			if (elm.isDisplayed() && elm.isEnabled()) {
				String dataval = util.resolveUniqueID(val);
				try {
					elm.clear();
					elm.sendKeys(dataval);
					elm.sendKeys(Keys.TAB);
					String currVal = elm.getAttribute("value");
					// System.out.println(tempVal);
					// Assert.assertEquals(currVal, val);
					boolean isTrue = ca.cu_assertEquals(currVal, dataval);
					if (isTrue) {
						if (logger != null) {
							logger.log(Status.PASS, "Value: '" + dataval + "' is successfully entered into the edit.");
						}
					} else {
						if (logger != null) {
							logger.log(Status.FAIL, "Failed to  enter Value '" + dataval + "' to the edit.");
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block

					String em = Arrays.toString(e.getStackTrace());
					if (logger != null) {
						logger.log(Status.FAIL, "StackTrace Result: " + em.replaceAll(",", "<br>"));
					}
					e.printStackTrace();
				}

			} else {
				if (logger != null) {
					logger.log(Status.INFO, "Object  is either disabled or hidden");
				}
			}
		} else {
			if (logger != null) {
				logger.log(Status.INFO, "Keyword Enter_Se is skipped");
			}
		}

	}

	/**
	 * Sets a value into an edit field and verifies the value set.
	 * 
	 *
	 * @param elm          WebElement
	 * @param val          Value to be set
	 * @param ElementLabel label of the edit object
	 * @author Ramya
	 */
	public void Enter_Se(WebElement elm, String val, String ElementLabel) {
		if (!val.isEmpty() && !(elm == null)) {
			if (elm.isDisplayed() && elm.isEnabled()) {
				String dataval = util.resolveUniqueID(val);
				try {
					elm.clear();
					elm.sendKeys(dataval);
					elm.sendKeys(Keys.TAB);
					elm.sendKeys(Keys.ENTER);
					String currVal = elm.getAttribute("value");
					// System.out.println(tempVal);
					// Assert.assertEquals(currVal, val);
					boolean isTrue = ca.cu_assertEquals(currVal, dataval);
					if (isTrue) {
						if (logger != null) {
							logger.log(Status.PASS,
									"Value: '" + dataval + "' is successfully entered into edit " + ElementLabel + ".");
						}
					} else {
						if (logger != null) {
							logger.log(Status.FAIL,
									"Failed to  enter Value '" + dataval + "' to  edit " + ElementLabel + ".");
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block

					String em = Arrays.toString(e.getStackTrace());
					if (logger != null) {
						logger.log(Status.FAIL, "StackTrace Result: " + em.replaceAll(",", "<br>"));
					}
					e.printStackTrace();
				}

			} else {
				if (logger != null) {
					logger.log(Status.INFO, "Object  is either disabled or hidden");
				}
			}
		} else {
			if (logger != null) {
				logger.log(Status.INFO, "Keyword Enter_Se is skipped");
			}
		}

	}



	/**
	 * Sets a value into an edit field.
	 * 
	 *
	 * @param elm WebElement
	 * @param val Value to be set
	 * @author Ramya
	 */
	public void EnterNoVerification_Se(WebElement elm, String val) {
		if (!val.isEmpty() && !(elm == null)) {
			if (elm.isDisplayed() && elm.isEnabled()) {
				String dataval = util.resolveUniqueID(val);
				try {
					elm.clear();
					elm.sendKeys(dataval);
					elm.sendKeys(Keys.ENTER);
					// String currVal = elm.getAttribute("value");
					// System.out.println(tempVal);
					// Assert.assertEquals(currVal, val);

					if (logger != null) {
						logger.log(Status.PASS, "Value: '" + dataval + "' is successfully entered into the edit.");
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block

					String em = Arrays.toString(e.getStackTrace());
					if (logger != null) {
						logger.log(Status.FAIL, "StackTrace Result: " + em.replaceAll(",", "<br>"));
					}
					e.printStackTrace();
				}

			} else {
				if (logger != null) {
					logger.log(Status.INFO, "Object  is either disabled or hidden");
				}
			}
		} else {
			if (logger != null) {
				logger.log(Status.INFO, "Keyword Enter_Se is skipped");
			}
		}

	}







	/**
	 * Clicks on a element.
	 * 
	 *
	 * @param elm WebElement
	 * @author Ramya
	 */
	public void Click_Se(WebElement elm) {
		if (!(elm == null)) {
			if (elm.isDisplayed() && elm.isEnabled()) {
				try {
					String txt = elm.getText();
					// elm.sendKeys(Keys.TAB);
					elm.click();

					// System.out.println(tempVal);
					// Assert.assertEquals(currVal, val);

					if (logger != null) {
						logger.log(Status.PASS, txt + " is successfully clicked.");
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block

					String em = Arrays.toString(e.getStackTrace());
					if (logger != null) {
						logger.log(Status.FAIL, "StackTrace Result: " + em.replaceAll(",", "<br>"));
					}
					e.printStackTrace();
				}

			} else {
				if (logger != null) {
					logger.log(Status.INFO, "Object  is either disabled or hidden");
				}
			}
		} else {
			if (logger != null) {
				logger.log(Status.INFO, "Pass a valid object");
			}
		}

	}

	/**
	 * Clicks on a element.
	 * 
	 *
	 * @param elm          WebElement
	 * @param object_label Label of the element
	 * @author Ramya
	 */
	public void Click_Se(WebElement elm, String object_label) {
		if (!(elm == null)) {
			if (elm.isDisplayed() && elm.isEnabled()) {
				try {
					String txt = object_label;
					// elm.sendKeys(Keys.TAB);
					elm.click();


					// System.out.println(tempVal);
					// Assert.assertEquals(currVal, val);

					if (logger != null) {
						logger.log(Status.PASS, txt + " is successfully clicked.");
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block

					String em = Arrays.toString(e.getStackTrace());
					if (logger != null) {
						logger.log(Status.FAIL, "StackTrace Result: " + em.replaceAll(",", "<br>"));
					}
					e.printStackTrace();
				}

			} else {
				if (logger != null) {
					logger.log(Status.INFO, "Object  is either disabled or hidden");
				}
			}
		} else {
			if (logger != null) {
				logger.log(Status.INFO, "Pass a valid object");
			}
		}

	}

	/**
	 * Clicks on a element.
	 *
	 *
	 * @param elm          WebElement
	 * @param object_label Label of the element
	 * @author Ramya
	 */
	public void ClickJS_Se(WebElement elm, String object_label) {
		if (!(elm == null)) {
			if (elm.isDisplayed() && elm.isEnabled()) {
				try {
					String txt = object_label;
					// elm.sendKeys(Keys.TAB);
				//	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elm);

					Thread.sleep(3000);
					elm.click();
					//JavascriptExecutor executor = (JavascriptExecutor)driver;

					//executor.executeScript("arguments[0].click();", elm);

					// System.out.println(tempVal);
					// Assert.assertEquals(currVal, val);

					if (logger != null) {
						logger.log(Status.PASS, txt + " is successfully clicked.");
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block

					String em = Arrays.toString(e.getStackTrace());
					if (logger != null) {
						logger.log(Status.FAIL, "StackTrace Result: " + em.replaceAll(",", "<br>"));
					}
					e.printStackTrace();
				}

			} else {
				if (logger != null) {
					logger.log(Status.INFO, "Object  is either disabled or hidden");
				}
			}
		} else {
			if (logger != null) {
				logger.log(Status.INFO, "Pass a valid object");
			}
		}

	}


	/**
	 * Verifies text in an element.
	 * 
	 *
	 * @param elm WebElement
	 * @param val Value to be verified
	 * @author Ramya
	 */
	public void VerifyText_Se(WebElement elm, String val) {
		if (!val.isEmpty() && !(elm == null)) {
			if (elm.isDisplayed()) {
				try {

					String currVal = elm.getText();

					boolean isTrue = ca.cu_assertEquals(currVal, val);
					if (isTrue) {
						if (logger != null) {
							logger.log(Status.PASS, "Actual value: '" + currVal
									+ "'  successfully matches  the Expected value:'" + val + "' .");
						}
					} else {
						logger.log(Status.FAIL,
								"Actual value:'" + currVal + "'  doesnot match  the Expected value: '" + val + "' .");
						// util.CaptureElementClip_Se(driver, elm, Status.FAIL, "Value " + val+ "
						// doesnot matcch with the value "+ currVal+" in the object."
						// , "C:/Users/Ramyaegesna/Documents/ARCBS/DonorToolCalculator/Reports/Images");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block

					String em = Arrays.toString(e.getStackTrace());
					if (logger != null) {
						logger.log(Status.FAIL, "StackTrace Result: " + em.replaceAll(",", "<br>"));
					}
					e.printStackTrace();
				}

			} else {
				if (logger != null) {
					logger.log(Status.INFO, "Keyword Enter_Se is skipped");
				}
			}
		}

	}

	/**
	 * Verifies text value of an element.
	 * 
	 *
	 * @param elm   WebElement
	 * @param val   Value to be verified
	 * @param label label of the edit object
	 * @author Ramya
	 */
	public void VerifyText_Se(WebElement elm, String val, String label) {
		if (!val.isEmpty() && !(elm == null)) {
			if (elm.isDisplayed()) {
				try {

					String currVal = elm.getText();

					boolean isTrue = ca.cu_assertEquals(currVal, val);
					if (isTrue) {
						if (logger != null) {
							logger.log(Status.PASS, "Actual value: '" + currVal
									+ "'  successfully matches  the Expected value:'" + val + "' in " + label + ".");
						}
					} else {
						logger.log(Status.FAIL, "Actual value:'" + currVal + "'  doesnot match  the Expected value: '"
								+ val + "' in " + label + ".");
						// util.CaptureElementClip_Se(driver, elm, Status.FAIL, "Value " + val+ "
						// doesnot matcch with the value "+ currVal+" in the object."
						// , "C:/Users/Ramyaegesna/Documents/ARCBS/DonorToolCalculator/Reports/Images");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block

					String em = Arrays.toString(e.getStackTrace());
					if (logger != null) {
						logger.log(Status.FAIL, "StackTrace Result: " + em.replaceAll(",", "<br>"));
					}
					e.printStackTrace();
				}

			} else {
				if (logger != null) {
					logger.log(Status.INFO, "Keyword Enter_Se is skipped");
				}
			}
		}

	}


	/**
	 * Verifies value attribute of an element.
	 * 
	 *
	 * @param elm WebElement
	 * @param val Value to be verified
	 * @author Ramya
	 */
	public void VerifyValue_Se(WebElement elm, String val) {
		if (!val.isEmpty() && !(elm == null)) {
			if (elm.isDisplayed()) {
				try {

					String currVal = elm.getAttribute("value");

					boolean isTrue = ca.cu_assertEquals(currVal, val);
					if (isTrue) {
						if (logger != null) {
							logger.log(Status.PASS, " Actual value: '" + currVal
									+ "'  successfully matches  the Expected value:'" + val + "' .");
						}
					} else {
						logger.log(Status.FAIL,
								"Actual value:'" + currVal + "'  doesnot match  the Expected value: '" + val + "' .");
						// util.CaptureElementClip_Se(driver, elm, Status.FAIL, "Value " + val+ "
						// doesnot matcch with the value "+ currVal+" in the object."
						// , "C:/Users/Ramyaegesna/Documents/ARCBS/DonorToolCalculator/Reports/Images");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block

					String em = Arrays.toString(e.getStackTrace());
					if (logger != null) {
						logger.log(Status.FAIL, "StackTrace Result: " + em.replaceAll(",", "<br>"));
					}
					e.printStackTrace();
				}

			} else {
				if (logger != null) {
					logger.log(Status.INFO, "Keyword Enter_Se is skipped");
				}
			}
		}

	}



}
