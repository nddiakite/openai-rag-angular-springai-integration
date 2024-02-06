import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { environment } from 'src/environment/environment';
import { map } from 'rxjs/operators';
import {Router} from "@angular/router";
import {NbToastrService} from "@nebular/theme";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;

  constructor(private http: HttpClient, private router: Router, private toastrService: NbToastrService) {
    const storedUser = localStorage.getItem('currentUser');
    // this.currentUserSubject = new BehaviorSubject<any>(storedUser ? JSON.parse(storedUser) : null);
    // this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): any {
    return this.currentUserSubject.value;
  }

  login(username: string, password: string) {
    localStorage.setItem('currentUser', '');
    const authToken = btoa(username + ':' + password);
    const headers = new HttpHeaders({ Authorization: 'Basic ' + authToken });

    this.http.get<any>(`${environment.serverApiUrl}/users/login`, { headers })
      .subscribe((data) => {
          console.error("Data after login success : ", data);
          localStorage.setItem('currentUser', authToken);
        },
      error => {
              console.error("Statut après login : " + JSON.stringify(error));

              let userError = 'Inconnu';

              if (error.status === 401) {
                userError = 'Access non autorisé !!';
              } else if (error.status === 403) {
                userError = 'Login ou mot de passe invalide';
              } else if (error.status === 200) {
                userError = 'ALL correct, should not be here !!';
                localStorage.setItem('currentUser', authToken);
                this.router.navigate(['/home']);
              }

              this.toastrService.danger(
                `Echec connexion - ${userError}`,
                `Erreur`,
                { duration: 5000 }
              );
        });
  }

  logout() {
    localStorage.removeItem('currentUser');
    // this.currentUserSubject.next(null);

    this.router.navigate(['login']);
  }

  isLoggedIn(): boolean {
    const token = localStorage.getItem('currentUser');
    return !!token;
  }

  getToken(): string {
    return localStorage.getItem('currentUser') || "";
  }
}
