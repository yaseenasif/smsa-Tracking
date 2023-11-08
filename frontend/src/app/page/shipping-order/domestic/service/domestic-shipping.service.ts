import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DomesticShipment } from 'src/app/model/DomesticShipment';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DomesticShippingService {

  url=environment.baseurl;
  constructor(private http:HttpClient) { }

  getALLShipments(obj:any,page:number,size:number):Observable<any>{
    let queryParams = new HttpParams();
    queryParams = queryParams.append("value",JSON.stringify(obj));
    queryParams = queryParams.append("page",page);
    queryParams = queryParams.append("size",size);
    
    return this.http.get<any>(`${this.url}/all-domestic-shipments`,{params:queryParams});
  }

  addDomesticShipment(shipment:DomesticShipment){
    return this.http.post<DomesticShipment>(`${this.url}/add-domestic-shipment`,shipment)
  }

  getDomesticShipmentById(id:number){
    return this.http.get<DomesticShipment>(`${this.url}/domestic-shipment/${id}`)
  }

  updateDomesticShipment(id:number,domesticShipment:DomesticShipment){
    return this.http.put<DomesticShipment>(`${this.url}/edit-domestic-shipment/${id}`,domesticShipment)
  }
  
  deleteDomesticShipment(id:number){
    return this.http.delete<any>(`${this.url}/delete-domestic-shipment/${id}`)
  }

  getDomesticShipmentHistoryByDomesticShipmentId(id:number){
    return this.http.get<any>(`${this.url}/all-domestic-shipments-history/${id}`)
  }

  downloadAttachments(fileName:string):Observable<Blob>{
    return this.http.get(`${this.url}/download/${fileName}`,{
      responseType: 'blob'
    });
  }
}
