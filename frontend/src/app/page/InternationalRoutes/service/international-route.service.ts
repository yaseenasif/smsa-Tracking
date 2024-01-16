import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
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

  getAllInternationalRoutesForAir(obj?: any, page?: number, size?: number): Observable<any> {
    let queryParams = new HttpParams();
    
    queryParams = queryParams.append("value", obj ? JSON.stringify(obj) : '');
    queryParams = queryParams.append("page", page ? page : 0);
    queryParams = queryParams.append("size", size ? size : 10);
    let url = `${this.url}/get-all-international-air`
    return this.http.get<any>(url, { params: queryParams })
  }

  getAllInternationalRoutesForRoad(obj?: any, page?: number, size?: number): Observable<any> {
    let queryParams = new HttpParams();
    
    queryParams = queryParams.append("value", obj ? JSON.stringify(obj) : '');
    queryParams = queryParams.append("page", page ? page : 0);
    queryParams = queryParams.append("size", size ? size : 10);
    let url = `${this.url}/get-all-international-road`
    return this.http.get<any>(url, { params: queryParams })
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
