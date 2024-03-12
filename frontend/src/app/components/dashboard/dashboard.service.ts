import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CardsData } from 'src/app/model/CardData';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private http:HttpClient) { }
  url = environment.baseurl;

  DomesticCardsData(year:number){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("year", year);
  return this.http.get<CardsData>(`${this.url}/dashboard-domestic`,{ params: queryParams });
  }
  InternationalAirCardsData(year:number){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("year", year);
  return this.http.get<CardsData>(`${this.url}/dashboard-international-count-air`,{ params: queryParams });
  }
  InternationalRoadCardsData(year:number){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("year", year);
  return this.http.get<CardsData>(`${this.url}/dashboard-international-count-road`,{ params: queryParams });
  }
}
