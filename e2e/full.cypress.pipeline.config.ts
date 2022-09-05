import { defineConfig } from "cypress";

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
      rabbitmqBridgePort: 15674,
      rabbitmqCentralHost: "bl_central_server",
      rabbitmqCentralPort: 15672,
      rabbitmqTrainHost: "bl_train_01_rabbitmq_server",
      rabbitmqTrainPort: 15673,
      sparkPassengersHost: "bl_readers",
      sparkPassengersPort: 4040,
      sparkMeterHost: "bl_readers",
      sparkMeterPort: 4041,
      centralAppsHost: "bl_central_server_apps",
      centralAppsPort: 9000
    },
    includeShadowDom: true,
    supportFile: `${__dirname}/cypress/support/e2e.ts`,
    specPattern: `${__dirname}/cypress/e2e/**/*.cy.{js,jsx,ts,tsx}`,
  },
});
