import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Location } from '../../../model/Location'

@Injectable({
  providedIn: 'root'
})
export class LocationService {
  constructor(private http:HttpClient) { }
  url=environment.baseurl;
  
  getAllLocation():Observable<Location[]>{
    return this.http.get<Location[]>(this.url.concat('/location'));
  }
  getAllLocationForDomestic():Observable<Location[]>{
    return this.http.get<Location[]>(this.url.concat('/location-domestic'));
  }
  getAllLocationForInternational():Observable<Location[]>{
    return this.http.get<Location[]>(this.url.concat('/location-international'));
  }
  getLocationByID(id:number):Observable<Location>{
    return this.http.get<Location>(this.url.concat('/location/',id.toString()));
  }
  updateLocationById(id:number,body:Location):Observable<Location>{
    return this.http.patch<Location>(this.url.concat('/location/',id.toString()),body);
  }
  addLocation(body:Location):Observable<Location>{
   return this.http.post<Location>(this.url.concat('/location'),body);
  }
  deleteLocationByID(id:number):Observable<Location>{
    return this.http.delete<Location>(this.url.concat('/location/',id.toString()));
  }

}
