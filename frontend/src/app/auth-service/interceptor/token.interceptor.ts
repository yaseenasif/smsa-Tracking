import { HTTP_INTERCEPTORS, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor() { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let token = localStorage.getItem("accessToken");
    if (token != null) {
      const authReq = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`)
      }
      );

      return next.handle(authReq);
    }

    return next.handle(req);
  }

}
// export const httpInterceptorProviders = [
//   { provide: HTTP_INTERCEPTORS,
//      useClass: TokenInterceptor, 
//      multi: true },
// ];