describe('Register spec', () => {
    it('Register successfull', () => {
      cy.visit('/register')
  
      cy.intercept('POST', '/api/auth/register', {
        body: {
          firstName: 'firstName',
          lastName: 'lastName',
          email: 'email',
          password: 'password'
        },
      })
  
      cy.intercept(
        {
          method: 'GET',
          url: '/api/session',
        },
        []).as('session')
  
      cy.get('input[formControlName=firstName]').type("test")
      cy.get('input[formControlName=lastName]').type("test")
      cy.get('input[formControlName=email]').type("test@test.fr")
      cy.get('input[formControlName=password]').type(`${"test@test.fr"}{enter}{enter}`)
  
      cy.url().should('include', '/login')
    })
  });