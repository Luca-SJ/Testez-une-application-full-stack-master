describe('session-api.service', () => {
    it('Create', () => {
      cy.visit('/login')

      cy.intercept('POST', '/api/auth/login', {
        body: {
          token: "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5b2dhQHN0dWRpby5jb20iLCJpYXQiOjE3MTM3NzgwNTUsImV4cCI6MTcxMzg2NDQ1NX0.Fsp-7H7xvEC9SU8op7PB3QpNv6qlC3hW4tKwprNNgqezaznIG2SZK70Z3k6XbJtQdtrxL24zbCSm17LJ7lrZ5A",
          type: "Bearer",
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

      cy.intercept(
        {
          method: 'GET',
          url: '/api/teacher',
        },
        [
          {
          "id": 1,
          "lastName": "DELAHAYE",
          "firstName": "Margot",
          "createdAt": null,
          "updatedAt": null
          },
          {
              "id": 2,
              "lastName": "THIERCELIN",
              "firstName": "Hélène",
              "createdAt": null,
              "updatedAt": null
          }
        ]).as('teacher')

      cy.get('input[formControlName=email]').type("yoga@studio.com")
      cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
        
      cy.get('.mat-card-header > button').click();

      cy.intercept('POST', '/api/session', {
        body: {
          name: 'test',
          date: "2024-04-10T00:00:00.000+00:00",
          teacher_id: 2,
          description: "aaaabbbbccc",
        },
      })

      cy.get('input[formControlName=name]').type("test")
      cy.get('input[formControlName=date]').type("2024-08-15")
      cy.get('mat-select[formControlName=teacher_id]').click()
      cy.get('#mat-option-0').click()
      cy.get('textarea[formControlName=description]').type(`${"test test test"}`)
      cy.get('button[type=submit]').click()

      cy.get('.mat-simple-snack-bar-content').contains('Session created !');
      
      cy.location().should((location) => {
        expect(location.pathname).to.eq('/sessions')
      })
    })

    it('Delete', () => {
      cy.visit('/login')

      cy.intercept('POST', '/api/auth/login', {
        body: {
          token: "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5b2dhQHN0dWRpby5jb20iLCJpYXQiOjE3MTM3NzgwNTUsImV4cCI6MTcxMzg2NDQ1NX0.Fsp-7H7xvEC9SU8op7PB3QpNv6qlC3hW4tKwprNNgqezaznIG2SZK70Z3k6XbJtQdtrxL24zbCSm17LJ7lrZ5A",
          type: "Bearer",
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
        [
          {
            id: 1,
            name: "aaaa",
            date: "2024-04-10T00:00:00.000+00:00",
            teacher_id: 2,
            description: "aaaabbbbccc",
            users: [],
            createdAt: "2024-04-02T12:19:25",
            updatedAt: "2024-04-02T12:19:25"
          },
        ]).as('session')

        cy.intercept(
          {
            method: 'GET',
            url: '/api/session/1',
          },
          {
            body:
            {
              id: 1,
              name: "aaaa",
              date: "2024-04-10T00:00:00.000+00:00",
              teacher_id: 2,
              description: "aaaabbbbccc",
              users: [],
              createdAt: "2024-04-02T12:19:25",
              updatedAt: "2024-04-02T12:19:25"
            }
          }).as('1')
          
      
        cy.get('input[formControlName=email]').type("yoga@studio.com")
        cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
        
        cy.get('mat-card-actions > button').first().click()

        cy.intercept('DELETE', '/api/session/1', {
          id: 1,
          name: "aaaa",
          date: "2024-04-10T00:00:00.000+00:00",
          teacher_id: 2,
          description: "aaaabbbbccc",
          users: [],
          createdAt: "2024-04-02T12:19:25",
          updatedAt: "2024-04-02T12:19:25"
        });

        cy.get('button').last().click();
        
        cy.location().should((location) => {
          expect(location.pathname).to.eq('/sessions')
        })
    })
});