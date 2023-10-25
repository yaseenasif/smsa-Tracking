import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Driver } from 'src/app/model/Driver';
import { PaginatedResponse } from 'src/app/model/PaginatedResponse';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DriverService {
  constructor(private http:HttpClient) { }
  url=environment.baseurl;
  
  getAllDriver():Observable<any>{
    return this.http.get<PaginatedResponse<Driver>>(this.url.concat('/driver'));
  }
  getDriverByID(id:number):Observable<Driver>{
    return this.http.get<Driver>(this.url.concat('/driver/',id.toString()));
  }
  updateDriverById(id:number,body:Driver):Observable<Driver>{
    return this.http.patch<Driver>(this.url.concat('/driver/',id.toString()),body);
  }
  addDriver(body:Driver):Observable<Driver>{
   return this.http.post<Driver>(this.url.concat('/driver'),body);
  }
  deleteDriverByID(id:number):Observable<Driver>{
    return this.http.delete<Driver>(this.url.concat('/driver/',id.toString()));
  }
}
