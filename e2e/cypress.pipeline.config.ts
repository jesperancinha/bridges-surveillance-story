import {defineConfig} from "cypress";

module.exports = defineConfig({
    e2e: {
        setupNodeEvents(on, config) {
            // implement node event listeners here
        },
        env: {
            TIMEOUT_CONFIG: {
                timeout: 10000
            },
            rabbitmqBridgeHost: "bl-bridge-01-rabbitmq-server",
            rabbitmqBridgePort: 15672,
            rabbitmqCentralHost: "bl-central-server",
            rabbitmqCentralPort: 15672,
            rabbitmqTrainHost: "bl-train-01-rabbitmq-server",
            rabbitmqTrainPort: 15672,
            sparkPassengersHost: "bl-readers",
            sparkPassengersPort: 4040,
            sparkMeterHost: "bl-readers",
            sparkMeterPort: 4041,
            centralAppsHost: "bl-central-server-apps",
            centralAppsPort: 9000
        },
        includeShadowDom: true,
        video: false,
        screenshotOnRunFailure: false,
        supportFile: `${__dirname}/cypress/support/e2e.ts`,
        specPattern: `${__dirname}/cypress/e2e/**/*.cy.{js,jsx,ts,tsx}`,
    },
});
