import { environment } from './../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
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

  getAllDomesticRoutes(): Observable<Routes[]> {
    let url = `${this.url}/all-domesticRoutes`
    return this.http.get<Routes[]>(url)
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
