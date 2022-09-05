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
            rabbitmqBridgeHost: "bl_bridge_01_rabbitmq_server",
            rabbitmqCentralHost: "bl_central_server",
            rabbitmqTrainHost: "bl_train_01_rabbitmq_server",
            sparkMeterHost: "bl_readers",
            sparkPassengersHost: "bl_readers",
            centralAppsHost: "bl_central_server_apps"
        },
        includeShadowDom: true,
        video: false,
        screenshotOnRunFailure: false,
        supportFile: `${__dirname}/cypress/support/e2e.ts`,
        specPattern: `${__dirname}/cypress/e2e/**/*.cy.{js,jsx,ts,tsx}`,
    },
});
