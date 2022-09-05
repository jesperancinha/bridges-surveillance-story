describe('Spark Tests for Central Passenger Readings', () => {
    const host = Cypress.env('sparkPassengersHost') ? Cypress.env('sparkPassengersHost') : 'localhost';
    const port = Cypress.env('sparkPassengersPort') ? Cypress.env('sparkPassengersPort') : '4040';

    it('show passenger readings job page', () => {
        cy.visit(`http://${host}:${port}`);
        cy.get('strong[title="PassengerBridgeLogisticsReader"]').should('exist');
    });

})