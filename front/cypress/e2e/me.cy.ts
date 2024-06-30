describe('Login spec', () => {
  it('test sur le compte me', () => {
      cy.visit('/login')
  
      cy.intercept('POST', '/api/auth/login', {
        body: {
          id: 1,
          username: 'userName',
          firstName: 'firstName',
          lastName: 'lastName',
          admin: true
        },
      })
  
      cy.intercept(
        {
          method: 'GET',
          url: '/api/session',
        },
        []).as('session')
  
      cy.get('input[formControlName=email]').type("yoga@studio.com")
      cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
  
      cy.url().should('include', '/sessions')
  
  
      
      cy.intercept('GET', '/api/user/1', {
        id: 1,
        email: 'luca@test.fr',
        lastName: 'Lu',
        firstName: 'L',
        admin: false,
        password: 'toto',
        createdAt: new Date(),
      });
  
      cy.get('.link').contains('Account').click();
      cy.url().should('include', '/me');

      cy.intercept('DELETE', '/api/user/1', {
        id: 1,
        email: 'luca@test.fr',
        lastName: 'Lu',
        firstName: 'L',
        admin: false,
        password: 'toto',
        createdAt: new Date(),
      });

      cy.get('.ng-star-inserted > button').click();
      cy.get('.mat-simple-snack-bar-content').contains('Your account has been deleted !');
      cy.url().should('include', '');
    })
});