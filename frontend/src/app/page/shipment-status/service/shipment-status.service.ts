import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ShipmentStatusService {

  constructor(private http:HttpClient) { }
  url=environment.baseurl;
  
  getALLShipmentStatus():Observable<ShipmentStatus[]>{
    return this.http.get<ShipmentStatus[]>(this.url.concat('/status'));
  }
  getShipmentStatusByID(id:number):Observable<ShipmentStatus>{
    return this.http.get<ShipmentStatus>(this.url.concat('/status/',id.toString()));
  }
  updateShipmentStatusById(id:number,body:ShipmentStatus):Observable<ShipmentStatus>{
    return this.http.patch<ShipmentStatus>(this.url.concat('/status/',id.toString()),body);
  }
  addShipmentStatus(body:ShipmentStatus):Observable<ShipmentStatus>{
   return this.http.post<ShipmentStatus>(this.url.concat('/status'),body);
  }
  deleteShipmentStatusByID(id:number):Observable<void>{
    return this.http.delete<void>(this.url.concat('/status/',id.toString()));
  }
}
