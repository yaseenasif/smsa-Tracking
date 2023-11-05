
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import {PermissionAuthService} from '../permission-guard/permission-auth.service'

@Injectable({
  providedIn: 'root'
})
export class PermissionAuthGuard implements CanActivate {
    constructor(private permissionAuthService:PermissionAuthService, private router: Router) {}
  
    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
      ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        const url = state.url;
        console.log(url);
        
       return this.permissionAuthService.permissionManager();
      }
}