import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DomesticPerformance } from 'src/app/model/DomesticPerformance';
import { InternationalAirReportPerformance } from 'src/app/model/InternationalAirReportPerformance';
import { InternationalAirReportStatus } from 'src/app/model/InternationalAirReportStatus';
import { InternationalRoadReportPerformance } from 'src/app/model/InternationalRoadReportPerformance';
import { InternationalRoadReportStatus } from 'src/app/model/InternationalRoadReportStatus';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(private http:HttpClient) { }
  url=environment.baseurl;
  getInternationalAirReportPerformance(searchBy:any):Observable<InternationalAirReportPerformance[]>{
    let queryParams = new HttpParams();
    
    queryParams = queryParams.append("value", searchBy ? JSON.stringify(searchBy) : '' );
    return this.http.get<InternationalAirReportPerformance[]>(this.url.concat('/int-air-report-performance'),{ params: queryParams });
  }
  getInternationalAirReportStatus(searchBy:any):Observable<InternationalAirReportStatus[]>{
    let queryParams = new HttpParams();
    queryParams = queryParams.append("value", searchBy ? JSON.stringify(searchBy) : '' );
    return this.http.get<InternationalAirReportStatus[]>(this.url.concat('/int-air-report-status'),{ params: queryParams });
  }

  getDomesticReportPerformance(searchBy:any):Observable<DomesticPerformance[]>{
    let queryParams = new HttpParams();
    queryParams = queryParams.append("value", searchBy ? JSON.stringify(searchBy) : '' );
    return this.http.get<DomesticPerformance[]>(this.url.concat('/domestic-performance'),{ params: queryParams });
  }

  getInternationalRoadReportPerformance(searchBy:any):Observable<InternationalRoadReportPerformance[]>{
    let queryParams = new HttpParams();
    
    queryParams = queryParams.append("value", searchBy ? JSON.stringify(searchBy) : '' );
    return this.http.get<InternationalRoadReportPerformance[]>(this.url.concat('/int-road-report-performance'),{ params: queryParams });
  }
  getInternationalRoadReportStatus(searchBy:any):Observable<InternationalRoadReportStatus[]>{
    let queryParams = new HttpParams();
    queryParams = queryParams.append("value", searchBy ? JSON.stringify(searchBy) : '' );
    return this.http.get<InternationalRoadReportStatus[]>(this.url.concat('/int-road-report-status'),{ params: queryParams });
  }
}


