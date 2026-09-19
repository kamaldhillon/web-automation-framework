package tests;

import base.BaseTest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SeleniumPracticePage;

public class SeleniumConceptsTest extends BaseTest {
    @Test(description = "CSS, name and XPath locators plus WebElement operations")
    public void locatorsAndLogin() {
        SeleniumPracticePage page = new SeleniumPracticePage(); page.open("/login");
        Assert.assertTrue(page.login("tomsmith", "SuperSecretPassword!").contains("You logged into a secure area!"));
    }

    @Test(description = "Explicit and FluentWait with a dynamically displayed element")
    public void waitsAndDynamicElement() {
        SeleniumPracticePage page = new SeleniumPracticePage(); page.open("/dynamic_loading/1");
        Assert.assertEquals(page.waitForDynamicText(), "Hello World!");
    }

    @Test(description = "Wait for a disabled dynamic input to become enabled and read its DOM value")
    public void dynamicControlAndDomProperty() {
        SeleniumPracticePage page = new SeleniumPracticePage(); page.open("/dynamic_controls");
        Assert.assertEquals(page.enableDynamicInputAndReadValue("Enabled using explicit wait"), "Enabled using explicit wait");
    }

    @Test(description = "Select class dropdown handling")
    public void dropdown() {
        SeleniumPracticePage page = new SeleniumPracticePage(); page.open("/dropdown");
        Assert.assertEquals(page.selectDropdown("Option 2"), "Option 2");
    }

    @Test(description = "Simple, confirmation and prompt alerts")
    public void alerts() {
        SeleniumPracticePage page = new SeleniumPracticePage(); page.open("/javascript_alerts");
        Assert.assertEquals(page.acceptSimpleAlert(), "I am a JS Alert");
        Assert.assertEquals(page.dismissConfirmationAlert(), "You clicked: Cancel");
        Assert.assertEquals(page.enterPrompt("Kamaljit"), "You entered: Kamaljit");
    }

    @Test(description = "Switch into an iframe and return to default content")
    public void iframe() {
        SeleniumPracticePage page = new SeleniumPracticePage(); page.open("/iframe");
        Assert.assertEquals(page.editIframe("Selenium iframe practice"), "Selenium iframe practice");
    }

    @Test(description = "Window handles and parent-child switching")
    public void windows() {
        SeleniumPracticePage page = new SeleniumPracticePage(); page.open("/windows");
        Assert.assertEquals(page.openChildWindowAndReturn(), "New Window");
    }

    @Test(description = "Actions mouse hover and context click")
    public void actions() {
        SeleniumPracticePage page = new SeleniumPracticePage(); page.open("/hovers");
        Assert.assertEquals(page.hoverFirstUser(), "name: user1");
        page.open("/context_menu");
        Assert.assertEquals(page.rightClickContextArea(), "You selected a context menu");
    }

    @Test(description = "JavaScriptExecutor scrolling and highlighting")
    public void javaScriptExecutor() {
        SeleniumPracticePage page = new SeleniumPracticePage(); page.open("");
        Assert.assertTrue(page.useJavaScriptExecutor() > 0);
    }

    @Test(description = "XPath following-sibling and ancestor axes with web tables")
    public void webTableAndXpathAxes() {
        SeleniumPracticePage page = new SeleniumPracticePage(); page.open("/tables");
        Assert.assertEquals(page.getDueAmount("Doe"), "$100.00");
        Assert.assertEquals(page.getEmailUsingAncestor("Doe"), "jdoe@hotmail.com");
    }

    @Test(description = "File upload using input type=file")
    public void fileUpload() throws IOException {
        SeleniumPracticePage page = new SeleniumPracticePage(); page.open("/upload");
        Path directory = Path.of("target", "test-files"); Files.createDirectories(directory);
        Path file = directory.resolve("sample-upload.txt"); Files.writeString(file, "Selenium upload practice");
        Assert.assertEquals(page.upload(file), "sample-upload.txt");
    }

    @Test(description = "Browser cookie creation and retrieval")
    public void cookies() {
        SeleniumPracticePage page = new SeleniumPracticePage();
        Assert.assertEquals(page.verifyCookie(), "Kamaljit");
    }
}
