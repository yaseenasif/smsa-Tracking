import { environment } from './../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Routes } from '../../../model/ShipmentRoutes'
@Injectable({
  providedIn: 'root'
})
export class DomesticRoutesService {

  url = environment.baseurl;

  constructor(private http: HttpClient) { }

  addDomesticRoute(obj: any) {

    let url = `${this.url}/add-domesticRoute`
    return this.http.post(url, obj)
  }

  getDomesticRoute(origin: string, destination: string) {
    return this.http.get(`${this.url}/getRoute/${origin}/${destination}`)
  }

  getAllDomesticRoutes(): Observable<Routes[]> {
    let url = `${this.url}/all-domesticRoutes`
    return this.http.get<Routes[]>(url)
  }

  deleteDomesticRoute(id: number): Observable<Routes> {
    let url = `${this.url}//${id}`
    return this.http.delete<Routes>(url)
  }

  getDomesticRouteById(id: number) {
    let url = `${this.url}/-/${id}`
    return this.http.get(url)
  }

  updateDomesticRoute(id: number, obj: any) {

    let url = `${this.url}/--/${id}`
    return this.http.put(url, obj)
  }
}
