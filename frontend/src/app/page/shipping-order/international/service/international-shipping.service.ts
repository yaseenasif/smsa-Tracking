import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { InternationalShipment, Time } from 'src/app/model/InternationalShipment';
// import { LocationPort } from 'src/app/model/LocationPort';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InternationalShippingService {

  constructor(private http: HttpClient) { }
  url = environment.baseurl;

  getAllInternationalShipmentByRoad(obj?: any, page?: number, size?: number): Observable<InternationalShipment[]> {
    let queryParams = new HttpParams();
    
    queryParams = queryParams.append("value", JSON.stringify(obj));
    queryParams = queryParams.append("page", page ? page : 0);
    queryParams = queryParams.append("size", size ? size : 10);
    return this.http.get<InternationalShipment[]>(this.url.concat('/international-shipments-by-user-road'), { params: queryParams });
  }
  getAllInternationalShipmentByAir(obj?: any, page?: number, size?: number): Observable<InternationalShipment[]> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append("value", JSON.stringify(obj));
    queryParams = queryParams.append("page", page ? page : 0);
    queryParams = queryParams.append("size", size ? size : 10);
    
    return this.http.get<InternationalShipment[]>(this.url.concat('/international-shipments-by-user-air'), { params: queryParams });
  }

  getAllInternationalShipmentByRoadForOutBound(obj?: any, page?: number, size?: number): Observable<InternationalShipment[]> {
    let queryParams = new HttpParams();
    
    queryParams = queryParams.append("value", JSON.stringify(obj));
    queryParams = queryParams.append("page", page ? page : 0);
    queryParams = queryParams.append("size", size ? size : 10);
    return this.http.get<InternationalShipment[]>(this.url.concat('/international-outbound-summery-road'), { params: queryParams });
  }
  getAllInternationalShipmentByAirForOutBound(obj?: any, page?: number, size?: number): Observable<InternationalShipment[]> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append("value", JSON.stringify(obj));
    queryParams = queryParams.append("page", page ? page : 0);
    queryParams = queryParams.append("size", size ? size : 10);
    
    return this.http.get<InternationalShipment[]>(this.url.concat('/international-outbound-summery-air'), { params: queryParams });
  }


  getInternationalShipmentByID(id: number): Observable<InternationalShipment> {
    return this.http.get<InternationalShipment>(this.url.concat('/international-shipment/', id.toString()));
  }
  updateInternationalShipmentById(id: number, body: InternationalShipment,oId:number,dId:number): Observable<InternationalShipment> {
    return this.http.patch<InternationalShipment>(this.url.concat('/update-international-shipment/',id.toString(),'/org-id/',oId.toString(),'/des-id/',dId.toString()), body);
  }
  addInternationalShipment(body: InternationalShipment,oId:number,dId:number): Observable<InternationalShipment> {
    return this.http.post<InternationalShipment>(this.url.concat('/add-international-shipment/org-id/',oId.toString(),'/des-id/',dId.toString()), body);
  }
  deleteInternationalShipmentByID(id: number): Observable<InternationalShipment> {
    return this.http.delete<InternationalShipment>(this.url.concat('/delete-international-shipment/', id.toString()));
  }
  getInternationalShipmentHistoryByInternationalShipmentId(id: number) {
    return this.http.get<any>(`${this.url}/all-international-shipments-history/${id}`)
  }

  getFileMetaDataByDomesticShipment(id: number) {
    return this.http.get<any>(`${this.url}/file-meta-data-by-domestic-shipment/${id}`)
  }
  getInternationalRouteForAir(origin: string, destination: string, trip: number) {
    return this.http.get<any>(`${this.url}/getRoute-air/${origin}/${destination}/${trip}`)
  }
  getInternationalRouteForRoad(origin: string, destination: string, trip: number) {
    return this.http.get<any>(`${this.url}/getRoute-road/${origin}/${destination}/${trip}`)
  }

  deleteAttachment(id:number){
    return this.http.delete<any>(`${this.url}/delete-attachment/${id}`)
  }

  getFileMetaDataByInternationalShipment(id: number) {
    return this.http.get<any>(`${this.url}/file-meta-data-by-international-shipment/${id}`)
  }

  // getLocationPortByLocation(name: string): Observable<LocationPort[]> {
  //   return this.http.get<LocationPort[]>(`${this.url}/location/location-port/${name}`)
  // }

  stringToTime(timeString: string | null | any): { hour: string | null | undefined, minute: string | null | undefined, nano: number | null | undefined, second: string | null | undefined } {
    const date = timeString ? new Date(timeString) : null;

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



  dynamicLabel(routeBy: string): ({ label: string, routerLink: string } | { label: string })[] | undefined {
    switch (routeBy) {
      case 'by-domestic-summary':
        return [{ label: 'Domestic Inbound', routerLink: '/domestic-summary' }, { label: 'View Attachment' }]
        break;
      case 'by-domestic-list':
        return [{ label: 'Domestic Outbound', routerLink: '/domestic-shipping' }, { label: 'View Attachment' }]
        break;
      case 'by-international-air-list':
        return [{ label: 'International Outbound', routerLink: '/international-tile' }, { label: 'International Outbound By Air', routerLink: '/international-shipment-by-air' }, { label: 'View Attachment' }];
        break;
      case 'by-international-air-summary':
        return [{ label: 'International Inbound By Air', routerLink: '/international-summary-by-air' }, { label: 'View Attachment' }]
        break;
      case 'by-international-road-list':
        return [{ label: 'International Outbound', routerLink: '/international-tile' }, { label: 'International Outbound By Road', routerLink: '/international-shipment-by-road' }, { label: 'View Attachment' }];
        break;
      case 'by-international-road-summary':
        return [{ label: 'International Inbound By Road', routerLink: '/international-summary-by-road' }, { label: 'View Attachment' }]
        break;
      default:
        return undefined;
        break;
    }

  }

}
