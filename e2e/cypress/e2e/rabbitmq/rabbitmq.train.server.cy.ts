describe('RabbitMQ', () => {
  it('Logs into Train Server Rabbit MQ (rabbit@bl_train_01_rabbitmq_server)', () => {

    cy.log("Login")
    cy.visit('http://localhost:15673/');
    cy.get('input[name="username"').type('test');
    cy.get('input[name="password"').type('test');
    cy.get('input[value="Login"]').click();

    cy.log("Main Page")
    cy.contains('rabbit@bl_train_01_rabbitmq_server').should('exist');

    cy.log("Connections")
    cy.contains('Connections').click();
    cy.checkCommonTrainRabbitMQTableValues();

    cy.log("Channels")
    cy.contains('Channels').click();
    cy.checkCommonTrainRabbitMQTableValues();
  })
})