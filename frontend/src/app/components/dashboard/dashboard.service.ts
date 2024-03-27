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

  lowAndHighVolumeByLocationOutboundDomestic(year:number){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("year", year);
  return this.http.get<any>(`${this.url}/low-and-high-volume-by-location-outbound-domestic`,{ params: queryParams });
  }
  lowAndHighVolumeByLocationInboundDomestic(year:number){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("year", year);
  return this.http.get<any>(`${this.url}/low-and-high-volume-by-location-inbound-domestic`,{ params: queryParams });
  }

  lowAndHighVolumeByLocationOutboundInternationalAir(year:number){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("year", year);
  return this.http.get<any>(`${this.url}/low-and-high-volume-by-location-outbound-international-air`,{ params: queryParams });
  }
  lowAndHighVolumeByLocationOutboundInternationalRoad(year:number){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("year", year);
  return this.http.get<any>(`${this.url}/low-and-high-volume-by-location-outbound-international-road`,{ params: queryParams });
  }
  lowAndHighVolumeByLocationInboundInternationalAir(year:number){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("year", year);
  return this.http.get<any>(`${this.url}/low-and-high-volume-by-location-inbound-international-air`,{ params: queryParams });
  }
  lowAndHighVolumeByLocationInboundInternationalRoad(year:number){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("year", year);
  return this.http.get<any>(`${this.url}/low-and-high-volume-by-location-inbound-international-road`,{ params: queryParams });
  }

  lowToHighDomesticOutboundTest(year:number){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("year", year);
  return this.http.get<any>(`${this.url}/low-to-high-domestic-outbound-test`,{ params: queryParams });
  }

  lowToHighInternationalAirOutboundTest(year:number){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("year", year);
  return this.http.get<any>(`${this.url}/low-to-high-international-air-outbound-test`,{ params: queryParams });
  }

  lowToHighInternationalRoadOutboundTest(year:number){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("year", year);
  return this.http.get<any>(`${this.url}/low-to-high-international-road-outbound-test`,{ params: queryParams });
  }

  
}
