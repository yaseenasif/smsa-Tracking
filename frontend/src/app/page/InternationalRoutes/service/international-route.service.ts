import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Routes } from '../../../model/ShipmentRoutes';
import { InternationalRoutes } from 'src/app/model/InternationalRoute';

@Injectable({
  providedIn: 'root'
})
export class InternationalRouteService {

  url = environment.baseurl;

  constructor(private http: HttpClient) { }

  addInternationalRoute(obj: any): Observable<InternationalRoutes> {

    let url = `${this.url}/add-internationalRoute`
    return this.http.post<InternationalRoutes>(url, obj)
  }

  getInternationalRoute(origin: string, destination: string) {
    return this.http.get(`${this.url}/getRoute/${origin}/${destination}`)
  }

  getAllInternationalRoutesForAir(): Observable<InternationalRoutes[]> {
    let url = `${this.url}/get-all-international-air`
    return this.http.get<InternationalRoutes[]>(url)
  }

  getAllInternationalRoutesForRoad(): Observable<InternationalRoutes[]> {
    let url = `${this.url}/get-all-international-road`
    return this.http.get<InternationalRoutes[]>(url)
  }

  deleteInternationalRoute(id: number): Observable<any> {
    let url = `${this.url}/delete-international-route/${id}`
    return this.http.delete<any>(url)
  }

  getInternationalRouteById(id: number): Observable<InternationalRoutes> {
    let url = `${this.url}/get-international-route-by-id/${id}`
    return this.http.get<InternationalRoutes>(url)
  }

  updateInternationalRoute(id: number, obj: any): Observable<InternationalRoutes> {
    let url = `${this.url}/update-international-route/${id}`
    return this.http.put<InternationalRoutes>(url, obj)
  }
}
