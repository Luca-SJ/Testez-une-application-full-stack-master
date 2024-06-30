describe('Not-found spec', () => {
    it('Test erreur 404', () => {
        cy.visit('/herdzrhgrftjhehjfhaaaedzdsd')
        cy.url().should('include', '/404');
    });
});