import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { InternationalAirReportPerformance } from 'src/app/model/InternationalAirReportPerformance';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(private http:HttpClient) { }
  url=environment.baseurl;
  getInternationalAirReportPerformance():Observable<InternationalAirReportPerformance[]>{
    return this.http.get<InternationalAirReportPerformance[]>(this.url.concat('/int-air-report-performance'));
  }
}
