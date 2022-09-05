describe('RabbitMQ', () => {
  const host = Cypress.env('rabbitmqCentralHost') ? Cypress.env('rabbitmqCentralHost') : 'localhost';
  const port = Cypress.env('rabbitmqCentralPort') ? Cypress.env('rabbitmqCentralPort') : '15672';

  it('Logs into Central Server Rabbit MQ (rabbit@bl-central-server)', () => {

    cy.log("Login")
    cy.visit(`http://${host}:${port}/`);
    cy.get('input[name="username"').type('test');
    cy.get('input[name="password"').type('test');
    cy.get('input[value="Login"]').click();

    cy.log("Main Page")
    cy.contains('rabbit@bl-central-server').should('exist');

    cy.log("Connections")
    cy.contains('Connections').click();
    cy.checkCommonCSRabbitMQTableValues();

    cy.log("Channels")
    cy.contains('Channels').click();
    cy.checkCommonCSRabbitMQTableValues();
  })
})