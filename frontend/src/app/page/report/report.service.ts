import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { InternationalAirReportPerformance } from 'src/app/model/InternationalAirReportPerformance';
import { InternationalAirReportStatus } from 'src/app/model/InternationalAirReportStatus';
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
  getInternationalAirReportStatus():Observable<InternationalAirReportStatus[]>{
    return this.http.get<InternationalAirReportStatus[]>(this.url.concat('/int-air-report-status'));
  }
}


