import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { BehaviorSubject } from 'rxjs';

import { SessionService } from './session.service';
import { Component } from '@angular/core';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

describe('SessionService', () => {
  let service: SessionService;
  const myClassInstance = new SessionService();

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('logout', () => {
    myClassInstance.logOut();

    expect(myClassInstance.sessionInformation).toBeUndefined();

    expect(myClassInstance.isLogged).toBe(false);

  });

  it('vérifie lutilisisation de next() pendant le logout', () => {
    // Arrange: Créez une instance de la classe MyClass
    const myClassInstance = new SessionService();

    // Créez un BehaviorSubject simulé
    const isLoggedSubjectMock = new BehaviorSubject<boolean>(true);
    // Injectez directement le BehaviorSubject simulé dans l'instance MyClass
    (myClassInstance as any).isLoggedSubject = isLoggedSubjectMock;

    // Appelons une méthode qui utilise next()
    // Par exemple, appelons logOut() et vérifions son effet sur isLoggedSubject
    myClassInstance.logOut();

    // Assert: Vérifiez que la méthode next du BehaviorSubject simulé a été appelée avec le bon argument
    expect(isLoggedSubjectMock.value).toBe(false);
  });

  it('login', () => {
    // Arrange: Créez une instance de la classe MyClass
    const myClassInstance = new SessionService();

    // Créez des informations de session simulées
    const user: SessionInformation = {
      token: 'aaea',
      type: 'aear',
      id: 0,
      firstName: 'luca',
      lastName: 'luca',
      username: 'testUser',
      admin: true
      // d'autres propriétés de la session si nécessaire
    };

    // Act: Appelez la méthode logIn avec les informations de session simulées
    myClassInstance.logIn(user);

    // Assert: Vérifiez que la sessionInformation est correctement définie
    expect(myClassInstance.sessionInformation).toEqual(user);

    // Assert: Vérifiez que isLogged est true
    expect(myClassInstance.isLogged).toBe(true);

    // Note: Vous pouvez également vérifier que la méthode next a été appelée correctement si nécessaire.
  });

  it('vérifie si on est connecté', () => {
    // Arrange: Créez une instance de la classe MyClass
    const myClassInstance = new SessionService();

    // Créez un BehaviorSubject simulé avec une valeur initiale de false
    const isLoggedSubjectMock = new BehaviorSubject<boolean>(false);
    // Injectez directement le BehaviorSubject simulé dans l'instance MyClass
    (myClassInstance as any).isLoggedSubject = isLoggedSubjectMock;

    // Act: Appelez la méthode $isLogged
    const observable = myClassInstance.$isLogged();

    // Assert: Vérifiez que l'Observable retourné émet la valeur actuelle de isLogged
    observable.subscribe((value) => {
      expect(value).toBe(false); // La valeur initiale de isLogged est false
    });

    // Note: Vous pouvez ajuster les valeurs et les assertions en fonction des besoins de votre code.
  });
});
