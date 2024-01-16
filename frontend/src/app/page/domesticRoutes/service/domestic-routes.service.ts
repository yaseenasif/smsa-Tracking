import { environment } from './../../../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Routes } from '../../../model/ShipmentRoutes'
import { DomesticShipment } from '../../../model/DomesticShipment';
@Injectable({
  providedIn: 'root'
})
export class DomesticRoutesService {

  url = environment.baseurl;

  constructor(private http: HttpClient) { }

  addDomesticRoute(domesticRouteObj:Routes){
    return this.http.post<Routes>(`${this.url}/add-domesticRoute`,domesticRouteObj)
  }

  getDomesticRoute(origin: string, destination: string) {
    return this.http.get(`${this.url}/getRoute/${origin}/${destination}`)
  }

  getAllDomesticRoutes(obj?: any, page?: number, size?: number): Observable<any> {
    let queryParams = new HttpParams();
    
    queryParams = queryParams.append("value", obj ? JSON.stringify(obj) : '');
    queryParams = queryParams.append("page", page ? page : 0);
    queryParams = queryParams.append("size", size ? size : 10);

    let url = `${this.url}/all-domesticRoutes`
    return this.http.get<any>(url, { params: queryParams })
  }

  deleteDomesticRoute(id: number): Observable<Error> {
    let url = `${this.url}/delete-domestic-route/${id}`
    return this.http.delete<Error>(url)
  }

  getDomesticRouteById(id: number): Observable<Routes> {
    let url = `${this.url}/get-domestic-route/${id}`
    return this.http.get<Routes>(url)
  }

  updateDomesticRoute(id: number, obj: any): Observable<Routes> {

    let url = `${this.url}/update-domestic-route/${id}`
    return this.http.patch<Routes>(url, obj)
  }
}
