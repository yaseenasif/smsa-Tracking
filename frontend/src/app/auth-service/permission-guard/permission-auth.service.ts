import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class PermissionAuthService {
  constructor() { }
  private token=localStorage.getItem('accessToken');
  private decodedToken!:any
  permissionManager() {
    this.token = localStorage.getItem('accessToken'); 
    this.decodedToken=jwtDecode(this.token!)
 
    
    if(this.decodedToken!.PERMISSIONS.includes('All')){
     return true
    }

    return false
  }
}
