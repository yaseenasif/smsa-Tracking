import { HTTP_INTERCEPTORS, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { JwtHelperService } from "@auth0/angular-jwt";
import { MessageService } from "primeng/api";
import { Observable } from "rxjs";


@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private router: Router) { }

  
  private jwtHelper: JwtHelperService = new JwtHelperService();

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {  
    let token = localStorage.getItem("accessToken");
    
    if (!(this.isTokenExpired(token!))) {
      
      const authReq = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`)
      }
      );

      return next.handle(authReq);
    }else{
      this.logout();
    }

    return next.handle(req);
  }

  // Check if the token is expired
isTokenExpired(token: string): boolean {
 return this.jwtHelper.isTokenExpired(token);
}

// Perform the logout action
logout(): void {
 // Add any additional logout logic here (e.g., clearing local storage, etc.)
 this.router.navigate(['/login']); // Redirect to the login page
}



}
// export const httpInterceptorProviders = [
//   { provide: HTTP_INTERCEPTORS,
//      useClass: TokenInterceptor, 
//      multi: true },
// ];