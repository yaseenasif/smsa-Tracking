import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DomesticShippingService {

  url=environment.baseurl;
  constructor(private http:HttpClient) { }

  getALLShipments():Observable<any>{
    return this.http.get<any>(this.url.concat('/all-domestic-shipments'));
  }
}
