describe('Spark Tests for Central Meter Readings', () => {
    const host = Cypress.env('sparkMeterHost') ? Cypress.env('host') : 'localhost';
    const port = Cypress.env('sparkMeterPort') ? Cypress.env('port') : '4041';

    it('show passenger readings job page', () => {
        cy.visit(`http://${host}:${port}`);
        cy.get('strong[title="MetersBridgeLogisticsReader"]').should('exist');
    });

})