import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DomesticShipment } from 'src/app/model/DomesticShipment';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DomesticShippingService {

  url = environment.baseurl;
  constructor(private http: HttpClient) { }

  getALLShipments(obj?: any, page?: number, size?: number): Observable<any> {
    let queryParams = new HttpParams();
    
    queryParams = queryParams.append("value", obj ? JSON.stringify(obj) : '');
    queryParams = queryParams.append("page", page ? page : 0);
    queryParams = queryParams.append("size", size ? size : 10);

    return this.http.get<any>(`${this.url}/all-domestic-shipments`, { params: queryParams });
  }

  getALLShipmentsOutBound(obj?: any, page?: number, size?: number): Observable<any> {
    let queryParams = new HttpParams();
    
    queryParams = queryParams.append("value", obj ? JSON.stringify(obj) : '');
    queryParams = queryParams.append("page", page ? page : 0);
    queryParams = queryParams.append("size", size ? size : 10);

    return this.http.get<any>(`${this.url}/domestic-shipment/outbound`, { params: queryParams });
  }
  addDomesticShipment(shipment: DomesticShipment,oId:number,dId:number) {
    return this.http.post<DomesticShipment>(`${this.url}/add-domestic-shipment/org-id/${oId}/des-id/${dId}`, shipment)
  }

  getDomesticShipmentById(id: number) {
    return this.http.get<DomesticShipment>(`${this.url}/domestic-shipment/${id}`)
  }

  updateDomesticShipment(id: number,oId: number,dId: number, domesticShipment: DomesticShipment) {
    return this.http.put<DomesticShipment>(`${this.url}/edit-domestic-shipment/${id}/org-id/${oId}/des-id/${dId}`, domesticShipment)
  }

  deleteDomesticShipment(id: number) {
    return this.http.delete<any>(`${this.url}/delete-domestic-shipment/${id}`)
  }

  getDomesticShipmentHistoryByDomesticShipmentId(id: number) {
    return this.http.get<any>(`${this.url}/all-domestic-shipments-history/${id}`)
  }

  getDomesticRoute(origin: string, destination: string) {
    return this.http.get<any>(`${this.url}/getRoute/${origin}/${destination}`)
  }

  getRouteByRouteNumber(routeNumber: string) {
    return this.http.get<any>(`${this.url}/domesticRoute/${routeNumber}`)
  }

  downloadAttachments(fileName: string): Observable<Blob> {
    return this.http.get(`${this.url}/download/${fileName}`, {
      responseType: 'blob'
    });
  }
}
