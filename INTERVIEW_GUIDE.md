# Business automation framework — interview walkthrough

## What this project is
An evolution of your uploaded framework into a business-oriented Selenium reference implementation.
Default application: Sauce Demo (public demo commerce application). This is not a claim of production
deployment or a framework used at your employer. Public demo credentials are examples only.
Existing Amazon and Selenium practice classes remain for reference, excluded from the business suite.

## Layer ownership
| Layer | Files | Responsibility |
|---|---|---|
| Build | pom.xml | Pins dependencies, Java 17 compiler, Surefire and suite selection |
| Execution | suites/business.xml, suites/smoke.xml | Suite membership, groups, two method workers |
| Lifecycle | initialzer/InitTest | One driver per method, configuration, cleanup |
| Browser abstractions | initialzer/InitPage | Explicit waits and common browser interactions |
| Page objects | pageobjects/shop | Stable selectors and screen operations |
| Business flows | pages/ShoppingFlow | Reusable sign-in and add-to-cart journeys |
| Tests | tests/business/ShoppingJourneyTest | Business expectations, independent setup, assertions |
| Data | missingCustomerFields DataProvider | Three required-field scenarios without duplicated test methods |
| Reporting | utils/Listeners, ExtentManager | Test results, configuration failures, embedded failure screenshots |
| CI | Jenkinsfile | Maven execution, JUnit XML publishing, artifact retention |

The historical package spelling 'initialzer' is retained to avoid breaking existing imports.

## Execution flow
1. mvn clean test resolves dependencies and compiles main/test Java.
2. Surefire reads suites/business.xml and invokes TestNG.
3. BeforeMethod creates an independent browser on the current worker thread.
4. The listener creates an Extent test entry.
5. The test calls ShoppingFlow, which calls page objects and common waits.
6. TestNG assertions determine PASS/FAIL. SoftAssert is local and always followed by assertAll.
7. The listener records failures with a Base64 screenshot while the browser is still available.
8. AfterMethod quits the session and removes ThreadLocal even if quit throws.
9. Execution-finish flushes the report. Surefire returns a failing exit code for failures.

## Interview narrative (about 90 seconds)
"I designed a layered Java Selenium framework using TestNG and Maven. Surefire executes
configurable smoke or regression suites. Each test receives a fresh browser, managed with
ThreadLocal for method-level parallel execution. Page objects encapsulate stable locators
and interactions; a business-flow layer composes operations across screens. Tests own
assertions, and DataProviders supply negative scenarios. Explicit waits synchronize with
specific UI states, while implicit wait stays zero. A TestNG listener creates Extent entries,
captures embedded screenshots on failure, reports setup failures and flushes at execution end.
Jenkins runs the Maven command, publishes Surefire XML and retains report artifacts.
I avoid global mutable page objects and automatic retries that hide product defects."

## Read the project in this order
pom.xml → suites/business.xml → InitTest → InitPage → SignInPage → ShoppingFlow →
ShoppingJourneyTest → Listeners → ExtentManager → Jenkinsfile.

## Why these decisions?
- Composition: ShoppingFlow coordinates independent page objects; no giant business base class.
- Driver lifetime: method scope prevents cart/session state from leaking between tests.
- By locators: re-find dynamic elements; inherited PageFactory remains for legacy classes.
- BigDecimal: checkout money should not use binary floating-point equality.
- Negative testing: DataProvider covers each missing field separately.
- SoftAssert: collect subtotal/total issues before placing an order.
- Screenshots: embedded Base64 stays portable when the report is moved to CI artifacts.
- Retries: old RetryAnalyzer remains only in legacy tests; business assertions are not retried.
- Groups: smoke is a subset of regression, not a dependency chain.
- Configuration: JVM properties override properties; public demo login can be overridden by
  SHOP_USER and SHOP_PASSWORD environment variables. Never put real secrets in source or reports.

## Concepts mapped to behavior
| Concept | Implemented location |
|---|---|
| CSS/test attributes, WebElement clear/sendKeys/click | ShopPage, SignInPage |
| Explicit waits | InitPage and destination-page waits |
| Select dropdown and collection sorting | CatalogPage, catalogSortsByPrice |
| findElements and collection assertions | CartPage and cart test |
| POM and business-flow composition | pageobjects/shop and ShoppingFlow |
| DataProvider, groups, hard/soft assertions | ShoppingJourneyTest |
| ThreadLocal, Before/AfterMethod, finally cleanup | InitTest |
| Listeners, setup failures, screenshots, reporting | Listeners |
| Maven, compiler release, Surefire | pom.xml |
| CI results and artifact archiving | Jenkinsfile |
| Alerts, frames, windows, Actions, JS, XPath axes, upload, FluentWait | Optional legacy concept suite (testng.xml) |

Do not add fake alerts/frames to checkout merely to demonstrate APIs. Their separate examples
remain useful when the application actually requires those controls.

## Scope and limitations
This is a business-oriented reference framework, not a claim that every prior theory topic is automated.
Grid/remote execution, downloads, contract/API automation, database validation, self-hosted test data
provisioning, secrets-manager integration and advanced BiDi observability are future extensions.
The helper for Shadow DOM exists, but no business test uses a shadow root.
Jenkins needs configured tool names jdk17 and maven3, a browser, and network access.
The demo site's selectors/behavior need an actual local browser run before claiming tests pass.
Legacy table class selectors and TinyMCE editability may depend on current demo-site behavior.

## Commands
From the project directory, with JDK 17+ and Maven installed:
- mvn clean test
- mvn clean test -Dheadless=true
- mvn clean test -DsuiteXmlFile=suites/smoke.xml
- mvn clean test -Dbrowser=firefox
- mvn clean test -DsuiteXmlFile=testng.xml -DbaseUrl=https://the-internet.herokuapp.com

For IDE execution, choose the business suite and set VM option
-DbaseUrl=https://www.saucedemo.com/
(the retained _web.properties uses the old practice-site base for compatibility).
Import pom.xml into a new IDE project configuration rather than reusing a machine-specific SDK path.

## Reports
target/extent-report/index.html — Extent report with embedded failure screenshots.
target/surefire-reports — Maven/TestNG XML and execution reports.
A missing report usually means build/dependency failure happened before TestNG.
A skipped test after BeforeMethod failure is not a passing test: read the configuration failure entry.

## Validation status
XML and archive integrity are checked. Maven was not available in the generation environment.
No successful compilation or live UI execution is claimed.

