describe('RabbitMQ', () => {
  const host = Cypress.env('rabbitmqTrainHost') ? Cypress.env('rabbitmqTrainHost') : 'localhost';
  const port = Cypress.env('rabbitmqTrainPort') ? Cypress.env('rabbitmqTrainPort') : '15673';

  it('Logs into Train Server Rabbit MQ (rabbit@bl-train-01-rabbitmq-server)', () => {

    cy.log("Login")
    cy.visit(`http://${host}:${port}/`);
    cy.get('input[name="username"').type('test');
    cy.get('input[name="password"').type('test');
    cy.get('input[value="Login"]').click();

    cy.log("Main Page")
    cy.contains('rabbit@bl-train-01-rabbitmq-server').should('exist');

    cy.log("Connections")
    cy.contains('Connections').click();
    cy.checkCommonTrainRabbitMQTableValues();

    cy.log("Channels")
    cy.contains('Channels').click();
    cy.checkCommonTrainRabbitMQTableValues();
  })
})