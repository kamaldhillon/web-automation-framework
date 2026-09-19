# Web Automation Framework

## Business framework entry point

The default Maven suite now runs commerce journeys against Sauce Demo:
purchase, cart removal, price sorting, required checkout fields, logout and locked-user login.
See INTERVIEW_GUIDE.md for architecture, execution flow, design tradeoffs, concept mapping
and a 90-second interview explanation. The practice coverage below is retained as an optional suite.

Run smoke: `mvn clean test -DsuiteXmlFile=suites/smoke.xml`.
Run legacy concepts: `mvn clean test -DsuiteXmlFile=testng.xml -DbaseUrl=https://the-internet.herokuapp.com`.
Failure screenshots in the business report are embedded Base64 for portability.

Java 17 Selenium framework using TestNG, Maven Surefire, Page Objects, Extent Reports, reusable waits, failure screenshots and parallel-safe `ThreadLocal<WebDriver>` sessions.

## Covered concepts

- CSS, name and XPath locators
- XPath axes and dynamic web tables
- WebElement methods
- Explicit and FluentWait
- Dropdowns using `Select`
- Simple, confirmation and prompt alerts
- Frames and default content
- Multiple windows and tabs
- Actions mouse hover and right-click
- JavaScriptExecutor scroll and highlighting
- Dynamic elements
- Enabled-state synchronization and `getDomProperty()`
- File upload
- Cookies
- Screenshots on failure
- TestNG listeners and retry analyser
- Maven Surefire and parallel execution
- Chrome, Firefox and Edge

## Commands

    mvn clean test
    mvn clean test -Dheadless=true
    mvn clean test -Dbrowser=firefox

Reports are generated at `target/extent-report/index.html`; failure screenshots are saved under `target/screenshots`.
