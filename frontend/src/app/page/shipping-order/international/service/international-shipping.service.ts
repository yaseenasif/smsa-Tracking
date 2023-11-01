import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { InternationalShipment, Time } from 'src/app/model/InternationalShipment';
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
  getAllInternationalShipmentByAir():Observable<InternationalShipment[]>{
    return this.http.get<InternationalShipment[]>(this.url.concat('/international-shipments-by-user-air'));
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

  stringToTime(timeString:string|null|any): {hour: string|null|undefined,minute:string|null|undefined,nano:number|null|undefined,second:string|null|undefined}{
    const date =timeString? new Date(timeString):null;
    
    return {
      hour: this.padNumber(date!.getHours().toString()),
      minute: this.padNumber(date!.getMinutes().toString()),
      nano: 0,
      second: this.padNumber(date!.getSeconds().toString()),
    };
  }

  // Convert Time interface to string
  timeToString(time: Time): string {
    const { hour, minute, nano, second } = time;

    // Create a Date object with provided values
    const date = new Date();
    date.setHours(parseInt(hour || '0', 10));
    date.setMinutes(parseInt(minute || '0', 10));
    date.setSeconds(parseInt(second || '0', 10));
    date.setMilliseconds(nano || 0);

    // Format the Date object to string
    return date.toISOString();
  }

  private padNumber(value: string): string {
    return value.length === 1 ? '0' + value : value;
  }

}
