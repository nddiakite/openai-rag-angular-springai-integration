import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let currentUser = localStorage.getItem('currentUser');

    if (currentUser && currentUser !== 'NOT_DEFINED') {
      request = request.clone({
        setHeaders: {
          Authorization: `Basic ${currentUser}`
        }
      });
    }

    return next.handle(request);
  }
}
