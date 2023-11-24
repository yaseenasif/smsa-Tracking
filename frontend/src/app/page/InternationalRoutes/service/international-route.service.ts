import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Routes } from '../../../model/ShipmentRoutes';

@Injectable({
  providedIn: 'root'
})
export class InternationalRouteService {

  url = environment.baseurl;

  constructor(private http: HttpClient) { }

  addInternationalRoute(obj: any) {

    let url = `${this.url}/add-internationalRoute`
    return this.http.post(url, obj)
  }

  getInternationalRoute(origin: string, destination: string) {
    return this.http.get(`${this.url}/getRoute/${origin}/${destination}`)
  }

  getAllInternationalRoutesForAir(): Observable<Routes[]> {
    let url = `${this.url}/get-all-international-air`
    return this.http.get<Routes[]>(url)
  }

  getAllInternationalRoutesForRoad(): Observable<Routes[]> {
    let url = `${this.url}/get-all-international-road`
    return this.http.get<Routes[]>(url)
  }

  deleteInternationalRoute(id: number): Observable<Routes> {
    let url = `${this.url}/-/${id}`
    return this.http.delete<Routes>(url)
  }

  getInternationalRouteById(id: number) {
    let url = `${this.url}/-/${id}`
    return this.http.get(url)
  }

  updateInternationalRoute(id: number, obj: any) {
    let url = `${this.url}/--/${id}`
    return this.http.put(url, obj)
  }
}
