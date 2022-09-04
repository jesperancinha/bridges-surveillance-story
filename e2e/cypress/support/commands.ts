/// <reference types="cypress" />
// ***********************************************
// This example commands.ts shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })
//
// declare global {
//   namespace Cypress {
//     interface Chainable {
//       login(email: string, password: string): Chainable<void>
//       drag(subject: string, options?: Partial<TypeOptions>): Chainable<Element>
//       dismiss(subject: string, options?: Partial<TypeOptions>): Chainable<Element>
//       visit(originalFn: CommandOriginalFn, url: string, options: Partial<VisitOptions>): Chainable<Element>
//     }
//   }
// }

Cypress.Commands.add('checkCommonCSRabbitMQTableValues', () => {
    cy.get('table > tbody > tr[class="alt1"] > td').contains("/").should('exist');
    cy.get('table > tbody > tr[class="alt2"] > td').contains("/").should('exist');
    cy.get('table > tbody > tr[class="alt1"] > td').contains("bl_bridge_01_sensor_vh").should('exist');
    cy.get('table > tbody > tr[class="alt2"] > td').contains("bl_bridge_01_sensor_vh").should('exist');
    cy.get('table > tbody > tr[class="alt1"]').eq(2).find("td").contains("bl_bridge_01_sensor_vh").should('exist');
    cy.get('table > tbody > tr[class="alt2"] > td').contains("bl_train_01_merchandise_vh").should('exist');
    cy.get('table > tbody > tr[class="alt1"] > td').contains("bl_train_01_merchandise_vh").should('exist');
    cy.get('table > tbody > tr[class="alt2"]').eq(3).find('td').contains("bl_train_01_merchandise_vh").should('exist');
    cy.get('table > tbody > tr[class="alt1"] > td').contains("bl_train_01_sensor_vh").should('exist');
    cy.get('table > tbody > tr[class="alt2"] > td').contains("bl_train_01_sensor_vh").should('exist');
    cy.get('table > tbody > tr[class="alt1"]').eq(5).find('td').contains("bl_train_01_sensor_vh").should('exist');
})

Cypress.Commands.add('checkCommonTrainRabbitMQTableValues', () => {
    cy.get('table > tbody > tr[class="alt1"] > td').contains("bl_train_01_merchandise_vh").should('exist');
    cy.get('table > tbody > tr[class="alt2"] > td').contains("bl_train_01_merchandise_vh").should('exist');
    cy.get('table > tbody > tr[class="alt1"] > td').contains("bl_train_01_sensor_vh").should('exist');
    cy.get('table > tbody > tr[class="alt2"] > td').contains("bl_train_01_sensor_vh").should('exist');
})

Cypress.Commands.add('checkCommonBridgeRabbitMQTableValues', () => {
    cy.get('table > tbody > tr[class="alt1"] > td').contains("bl_bridge_01_sensor_vh").should('exist');
    cy.get('table > tbody > tr[class="alt2"] > td').contains("bl_bridge_01_sensor_vh").should('exist');
})
