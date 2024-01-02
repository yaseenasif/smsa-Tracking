import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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
 
  downloadReportExcel(address:string,searchBy:any,name:any){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    let queryParams = new HttpParams();

    queryParams = queryParams.append("value", searchBy ? JSON.stringify(searchBy) : '' );
    this.http
      .get(`${this.url}${address}`,{ params: queryParams ,
        responseType: 'blob',
        headers,
      })
      .subscribe((response: any) => {
        const blob = new Blob([response], { type: 'application/octet-stream' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.style.display = 'none';
        a.href = url;
        a.download = name;
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
      });
  }

}


