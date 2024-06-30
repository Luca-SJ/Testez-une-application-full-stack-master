import { SessionApiService } from './session-api.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Session } from '../interfaces/session.interface';
import { UserService } from 'src/app/services/user.service';

describe('SessionApiService', () => {
  let service: SessionApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SessionApiService]
    });
    service = TestBed.inject(SessionApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // Vérifiez qu'il n'y a pas de requêtes non attendues
  });

  it('should create a session', () => {
    const session: Session = { id: 1, name: 'Test Session', description: '', date: new Date(), teacher_id: 1, users: [1] };

    service.create(session).subscribe(createdSession => {
      expect(createdSession).toEqual(session); // Vérifiez que la session créée est la même que celle fournie
    });

    const req = httpMock.expectOne('api/session'); // Vérifiez la requête HTTP
    expect(req.request.method).toBe('POST'); // Vérifiez la méthode HTTP utilisée
    expect(req.request.body).toEqual(session); // Vérifiez que le corps de la requête est correct
    req.flush(session); // Simulez la réponse HTTP
  });

  it('should update a session', () => {
    const sessionId = '1';
    const updatedSession: Session = { id: 1, name: 'Test Session', description: '', date: new Date(), teacher_id: 1, users: [1] };

    service.update(sessionId, updatedSession).subscribe(response => {
      expect(response).toEqual(updatedSession); // Vérifiez que la session mise à jour est la même que celle fournie
    });

    const req = httpMock.expectOne(`api/session/${sessionId}`); // Vérifiez la requête HTTP
    expect(req.request.method).toBe('PUT'); // Vérifiez la méthode HTTP utilisée
    expect(req.request.body).toEqual(updatedSession); // Vérifiez que le corps de la requête est correct
    req.flush(updatedSession); // Simulez la réponse HTTP
  });

  it('should delete a session', () => {
    const sessionId = '1';

    service.delete(sessionId).subscribe(response => {
      expect(response).toBeUndefined(); // Vérifiez que la réponse est undefined, car le serveur ne renvoie généralement rien pour une suppression réussie
    });

    const req = httpMock.expectOne(`api/session/${sessionId}`); // Vérifiez la requête HTTP
    expect(req.request.method).toBe('DELETE'); // Vérifiez la méthode HTTP utilisée
    req.flush(null); // Simulez la réponse HTTP (null car le serveur ne renvoie généralement rien pour une suppression réussie)
  });

  it('should add user participation to a session', () => {
    const sessionId = '1';
    const userId = '123';

    service.participate(sessionId, userId).subscribe(() => {
      // La souscription est vide car la méthode renvoie un Observable<void>
    });

    const req = httpMock.expectOne(`api/session/${sessionId}/participate/${userId}`); // Vérifiez la requête HTTP
    expect(req.request.method).toBe('POST'); // Vérifiez la méthode HTTP utilisée
    expect(req.request.body).toBeNull(); // Vérifiez que le corps de la requête est null
    req.flush(null); // Simulez la réponse HTTP
  });

  it('should remove user participation from a session', () => {
    const sessionId = '1';
    const userId = '123';

    service.unParticipate(sessionId, userId).subscribe(() => {
      // La souscription est vide car la méthode renvoie un Observable<void>
    });

    const req = httpMock.expectOne(`api/session/${sessionId}/participate/${userId}`); // Vérifiez la requête HTTP
    expect(req.request.method).toBe('DELETE'); // Vérifiez la méthode HTTP utilisée
    req.flush(null); // Simulez la réponse HTTP
  });
});
