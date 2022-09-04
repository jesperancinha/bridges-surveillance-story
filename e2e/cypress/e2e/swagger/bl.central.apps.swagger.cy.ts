describe('Swagger Tests for Central Apps', () => {
    const host = Cypress.env('host') ? Cypress.env('host') : 'localhost';
    const port = Cypress.env('port') ? Cypress.env('port') : '9000';

    it('shows swagger for central web-app', () => {
        cy.visit(`http://${host}:${port}/api/bridge/logistics/swagger-ui/index.html`);
        cy.get('h2', {timeout: 10000}).contains('OpenAPI definition', {timeout: 10000}).should('not.be.null');
        cy.wait(1000);

        cy.get('div[class="servers"] > label > select > option').should('have.value', 'http://localhost:9000/api/bridge/logistics')
    });

})