import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthguardService {

  constructor() { }
  isAuthenticated(): boolean {
    const token = localStorage.getItem('accessToken');
    return !!token; 
  }
}