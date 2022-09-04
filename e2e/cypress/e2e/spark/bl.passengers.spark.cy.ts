describe('Spark Tests for Central Passenger Readings', () => {
    const host = Cypress.env('host') ? Cypress.env('host') : 'localhost';
    const port = Cypress.env('port') ? Cypress.env('port') : '4040';

    it('show passenger readings job page', () => {
        cy.visit(`http://${host}:${port}`);
        cy.get('strong[title="PassengerBridgeLogisticsReader"]').should('exist');
    });

})