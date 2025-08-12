package Hooks;

import BaseClass.LibraryClass;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

public class Hooks {

    // The @Before hook for launching the browser has been removed.

    // This @After hook will still run after EACH scenario.
    // It's perfect for taking screenshots on failure.
    @After
    public void checkScenarioStatus(Scenario scenario) {
        if (scenario.isFailed()) {
            // This will take a screenshot only if a scenario fails
            LibraryClass.captureScreenshot(scenario.getName());
        }
    }
}