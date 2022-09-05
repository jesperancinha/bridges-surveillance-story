describe('RabbitMQ', () => {
  const host = Cypress.env('rabbitmqBridgeHost') ? Cypress.env('rabbitmqBridgeHost') : 'localhost';
  const port = Cypress.env('rabbitmqBridgePort') ? Cypress.env('rabbitmqBridgePort') : '15674';

  it('Logs into Bridge Server Rabbit MQ (rabbit@bl-bridge-01-rabbitmq-server)', () => {

    cy.log("Login")
    cy.visit(`http://${host}:${port}/`);
    cy.get('input[name="username"').type('test');
    cy.get('input[name="password"').type('test');
    cy.get('input[value="Login"]').click();

    cy.log("Main Page")
    cy.contains('rabbit@bl-bridge-01-rabbitmq-server').should('exist');

    cy.log("Connections")
    cy.contains('Connections').click();
    cy.checkCommonBridgeRabbitMQTableValues();

    cy.log("Channels")
    cy.contains('Channels').click();
    cy.checkCommonBridgeRabbitMQTableValues();
  })
})