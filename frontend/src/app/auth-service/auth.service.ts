import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from '../../environments/environment'

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})

export class AuthService {

  url:string=environment.baseurl;
  
  constructor(private httpClient:HttpClient) { }

  
  login(credentials:any):Observable<any>{
    return this.httpClient.post<any>(this.url.concat("/login"),credentials,httpOptions);
  }

}
