import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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
  getInternationalAirReportPerformance(searchBy:any):Observable<InternationalAirReportPerformance[]>{
    let queryParams = new HttpParams();
    
    queryParams = queryParams.append("value", searchBy ? JSON.stringify(searchBy) : '' );
    return this.http.get<InternationalAirReportPerformance[]>(this.url.concat('/int-air-report-performance'),{ params: queryParams });
  }

  downloadExcelOfInternationalAirReportPerformance(){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    this.http
      .get(`${this.url}/int-air-rep-per`, {
        responseType: 'blob',
        headers,
      })
      .subscribe((response: any) => {
        const blob = new Blob([response], { type: 'application/octet-stream' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.style.display = 'none';
        a.href = url;
        a.download = 'International Air Report performance.xlsx'; 
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
      });
  }
 
}


