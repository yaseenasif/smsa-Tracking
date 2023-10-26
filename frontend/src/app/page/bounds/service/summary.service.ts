import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SummaryService {
  url=environment.baseurl;
  constructor(private http:HttpClient) { }

  getInboundSummary(obj:any,page:number,size:number):Observable<any>{
    let queryParams = new HttpParams();
    queryParams = queryParams.append("value",JSON.stringify(obj));
    queryParams = queryParams.append("page",page);
    queryParams = queryParams.append("size",size);
    debugger
    return this.http.get<any>(`${this.url}/domestic-shipment/inbound`,{params:queryParams});
  }

  getOutboundSummary(obj:any,page:number,size:number):Observable<any>{
    let queryParams = new HttpParams();
    queryParams = queryParams.append("value",JSON.stringify(obj));
    queryParams = queryParams.append("page",page);
    queryParams = queryParams.append("size",size);
    debugger
    return this.http.get<any>(`${this.url}/domestic-shipment/outbound`,{params:queryParams});
  }


}
