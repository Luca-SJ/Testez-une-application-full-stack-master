import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { SessionInformation } from '../../../../interfaces/sessionInformation.interface';
import { Observable } from 'rxjs';


import { LoginComponent } from './login.component';

// Définissez une interface pour le type de retour de la méthode login
interface LoginResponse {
  // Définissez la structure des données de session simulées
  token: string;
  type: string;
  id: number;
  firstName: string;
  lastName: string;
  username: string;
  admin: boolean;
  // d'autres propriétés de la session si nécessaire

}

describe('LoginComponent', () => {
  let component: LoginComponent;
  let authService: AuthService;
  let sessionService: SessionService;
  let router: Router;
  let formBuilder: FormBuilder;


  beforeEach(() => {
    // authService = {
    //   login: jest.fn(), // Espionnez la méthode login
    // } as jest.Mocked<AuthService>; // Utilisation de jest.Mocked pour les méthodes espionnées

    sessionService = {
      logIn: jest.fn(),
    } as any;

    router = {
      navigate: jest.fn(),
    } as any;

    formBuilder = new FormBuilder();

    component = new LoginComponent(authService, formBuilder, router, sessionService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // it('should submit login request successfully', () => {
  //   // Arrange
  //   component.form.patchValue({ email: 'test@test.com', password: '123456' });

  //   // Espionner la méthode login de authService
  //   const loginResponse: LoginResponse = {
  //     token: 'aaea',
  //     type: 'aear',
  //     id: 0,
  //     firstName: 'luca',
  //     lastName: 'luca',
  //     username: 'testUser',
  //     admin: true
  //   };
  //   const authServiceSpy = jest.spyOn(authService, 'login').mockReturnValue(of(loginResponse));

  //   // Act
  //   component.submit();

  //   // Assert
  //   expect(authService.login).toHaveBeenCalledWith({ email: 'test@test.com', password: '123456' });
  //   expect(sessionService.logIn).toHaveBeenCalledWith(loginResponse);
  //   expect(router.navigate).toHaveBeenCalledWith(['/sessions']);
  //   expect(component.onError).toBe(false); // Assurez-vous que onError est toujours false en cas de succès
  // });

  // it('should set onError flag on error', () => {
  //   // Arrange
  //   component.form.patchValue({ email: 'test@test.com', password: '123456' });

  //   // Espionner la méthode login de authService
  //   const authServiceSpy = jest.spyOn(authService, 'login').mockReturnValue(throwError('Fake error'));

  //   // Act
  //   component.submit();

  //   // Assert
  //   expect(component.onError).toBe(true);
  // });
});
