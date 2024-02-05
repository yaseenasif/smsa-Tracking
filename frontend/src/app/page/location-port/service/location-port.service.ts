import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
// import { LocationPort } from 'src/app/model/LocationPort';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LocationPortService {
  // constructor(private http:HttpClient) { }
  // url=environment.baseurl;
  
  // getAllLocationPort():Observable<LocationPort[]>{
  //   return this.http.get<LocationPort[]>(this.url.concat('/location-port'));
  // }
  // getLocationPortByID(id:number):Observable<LocationPort>{
  //   return this.http.get<LocationPort>(this.url.concat('/location-port/',id.toString()));
  // }
  // updateLocationPortById(id:number,body:LocationPort):Observable<LocationPort>{
  //   return this.http.patch<LocationPort>(this.url.concat('/location-port/',id.toString()),body);
  // }
  // addLocationPort(body:LocationPort):Observable<LocationPort>{
  //  return this.http.post<LocationPort>(this.url.concat('/location-port'),body);
  // }
  // deleteLocationPortByID(id:number):Observable<LocationPort>{
  //   return this.http.delete<LocationPort>(this.url.concat('/location-port/',id.toString()));
  // }
}
