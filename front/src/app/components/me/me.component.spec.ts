import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SessionService } from 'src/app/services/session.service';
import { of } from 'rxjs';
import { SessionInformation } from '../../interfaces/sessionInformation.interface';

import { MeComponent } from './me.component';

// Mock des services et classes dépendantes
class MockUserService {
  delete(userId: string) {
    // Simuler un Observable vide
    return of(null);
  }
}

class MockSessionService {
  logOut() {
    // Vide car on ne teste pas le comportement de logOut ici
  }
}

class MockMatSnackBar {
  open(message: string, action: string, config: any) {
    // Vide car on ne teste pas le comportement de MatSnackBar ici
  }
}

class MockRouter {
  navigate(path: string[]) {
    // Vide car on ne teste pas le comportement de Router ici
  }
}

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
    }
  }
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ],
      providers: [{ provide: SessionService, useValue: mockSessionService }],
    })
      .compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Déclaration d'un test avec Jest pour vérifier le retour vers la page d'accueil
  it("Revenir vers la page d'accueil", () => {
    // Espionnage de la méthode window.history.back() avec Jest
    jest.spyOn(window.history, 'back').mockImplementation(() => { });

    // Appel de la méthode back() du composant
    component.back();

    // Vérification que la méthode window.history.back() a bien été appelée
    expect(window.history.back).toHaveBeenCalled();
  });

  it('delete', () => {
    jest.spyOn(window.history, 'back').mockImplementation(() => { });

    component.delete();

    expect(window.location.href).toBe("http://localhost/");
  });

});
