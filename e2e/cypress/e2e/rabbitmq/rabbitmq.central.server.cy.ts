describe('RabbitMQ', () => {
  it('Logs into Central Server Rabbit MQ (rabbit@bl_central_server)', () => {

    cy.log("Login")
    cy.visit('http://localhost:15672/');
    cy.get('input[name="username"').type('test');
    cy.get('input[name="password"').type('test');
    cy.get('input[value="Login"]').click();

    cy.log("Main Page")
    cy.contains('rabbit@bl_central_server').should('exist');

    cy.log("Connections")
    cy.contains('Connections').click();
    cy.checkCommonCSRabbitMQTableValues();

    cy.log("Channels")
    cy.contains('Channels').click();
    cy.checkCommonCSRabbitMQTableValues();
  })
})