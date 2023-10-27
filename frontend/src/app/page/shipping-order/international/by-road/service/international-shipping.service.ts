import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { InternationalShipment } from 'src/app/model/InternationalShipment';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InternationalShippingService {

  constructor(private http:HttpClient) { }
  url=environment.baseurl;
  
  getAllInternationalShipmentByRoad():Observable<InternationalShipment[]>{
    return this.http.get<InternationalShipment[]>(this.url.concat('/international-shipments-by-user-road'));
  }
  getInternationalShipmentByID(id:number):Observable<InternationalShipment>{
    return this.http.get<InternationalShipment>(this.url.concat('/international-shipment/',id.toString()));
  }
  updateInternationalShipmentById(id:number,body:InternationalShipment):Observable<InternationalShipment>{
    return this.http.patch<InternationalShipment>(this.url.concat('/update-international-shipment/',id.toString()),body);
  }
  addInternationalShipment(body:InternationalShipment):Observable<InternationalShipment>{
   return this.http.post<InternationalShipment>(this.url.concat('/add-international-shipment'),body);
  }
  deletePermissionByID(id:number):Observable<InternationalShipment>{
    return this.http.delete<InternationalShipment>(this.url.concat('/InternationalShipment/',id.toString()));
  }
}
